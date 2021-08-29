package com.exam.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OtpStore {
	
	@Id
	private long userId;
	private String username;
	private int otp;
	
	public OtpStore() {
	
	}
	public OtpStore(int userId, String username, int otp) {
		
		this.userId = userId;
		this.username = username;
		this.otp = otp;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	

}
