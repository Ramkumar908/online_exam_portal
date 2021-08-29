package com.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.OtpStore;

public interface OtpStoreRepo extends JpaRepository<OtpStore,Long>{

	
//	public OtpStore findByOtp(String username);

	public OtpStore findByOtp(int otp);
}
