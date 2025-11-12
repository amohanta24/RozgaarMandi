package com.rozgaarmandi.ResponseDTO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReviewResponseDTO {
	
	private Integer revieweeId;
	private Integer reviewerId;
	private Integer jobId;
	private Double rating;
	private String comment;
	private Timestamp created_on;

}
