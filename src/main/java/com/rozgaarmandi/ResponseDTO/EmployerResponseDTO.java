package com.rozgaarmandi.ResponseDTO;
import java.util.List;

import com.rozgaarmandi.Models.Employer.EmployerType;

import lombok.Data;
@Data
public class EmployerResponseDTO extends UserResponseDTO{
	
	private List<Integer> postedJobsIds;
	private EmployerType employerType;   
	private List<Integer> paymentIds;
	private String contactPersonName;
    private String contactPersonNumber;
    private String address;     
    private String gstNumber;
    private String businessDescription;
    
}
