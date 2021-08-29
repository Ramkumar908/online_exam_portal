package com.exam.service;

import com.exam.model.OtpRequest;
import com.exam.model.StatusDescriptionModel;
import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

import org.springframework.http.ResponseEntity;

public interface UserService {

    //creating user
    public StatusDescriptionModel createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get user by username
    public User getUser(String username);

    //delete user by id
    public void deleteUser(Long userId);
    
    
    public StatusDescriptionModel forgetPass(String email);

	public StatusDescriptionModel verisfyUserOtp(OtpRequest otpRequest);

	public StatusDescriptionModel updateUserPass(String pass,String  email);
}
