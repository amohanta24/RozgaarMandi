package com.rozgaarmandi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/blockUser/{userId}")
	public ResponseEntity<String> blockUser(@PathVariable int userId) throws BusinessValidationException{
		   Boolean isUserBlocked = adminService.blockUser(userId);
		   return new ResponseEntity<>(isUserBlocked ? "User Blocked Sucessfully" : "Unable to Block the User", HttpStatus.OK);
	}
}
