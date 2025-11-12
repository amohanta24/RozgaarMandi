package com.rozgaarmandi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Repositories.UserRepository;

@Service
public class UserInfoService {
	
	@Autowired
	private JwtService jwt;
	@Autowired
	private UserRepository userRepo;
	
	
	public UserInfo getUserById(int id) throws BusinessValidationException {
		Optional<UserInfo> userById = userRepo.findById(id);
		if(userById.isEmpty())
			throw new BusinessValidationException(400, "User not found");
		
		return userById.get();
	}


	public UserInfo getUser(String header) throws BusinessValidationException {
		UserInfo userFromHeader = jwt.getUserFromHeader(header);
		return userFromHeader;
	}

}
