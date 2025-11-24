package com.rozgaarmandi.RequestDTO;

import com.rozgaarmandi.Models.UserInfo.Role;

import lombok.Data;

import com.rozgaarmandi.Models.Employer.EmployerType;
import java.util.List;

@Data
public class SignUpDTO {

	// Common user fields
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private Role role;
	
	private String workerFirstName;
	private String workerLastName;
	private String employerFirstName;
	private String employerLastName;

	// Employer-specific fields
	private EmployerType employerType;
	private String contactPersonName;
	private String contactPersonNumber;
	private String gstNumber;
	private String businessDescription;
	private String businessName;
	


	// Worker-specific fields
	private String skills;

}
