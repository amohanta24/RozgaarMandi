package com.rozgaarmandi.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer>{

	Optional<UserInfo> findByIsActiveAndEmailOrPhoneNumber(boolean isActive, String email, String phoneNumber);

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

}
