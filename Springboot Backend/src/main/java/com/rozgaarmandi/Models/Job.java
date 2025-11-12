package com.rozgaarmandi.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rozgaarmandi.Model.WorkerJobApplication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Job {

	public static enum JobStatus {
			OPEN,
		    IN_PROGRESS,
		    WORK_DONE,
		    COMPLETED,
		    CANCELLED;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobId;

	@ManyToOne
	@JoinColumn(name = "employer_id")
	private Employer employer;

	@ManyToMany
	@JoinTable(name ="WORKER_JOB_APPLICATIONS", joinColumns =  @JoinColumn(name ="JOB_ID"), inverseJoinColumns = @JoinColumn(name = "WORKER_ID"))
	private List<Worker> appliedWorkers;

	@ManyToOne
	@JoinColumn(name = "worker_id")
	private Worker assignedWorker;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkerJobApplication> jobApplications = new ArrayList<>();


	private String jobTitle;
	private String jobDescription;
	private String duration;
	private String location;
	private Double pay;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JobStatus status;

	@CreationTimestamp
	private Timestamp createdOn;
	@UpdateTimestamp
	private Timestamp updatedOn;
	
	@OneToMany(mappedBy = "job")
	private List<Review> reviews;
	
	@OneToOne(mappedBy = "job")
	private Payment payment;
	
	private String isActive;

}
