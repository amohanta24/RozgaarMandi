package com.rozgaarmandi.DataLoader;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.RequestDTO.JobRequestDTO;
import com.rozgaarmandi.RequestDTO.SignUpDTO;
import com.rozgaarmandi.Service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LoginService loginService;

	@Override
	public void run(ApplicationArguments args){
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<JobRequestDTO> jobList = mapper.readValue(this.getJobs(), new TypeReference<List<JobRequestDTO>>() {
			});
			SignUpDTO workerSignup = mapper.readValue(this.getWorker(), SignUpDTO.class);
			SignUpDTO employerIndSignup = mapper.readValue(this.getEmployerInd(), SignUpDTO.class);
			SignUpDTO employerBusinessSignup = mapper.readValue(this.getEmployerBusiness(), SignUpDTO.class);
			
			

			UserInfo worker = loginService.signUp(workerSignup);
			UserInfo employerInd = loginService.signUp(employerIndSignup);
			UserInfo employerBusinessInd = loginService.signUp(employerBusinessSignup);
			


			List<Job> list = jobList.stream().map(obj -> {
				Job job = modelMapper.map(obj, Job.class);
				job.setStatus(JobStatus.OPEN);
				job.setEmployer((Employer) employerInd);
				return job;
			}).toList();

			jobRepo.saveAll(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}

	String getWorker() {
		return """
				{
				  "username": "rajesh_kumar",
				  "password": "Rajesh@123",
				  "email": "rajesh.kumar@example.com",
				  "phoneNumber": "9876543210",
				  "role": "WORKER",
				  "workerFirstName": "Rajesh",
				  "workerLastName": "Kumar",
				  "skills": "Plumbing, Electrical Repair, Tile Fitting"
				}
				""";
	}

	String getEmployerInd() {
		return """
				{
				  "username": "mohan_singh",
				  "password": "Mohan@123",
				  "email": "mohan.singh@example.com",
				  "phoneNumber": "9876543215",
				  "role": "EMPLOYER",
				  "employerFirstName": "Mohan",
				  "employerLastName": "Singh",
				  "employerType": "INDIVIDUAL",
				  "contactPersonName": "Mohan Singh",
				  "contactPersonNumber": "9876543215",
				  "businessDescription": "Looking for electrician and plumber for house work"
				}

				""";
	}

	String getEmployerBusiness() {
		return """
				  {
				  "username": "sharma_enterprises",
				  "password": "Sharma@123",
				  "email": "info@sharmaenterprises.com",
				  "phoneNumber": "9123456789",
				  "role": "EMPLOYER",
				  "employerFirstName": "Rakesh",
				  "employerLastName": "Sharma",
				  "employerType": "BUSINESS",
				  "contactPersonName": "Rakesh Sharma",
				  "contactPersonNumber": "9123456789",
				  "gstNumber": "29ABCDE1234F1Z5",
				  "businessDescription": "Small construction firm offering painting and carpentry services",
				  "businessName": "Sharma Enterprises"
				}
				""";
	}

	String getJobs() {
		return """
				[
				  {
				    "jobTitle": "House Painting",
				    "jobDescription": "Repaint walls of a 2BHK flat with premium washable paint.",
				    "duration": "2 days",
				    "location": "Mumbai, Maharashtra",
				    "pay": 2500.0
				  },
				  {
				    "jobTitle": "Electrician Needed",
				    "jobDescription": "Fix wiring issues and install new light fittings in living room.",
				    "duration": "1 day",
				    "location": "Delhi, India",
				    "pay": 1800.0
				  },
				  {
				    "jobTitle": "Gardening Help",
				    "jobDescription": "Maintain small garden, trim hedges, and water plants daily.",
				    "duration": "3 days",
				    "location": "Pune, Maharashtra",
				    "pay": 1500.0
				  },
				  {
				    "jobTitle": "Plumber Required",
				    "jobDescription": "Repair leaking tap and replace bathroom shower head.",
				    "duration": "5 hours",
				    "location": "Bangalore, Karnataka",
				    "pay": 1200.0
				  },
				  {
				    "jobTitle": "Home Cleaning Service",
				    "jobDescription": "Deep clean 2BHK apartment including kitchen and bathroom.",
				    "duration": "1 day",
				    "location": "Chennai, Tamil Nadu",
				    "pay": 2000.0
				  },
				  {
				    "jobTitle": "Carpenter Work",
				    "jobDescription": "Repair a wooden door and assemble a small bookshelf.",
				    "duration": "2 days",
				    "location": "Hyderabad, Telangana",
				    "pay": 2200.0
				  },
				  {
				    "jobTitle": "AC Servicing",
				    "jobDescription": "General AC cleaning and gas refill for one split AC.",
				    "duration": "1 day",
				    "location": "Kolkata, West Bengal",
				    "pay": 1500.0
				  },
				  {
				    "jobTitle": "Cook for Home",
				    "jobDescription": "Need a cook for 2 meals daily for a family of 4 members.",
				    "duration": "30 days",
				    "location": "Ahmedabad, Gujarat",
				    "pay": 15000.0
				  },
				  {
				    "jobTitle": "Tile Fitting Work",
				    "jobDescription": "Install new ceramic tiles in kitchen and bathroom area.",
				    "duration": "4 days",
				    "location": "Lucknow, Uttar Pradesh",
				    "pay": 5000.0
				  },
				  {
				    "jobTitle": "Delivery Assistant",
				    "jobDescription": "Help with local deliveries for small business orders.",
				    "duration": "15 days",
				    "location": "Jaipur, Rajasthan",
				    "pay": 8000.0
				  }
				]
				""";

	}

}
