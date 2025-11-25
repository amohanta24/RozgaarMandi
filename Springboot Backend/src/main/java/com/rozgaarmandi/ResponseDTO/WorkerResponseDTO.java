package com.rozgaarmandi.ResponseDTO;

import java.util.List;

import lombok.Data;
@Data
public class WorkerResponseDTO extends UserResponseDTO{
	
	private Integer workerId;
	private String skills;
	private List<Integer> appliedJobIds;
	private List<Integer> assignedJobIds;
	private List<Integer> paymentIds;
	
	private String firstName;
	private String lastName;
	private Double rating;
	private String location;
	
	
}
