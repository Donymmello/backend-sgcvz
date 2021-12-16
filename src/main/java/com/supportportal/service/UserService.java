package com.supportportal.service;


import com.supportportal.domain.*;
import com.supportportal.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    User updateUser(String currentUsername, String newNome, String newApelido, String newNascimento, String newMorada, String newB_i, String newNuit, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;

    void resetPassword(String email) throws MessagingException, EmailNotFoundException;

    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    List<Operation> checkingAccount(int code);

    Operation deposit(int number, double amount);

    Operation debit(int number, double amount);

    Operation transfer(int number1, int number2, Operation operation);

    void debitOperation(int number, double amount);

    void depositOperation(int number, double amount);

    BusinessAccount editBusinessAccount(BusinessAccount businessAccount, int id);

    List<Operation> findCredits(int id);

    List<Operation> findWithdraws(int id);

    List<Account> findBusinessAccountAccounts(long id);

    List<Account> findAccountsForUser(long id);

    BusinessAccount addAccount(BusinessAccount businessAccount, long id);

    //Loan addNewLoan (String loanStatus, double amount);

    Loan registerLoan(Loan loan, long id);
}
