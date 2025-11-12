package com.rozgaarmandi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Models.UserInfoUserDetails;
import com.rozgaarmandi.Repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
	

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserInfo> user =  userRepo.findByIsActiveAndEmailOrPhoneNumber(true,username,username);
		if(user.isEmpty()) {
			log.error("FAILED LOGIN ATTEMPT --- "+ username + "not found");
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserInfoUserDetails(user.get());
	}

	

	
	
	
}
