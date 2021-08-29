package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.OtpRequest;
import com.exam.model.Role;
import com.exam.model.StatusDescriptionModel;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.apache.coyote.Response;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Multipart;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * creating user
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public ResponseEntity<StatusDescriptionModel> createUser(@RequestBody User user) throws Exception {

		StatusDescriptionModel responseModel=new StatusDescriptionModel();
		user.setProfile("default.png");
		// encoding password with bcryptpasswordencoder

		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		Set<UserRole> roles = new HashSet<>();

		Role role = new Role();
		role.setRoleId(45L);
		role.setRoleName("NORMAL");

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);

		roles.add(userRole);
		responseModel=this.userService.createUser(user, roles);

		return new ResponseEntity<StatusDescriptionModel>(responseModel,HttpStatus.OK);

	}

	/**
	 * get user for db
	 * 
	 * @param username
	 * @return
	 */
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}

	/**
	 * delete the user by id
	 * 
	 * @param userId
	 */
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}

	/**
	 * update api
	 * 
	 * @param ex
	 * @return
	 */

	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
		return ResponseEntity.ok(ex.getMessage());
	}

	/**
	 * update Profile
	 * 
	 * @param user
	 * @return
	 */

	@PostMapping(value = "/profile/update")
	public ResponseEntity<?> updateProfile(@RequestBody User user) {

		return null;

	}
	
	       @PostMapping(value="/verifyOtp")
	    public ResponseEntity<StatusDescriptionModel> verifyUserOtp(@RequestBody OtpRequest otpRequest)
		{
	    	   
	    	   System.out.println("get Data from otpRequest"+otpRequest.getOtp()+"user"+otpRequest.getUsername()+"email"+otpRequest.getEmail());
	    	   StatusDescriptionModel otpResponse=userService.verisfyUserOtp(otpRequest);
	    	   return new ResponseEntity<StatusDescriptionModel>(otpResponse,HttpStatus.OK);
	    	   
		}
	       
	       
	   @PostMapping(value="/update/password")
	   public ResponseEntity<StatusDescriptionModel> updateUserPassword(@RequestBody User user)
	   {
		   StatusDescriptionModel responseModel =new StatusDescriptionModel();
		  String encodepass= bCryptPasswordEncoder.encode(user.getPassword());
		  user.setPassword(encodepass);
		   System.out.println("userPass"+encodepass);
		   responseModel=userService.updateUserPass(encodepass,user.getEmail());
		   return new ResponseEntity<StatusDescriptionModel>(responseModel,HttpStatus.OK);
	   }
	   @PostMapping(value="/pdfUpload")
	public ResponseEntity<StatusDescriptionModel>upoadandSavePdfFile(@RequestPart("studyMaterialPdf") MultipartFile file)
	   {
		   String uploadFileStatus = null;
		 // Path root = Paths.get("uploads");
		  String basePath = "D:/Spring_tool_suite_workspace/examserver/uplods/";
		  if (file!= null && !file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String newFileName = "pdfDoc"+fileName;
				System.out.println("filename"+newFileName);
				//obj.setThirdDocumentPath(actualPath + newFileName);
				uploadFileStatus=uploadFile(basePath, newFileName, file);
				System.out.println("uploadFileStatus"+uploadFileStatus);
			}
					
			
		  
		   StatusDescriptionModel responseModel=new StatusDescriptionModel();
		   if(uploadFileStatus.equals("success"))
		   {
			   responseModel.setStatusCode(200);
			   responseModel.setStatusMessage("file upload successfully");
			   responseModel.setTransactionId("");
			   //return responseModel
			   
		   }
		   else
		   {
		   responseModel.setStatusCode(304);
		   responseModel.setStatusMessage("file upload failed");
		   responseModel.setTransactionId("");
		   }
		   
		   return new ResponseEntity<StatusDescriptionModel>(responseModel,HttpStatus.OK);
		   
		   
	   }
	
	   
	   private String uploadFile(final String finalUploadFolder, final String finalFileName, final MultipartFile file) {
			String uploadStatus = null;
			try {

				if (file != null && !file.isEmpty()) {

					File dir = new File(finalUploadFolder);
					if (!dir.isDirectory()) {
						dir.mkdirs();
					}
					byte[] bytes = file.getBytes();
					String finalFile = finalUploadFolder + finalFileName;
					Path path = Paths.get(finalFile);
					Files.write(path, bytes);
					uploadStatus = "success";
					System.out.println("uploaded success");
					System.out.println(finalFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return uploadStatus;
		}

	

   
	
    

}
