package com.supportportal.service.impl;

import com.supportportal.domain.*;
import com.supportportal.enumeration.LoanStatus;
import com.supportportal.enumeration.Role;
import com.supportportal.exception.domain.*;
import com.supportportal.repository.AccountDao;
import com.supportportal.repository.LoanRepository;
import com.supportportal.repository.OperationDao;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.EmailService;
import com.supportportal.service.LoginAttemptService;
import com.supportportal.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.UserImplConstant.*;
//import static com.supportportal.enumeration.Role.ROLE_SUPER_ADMIN;
import static com.supportportal.enumeration.LoanStatus.PENDING;
import static com.supportportal.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
@Component
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(LoanRepository loanRepository, UserRepository userRepository, AccountDao accountDao, OperationDao operationDao, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OperationDao operationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    @Override
    public User register(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        user.setNome(nome);
        user.setApelido(apelido);
        user.setNascimento(nascimento);
        user.setMorada(morada);
        user.setB_i(b_i);
        user.setNuit(nuit);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        //user.setRole(ROLE_SUPER_ADMIN.name());
        //user.setRole(ROLE_ADMIN.name());
        user.setRole(ROLE_USER.name());
        //user.setAuthorities(ROLE_SUPER_ADMIN.getAuthorities());
        //user.setAuthorities(ROLE_ADMIN.getAuthorities());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(nome, password, email);
        return user;
    }

    @Override
    public User addNewUser(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        String password = generatePassword();
        user.setUserId(generateUserId());
        user.setNome(nome);
        user.setApelido(apelido);
        user.setNascimento(nascimento);
        user.setMorada(morada);
        user.setB_i(b_i);
        user.setNuit(nuit);
        user.setJoinDate(new Date());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        saveProfileImage(user, profileImage);
        LOGGER.info("New user password: " + password);
        return user;
    }
    @Override
    public Loan registerLoan (Loan loan, long id){
        User user = userRepository.findById(id).orElse(null);
        loan.setCreatedOn(new Date());
        loan.setLoanStatus(PENDING.name());
        user.registerLoan(loan);
        return loanRepository.save(loan);
    }
    //@Override
    //public Loan addNewLoan(String loanStatus, double amount) {
        //Loan loan = new Loan();
        //loan.setLoanStatus(getLoanStatusEnumName(loanStatus).name());
        //loan.setAuthorities(getLoanStatusEnumName(loanStatus).getAuthorities());
        //loan.setAmount(amount);
        //loanRepository.save(loan);
        //return loan;
    //}

    @Override
    public User updateUser(String currentUsername, String newNome, String newApelido, String newNascimento, String newMorada, String newB_i, String newNuit, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setNome(newNome);
        currentUser.setApelido(newApelido);
        currentUser.setNascimento(newNascimento);
        currentUser.setMorada(newMorada);
        currentUser.setB_i(newB_i);
        currentUser.setNuit(newNuit);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        return currentUser;
    }

    @Override
    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(user.getNome(), password, user.getEmail());
    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(user, profileImage);
        return user;
    }

    @Override
    public List<Operation> checkingAccount(int number) {
        return operationDao.checkingAccount(number);
    }

    @Override
    public Operation deposit(int number, double amount) {
        String deposit = "deposit";
        Account account = accountDao.findById(number).orElse(null);
        Credit credit = new Credit(new Date(), amount, deposit, account);
        account.setBalance(account.getBalance() + amount);
        accountDao.save(account);
        return operationDao.save(credit);
    }

    @Override
    public Operation debit(int number, double amount) {
        String debit = "debit";
        Account account = accountDao.findById(number).orElse(null);
        double checkout = 0;
        if (account instanceof BusinessAccount) {
            checkout = ((BusinessAccount) account).getLoan();
        }

        if (account.getBalance() + checkout < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        Withdraw withdraw = new Withdraw(new Date(), amount, debit, account);
        account.setBalance(account.getBalance() - amount);
        accountDao.save(account);

        return operationDao.save(withdraw);
    }

    @Override
    public Operation transfer(int number1, int number2, Operation operation) {
        if (number1 == number2) {
            throw new RuntimeException("Cannot transfer to the same account ");
        }
        deposit(number1, operation.getAmount());
        debit(number2, operation.getAmount());
        return operationDao.save(operation);
    }

    @Override
    public void debitOperation(int number, double amount) {
        Account account = accountDao.findById(number).orElse(null);
        debit(account.getId(), amount);
    }

    @Override
    public void depositOperation(int number, double amount) {
        Account account = accountDao.findById(number).orElse(null);
        deposit(account.getId(), amount);
    }

    @Override
    public BusinessAccount editBusinessAccount(BusinessAccount businessAccount, int id) {
        BusinessAccount businessAccount2 = (BusinessAccount) accountDao.findById(id).orElse(null);
        businessAccount2.setLoan(businessAccount.getLoan());
        businessAccount2.setBalance(businessAccount.getBalance() + businessAccount.getLoan());
        return accountDao.save(businessAccount2);
    }

    @Override
    public List<Operation> findWithdraws(int id) {
        List<Operation> operations = accountDao.getOne(id).getOperations().stream().filter(w -> w instanceof Withdraw)
                .collect(Collectors.toList());
        return operations;
    }

    @Override
    public List<Operation> findCredits(int id) {
        List<Operation> operations = accountDao.getOne(id).getOperations().stream().filter(c -> c instanceof Credit)
                .collect(Collectors.toList());
        return operations;
    }

    @Override
    public List<Account> findBusinessAccountAccounts(long id) {
        List<Account> accounts = userRepository.getOne(id).getAccounts().stream().filter(s -> s instanceof BusinessAccount)
                .collect(Collectors.toList());
        return accounts;
    }

    @Override
    public List<Account> findAccountsForUser(long id) {
        User user = userRepository.findById(id).orElse(null);
        return user.getAccounts();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void deleteUser(String username) throws IOException {
        User user = userRepository.findUserByUsername(username);
        Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        userRepository.deleteById(user.getId());
    }

    @Override
    public BusinessAccount addAccount(BusinessAccount businessAccount, long id) {
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new  StringBuilder(6);
        Random random = new Random();
        for (int  i = 0; i < 6; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        User user = userRepository.findById(id).orElse(null);
        businessAccount.setCreateDate(new Date());
        businessAccount.setNumber(output);
        user.addAccount(businessAccount);
        return accountDao.save(businessAccount);
    }

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
                + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private LoanStatus getLoanStatusEnumName(String loanStatus) {
        return LoanStatus.valueOf(loanStatus.toUpperCase());
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private void validateLoginAttempt(User user) {
        if (user.isNotLocked()) {
            user.setNotLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }

    }

}
