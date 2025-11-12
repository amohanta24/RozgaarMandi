package com.rozgaarmandi.RequestDTO;

import lombok.Data;

@Data
public class JobRequestDTO {
	
	private String jobTitle;
	private String jobDescription;
	private String duration;
	private String location;
	private Double pay;

}
