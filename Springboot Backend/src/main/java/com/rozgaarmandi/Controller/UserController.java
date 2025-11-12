package com.rozgaarmandi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.ResponseDTO.UserResponseDTO;
import com.rozgaarmandi.Service.UserInfoService;
import com.rozgaarmandi.Service.UserService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) throws BusinessValidationException{
		
		UserInfo user = userInfoService.getUserById(id);
		UserResponseDTO response = MapperUtils.userToUserResponseDTO(user).get(0);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<UserResponseDTO> getUser(@RequestHeader("Authorization") String header) throws BusinessValidationException{
		UserInfo user = userInfoService.getUser(header);
		UserResponseDTO response = MapperUtils.userToUserResponseDTO(user).get(0);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
