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

    Operation deposit(int code, double amount);

    Operation debit(int code, double amount);

    Operation transfer(int code1, int code2, Operation operation);

    void debitOperation(int code, double amount);

    void depositOperation(int code, double amount);

    StandingAccount addStandingAccount(StandingAccount standingAccount, long id);

    SavingsAccount addSavingsAccount(SavingsAccount savingsAccount, long id);

    StandingAccount editStandingAccount(StandingAccount standingAccount, int id);

    SavingsAccount editSavingsAccount(SavingsAccount savingsAccount, int id);

    List<Operation> findWithdraws(int id);

    List<Operation> findCredits(int id);

    List<Account> findStandingAccountAccounts(long id);

    List<Account> findSavingsAccountAccounts(long id);

    List<Account> findAccountsForUser(long id);




}
