package com.rozgaarmandi.ResponseDTO;

import java.util.List;

import com.rozgaarmandi.Models.Job.JobStatus;

import lombok.Data;

@Data
public class JobResponseDTO {
	
	private int jobId;
	private EmployerResponseDTO employer;
	private List<Integer> appliedWorkerIds;
	private Integer assignedWorkerId;
	private String jobTitle;
	private String jobDescription;
	private String duration;
	private String location;
	private Double pay;
	private JobStatus status;
	private List<Integer> reviewsIds;
	
}
