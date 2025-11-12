package com.rozgaarmandi.Models;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;
	
	@ManyToOne
	@JoinColumn(name = "reviewee_id")
	private UserInfo reviewee;
	
	@ManyToOne
	@JoinColumn(name = "reviewer_id")
	private UserInfo reviewer;
	
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	private Double rating;
	
	@CreationTimestamp
	private Timestamp created_on;


}
