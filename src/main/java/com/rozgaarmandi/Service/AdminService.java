package com.rozgaarmandi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Repositories.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepo;

	public Boolean blockUser(int userId) throws BusinessValidationException {
		UserInfo user = userRepo.findById(userId).orElseThrow(()-> new BusinessValidationException(400,"User No found"));
			user.setIsActive(false);
			 UserInfo blockedUser = userRepo.save(user);
			 return blockedUser.getIsActive();
		}
	}
