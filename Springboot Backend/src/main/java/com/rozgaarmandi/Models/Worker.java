package com.rozgaarmandi.Models;

import java.util.ArrayList;
import java.util.List;

import com.rozgaarmandi.Model.WorkerJobApplication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="WORKER")
@Data
public class Worker extends UserInfo {
	
	
	private List<String> skills;
	private String location;
	
	@ManyToMany(mappedBy = "appliedWorkers")
	private List<Job> appliedJobs;
	
	
	@OneToMany(mappedBy ="assignedWorker")
	private List<Job> assignedJobs;
	
	@OneToMany(mappedBy = "worker")
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkerJobApplication> jobApplications = new ArrayList<>();
	
	private String firstName;
	private String lastName;
	
} 
