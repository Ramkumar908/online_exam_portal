package com.exam.service.impl;

import java.util.Random;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exam.model.OtpRequest;
import com.exam.model.OtpStore;
import com.exam.model.StatusDescriptionModel;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.OtpStoreRepo;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	

    @Autowired
    private JavaMailSender sender;
    
    
    @Autowired
    OtpStoreRepo otpRepo;

	// creating user
	@Override
	public StatusDescriptionModel createUser(User user, Set<UserRole> userRoles) throws Exception {

		Random rnd=new Random();
		StatusDescriptionModel responseodel=new StatusDescriptionModel();
		User local = this.userRepository.findByUsername(user.getUsername());
		User checkEmail=this.userRepository.findByEmail(user.getEmail());
		User checkPhone=this.userRepository.findByPhone(user.getPhone());
//		if (local != null) {
//			responseodel.setStatusCode(409);
//			responseodel.setStatusMessage("User already exist");
//			responseodel.setTransactionId("");
//			//throw new UserFoundException();
//		} 
		if(local!=null)
		{
			responseodel.setStatusCode(409);
			responseodel.setStatusMessage("User already exist");
			responseodel.setTransactionId("");
		}

		 else
		 {
			  if(checkEmail!=null)
				 {
				    responseodel.setStatusCode(409);
					responseodel.setStatusMessage("email already exist");
					responseodel.setTransactionId("");
					return responseodel;
			 }		
			else if(checkPhone!=null)
				 {
					    responseodel.setStatusCode(409);
						responseodel.setStatusMessage("Phone no already exist");
						responseodel.setTransactionId("");  
						return responseodel;
			        }
			  
			  
		    for (UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
			System.out.println("username"+user.getEmail());
			int otp=rnd.nextInt(999999);
			sendMailRegister(user,otp,"Otp");
			
		      }
		

		user.getUserRoles().addAll(userRoles);
		local = this.userRepository.save(user);
		responseodel.setStatusCode(200);
		responseodel.setStatusMessage("User SuccessFully register");
		responseodel.setTransactionId("");
		return responseodel;
		 }

		return responseodel;
	}

	// getting user by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}
	
	@Override
	public StatusDescriptionModel forgetPass(String email) {
		
		StatusDescriptionModel responseModel=new StatusDescriptionModel();
		System.out.println("get user Mail at Impl"+email);
		
		User user=userRepository.findByEmail(email);
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		if(user.getEmail().isEmpty())
		{
			
			responseModel.setStatusCode(305);
			responseModel.setStatusMessage("User with"+user.getEmail()+"not exist");
			responseModel.setTransactionId("");
			
			return responseModel;
		}
		sendMail(user,number,"OTP");
		responseModel.setStatusCode(200);
		responseModel.setStatusMessage("Otp send to your Register mail");
		responseModel.setTransactionId("");
		
		
		return responseModel;
	}

	
	public String sendMail(User user,int otp,String subject) {
		OtpStore saveOtp=new OtpStore();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
        	System.out.println("get UserId"+user.getId());
            helper.setTo(user.getEmail());
            helper.setText("Kindly enter this Otp"  +otp);
            helper.setSubject(subject);
            saveOtp.setUserId(user.getId());
            saveOtp.setOtp(otp);
            saveOtp.setUsername(user.getUsername());
            OtpStore saveStatus=otpRepo.save(saveOtp);
            System.out.println("after store Otp get Status"+saveStatus.getOtp());
            
            
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

	
	public String sendMailRegister(User user,int otp,String subject) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        OtpStore saveOtp=new OtpStore();
        
        try {
        	System.out.println("get UserId"+user.getId());
            helper.setTo(user.getEmail());
            helper.setText("Kindly enter this Otp"  +otp);
            helper.setSubject(subject);
           // saveOtp.setUserId(user.getId());
            saveOtp.setOtp(otp);
            saveOtp.setUsername(user.getUsername());
            OtpStore saveStatus=otpRepo.save(saveOtp);
           System.out.println("after store Otp get Status"+saveStatus.getOtp());
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

	@Override
	public StatusDescriptionModel verisfyUserOtp(OtpRequest otpRequest) {
        OtpStore saveOtp=new OtpStore();
        StatusDescriptionModel responseModel=new StatusDescriptionModel();
        System.out.println("get otp before sql"+otpRequest.getOtp());
        saveOtp=otpRepo.findByOtp(otpRequest.getOtp());
        
        if(saveOtp!=null)
        {
        	if(saveOtp.getOtp()==otpRequest.getOtp())
        	{
        		responseModel.setStatusCode(200);
        		responseModel.setStatusMessage("Sucessfull register  Otp verify");
        		responseModel.setTransactionId("");
        	}
        	else
        	{
        		responseModel.setStatusCode(303);
        		responseModel.setStatusMessage("Otp not match please enter again");
        		responseModel.setTransactionId("");
        		
        	}
        }
        else
        {
        	responseModel.setStatusCode(304);
    		responseModel.setStatusMessage("User Not Register please register again");
    		responseModel.setTransactionId("");
        }
		return responseModel;
	}

	@Override
	public StatusDescriptionModel updateUserPass(String encodepass,String email) {
		
		StatusDescriptionModel responseModel =new StatusDescriptionModel();
		User user=userRepository.findByEmail(email);
		System.out.println("before update pass encodepass:"+encodepass+"email : "+email);
		
		
		if(user!=null)
		{
			
			int updateUserStatus=userRepository.updateUserPass(encodepass,email,email);
		     System.out.println("get userUpdate pass status is"+updateUserStatus);
			if(updateUserStatus!=-1)
			{
				//System.out.println("after update pass encodepass:"+encodepass+"email : "+email);
				responseModel.setStatusCode(200);
				responseModel.setStatusMessage("password updated successfully");
				responseModel.setTransactionId("");
				
			}
			else
			{
			
				responseModel.setStatusCode(306);
				responseModel.setStatusMessage("Failed please try again");
				responseModel.setTransactionId("");
			}
			
		}
		else
		{
			responseModel.setStatusCode(305);
			responseModel.setStatusMessage("User not Exist");
			responseModel.setTransactionId("");
		}
		// TODO Auto-generated method stub
		return responseModel;
	}

}
