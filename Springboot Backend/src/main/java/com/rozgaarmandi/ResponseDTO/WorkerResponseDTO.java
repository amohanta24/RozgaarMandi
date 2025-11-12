package com.rozgaarmandi.ResponseDTO;

import java.util.List;

import com.rozgaarmandi.Models.Payment;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
@Data
public class WorkerResponseDTO extends UserResponseDTO{
	
	private List<String> skills;
	private List<Integer> appliedJobIds;
	private List<Integer> assignedJobIds;
	private List<Integer> paymentIds;
	
}
