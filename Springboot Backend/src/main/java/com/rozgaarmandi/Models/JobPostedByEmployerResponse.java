package com.rozgaarmandi.Models;

import java.util.List;

import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;

import lombok.Data;


@Data
public class JobPostedByEmployerResponse {
	
	  
	private JobResponseDTO job;
	private List<WorkerResponseDTO> applicants;
	
}
