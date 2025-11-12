package com.rozgaarmandi.Models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Employer extends UserInfo {
	
	public static enum EmployerType{
		INDIVIDUAL, BUSINESS
	}
	
	
	@OneToMany(mappedBy = "employer")
	private List<Job> postedJobs;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
    private EmployerType employerType;   
	
	@OneToMany(mappedBy = "employer")
	private List<Payment> payments;
    
    private String contactPersonName;
    private String contactPersonNumber;
    private String gstNumber;
    private String businessDescription;
    private String businessName;
    
    private String firstName;
    private String lastName;
    
}
