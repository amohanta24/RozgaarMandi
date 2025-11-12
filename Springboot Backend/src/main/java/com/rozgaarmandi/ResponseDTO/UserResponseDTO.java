package com.rozgaarmandi.ResponseDTO;

import java.util.List;

import com.rozgaarmandi.Models.UserInfo.Role;

import lombok.Data;
@Data
public class UserResponseDTO {
	
	private String username;
	private String phoneNumber;
	private String email;
	private Role role;
	private List<Integer> receivedReviewIds;
	private List<Integer> writtenReviewIds;
}