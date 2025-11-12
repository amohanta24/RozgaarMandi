package com.rozgaarmandi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.RequestDTO.SignUpDTO;
import com.rozgaarmandi.RequestDTO.loginDTO;
import com.rozgaarmandi.ResponseDTO.UserResponseDTO;
import com.rozgaarmandi.Service.LoginService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/public")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public Object login(@RequestBody loginDTO login) {
		return this.loginService.login(login);
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<UserResponseDTO> SignUp(@RequestBody SignUpDTO request) throws BusinessValidationException {
		 UserInfo userInfo = this.loginService.signUp(request);
		 UserResponseDTO response = MapperUtils.userToUserResponseDTO(userInfo).get(0);
		 return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
