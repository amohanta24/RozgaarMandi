package com.rozgaarmandi.Models;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_TABLE")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class UserInfo {
	
	public enum Role {
		WORKER, EMPLOYER, ADMIN
	};
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false , unique = true)
	private String username;
	private String password;
	@Column(nullable = false , unique = true)
	private String phoneNumber;
	@Column(nullable = false , unique = true)
	private String email;
	
	private Boolean isActive;
	private Boolean isEmailVerified = false;
	private Boolean isPhoneVerified = false;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	@CreationTimestamp
	private Timestamp createdOn;
	@UpdateTimestamp
	private Timestamp updateOn;
	
	private double rating;
	
	@OneToMany(mappedBy = "reviewee")
	private List<Review> receivedReviews;
	
	@OneToMany(mappedBy = "reviewer")
	private List<Review> writtenReviews;
	

}
