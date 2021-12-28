package com.supportportal.service;


import com.stripe.model.Account;
import com.supportportal.domain.*;
import com.supportportal.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User findUser(long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String nome, String apelido, String nascimento, String morada, String b_i, String nuit, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    //@Override
    //public Loan addNewLoan(String loanStatus, double amount) {
        //Loan loan = new Loan();
        //loan.setLoanStatus(getLoanStatusEnumName(loanStatus).name());
        //loan.setAuthorities(getLoanStatusEnumName(loanStatus).getAuthorities());
        //loan.setAmount(amount);
        //loanRepository.save(loan);
        //return loan;
    //}

    User updateUser(String currentUsername, String newNome, String newApelido, String newNascimento, String newMorada, String newB_i, String newNuit, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;

    void resetPassword(String email) throws MessagingException, EmailNotFoundException;

    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    Loan registerLoan(Loan loan, long id);
}
