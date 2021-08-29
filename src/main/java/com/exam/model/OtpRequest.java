package com.exam.model;

public class OtpRequest {
	
	
	private int otp;
	private String username;
	private String email;
	public OtpRequest() {
		
	}
	public OtpRequest(int otp, String username, String email) {
		
		this.otp = otp;
		this.username = username;
		this.email = email;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "OtpRequest [otp=" + otp + ", username=" + username + ", email=" + email + "]";
	}
	
	
	

}
