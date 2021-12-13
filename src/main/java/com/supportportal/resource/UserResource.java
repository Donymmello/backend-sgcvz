package com.supportportal.resource;


import com.supportportal.domain.*;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.*;
import com.supportportal.repository.AccountDao;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/", "/user"})
@CrossOrigin("http://localhost:4200")
public class UserResource extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private AuthenticationManager authenticationManager;
    private UserService userService;

    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserResource(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Autowired
    private AccountDao accountDao;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        User newUser = userService.register(user.getNome(), user.getApelido(), user.getNascimento(), user.getMorada(), user.getB_i(), user.getNuit(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestParam("nome") String nome,
                                           @RequestParam("apelido") String apelido,
                                           @RequestParam("nascimento") String nascimento,
                                           @RequestParam("morada") String morada,
                                           @RequestParam("b_i") String b_i,
                                           @RequestParam("nuit") String nuit,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("role") String role,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("isNonLocked") String isNonLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User newUser = userService.addNewUser(nome, apelido, nascimento, morada, b_i, nuit, username, email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("nome") String nome,
                                       @RequestParam("apelido") String apelido,
                                       @RequestParam("nascimento") String nascimento,
                                       @RequestParam("morada") String morada,
                                       @RequestParam("b_i") String b_i,
                                       @RequestParam("nuit") String nuit,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser = userService.updateUser(currentUsername, nome, apelido, nascimento, morada, b_i, nuit, username, email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/resetpassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException {
        userService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        userService.deleteUser(username);
        return response(OK, USER_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = userService.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    @GetMapping("/checkingAccount/{code}")
    List<Operation> checkingAccount(@PathVariable int code) {
        return userService.checkingAccount(code);
    }

    @PostMapping("/depositOperation/{code}")
    void depositOperation(@PathVariable int code, @RequestBody Operation operation) {
        userService.depositOperation(code, operation.getAmount());
    }

    @PostMapping("/addStandingAccount/{id}")
    StandingAccount addStandingAccount(@RequestBody StandingAccount standingAccount, @PathVariable long id) {
        return userService.addStandingAccount(standingAccount, id);
    }

    @PostMapping("/addSavingsAccount/{id}")
    SavingsAccount addSavingsAccount(@RequestBody SavingsAccount savingsAccount, @PathVariable long id) {
        return userService.addSavingsAccount(savingsAccount, id);
    }

    @PostMapping("/debitOperation/{code}")
    void debitOperation(@PathVariable int code, @RequestBody Operation operation) {
        userService.debitOperation(code, operation.getAmount());
    }

    @GetMapping("/findWithdraws/{id}")
    public List<Operation> findWithdraws(@PathVariable int id) {
        return userService.findWithdraws(id);
    }

    @GetMapping("/findCredits/{id}")
    List<Operation> findCredits(@PathVariable int id) {
        return userService.findCredits(id);
    }

    @PostMapping("/transfer/{code1}/{code2}")
    Operation transfer(@PathVariable int code1, @PathVariable int code2, @RequestBody Operation operation) {
        return userService.transfer(code1, code2, operation);
    }

    @GetMapping("/findStandingAccountAccounts/{id}")
    List<Account> findStandingAccountAccounts(@PathVariable long id) {
        return userService.findStandingAccountAccounts(id);
    }

    @GetMapping("/findSavingsAccountAccounts/{id}")
    List<Account> findSavingsAccountAccounts(@PathVariable long id) {
        return userService.findSavingsAccountAccounts(id);
    }

    @GetMapping("/findAccountById/{id}")
    Account findAccountById(@PathVariable int id) {
        return accountDao.findById(id).orElse(null);
    }

    @PostMapping("/editStandingAccount/{id}")
    StandingAccount editStandingAccount(@RequestBody StandingAccount standingAccount, @PathVariable int id) {
        return userService.editStandingAccount(standingAccount, id);
    }
    @PostMapping("/editSavingsAccount/{id}")
    SavingsAccount editSavingsAccount(@RequestBody SavingsAccount savingsAccount, @PathVariable int id) {
        return userService.editSavingsAccount(savingsAccount, id);
    }
}
