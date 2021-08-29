package com.exam.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByPhone(String phone);
    
//    @Modifying
//	@Transactional
//    @Query("update User u set u.password = :password where u.email = :email")
//    int updateUserPass(@Param("password") String password,@Param("email") String email);
   
    @Modifying
    @Transactional
    @Query("update User u set u.password = ?1, u.email = ?2 where u.email = ?3")
    int updateUserPass(@Param("password") String password,@Param("email") String email,@Param("usermail") String userEmail);
    
}
