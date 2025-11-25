package com.rozgaarmandi.Utils;

import java.util.List;
import java.util.OptionalDouble;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Review;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.ResponseDTO.EmployerResponseDTO;
import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.PaymentResponseDTO;
import com.rozgaarmandi.ResponseDTO.ReviewResponseDTO;
import com.rozgaarmandi.ResponseDTO.UserResponseDTO;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;

@Service
public class MapperUtils{
	
	private static ModelMapper modelMapper;
	
	@Autowired
	private void modelMapper(ModelMapper mapper) {
		modelMapper = mapper;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<JobResponseDTO> jobToJobResponseDTO(Object jobOrList) {
	    List<Job> jobList = jobOrList instanceof List<?> job ? 
	                        (List<Job>) job : 
	                        List.of((Job) jobOrList);

	    return jobList.stream().map(obj -> {
	        JobResponseDTO dto = modelMapper.map(obj, JobResponseDTO.class);

	        setWorkerIds(obj, dto);
	        dto.setEmployerId(obj.getEmployer() != null ? obj.getEmployer().getId() : null);
	        dto.setReviewsIds(obj.getReviews() != null ? obj.getReviews().stream().map(Review::getReviewId).toList() : null);

	        return dto;
	    }).toList();
	}

	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	private static void setWorkerIds(Job obj, JobResponseDTO dto) {
		dto.setAppliedWorkerIds(obj.getAppliedWorkers() != null ? obj.getAppliedWorkers().stream().map(Worker::getId).toList() : null);
		dto.setAssignedWorkerId(obj.getAssignedWorker() != null ? obj.getAssignedWorker().getId() : null);
	}

	@SuppressWarnings("unchecked")
	public static List<WorkerResponseDTO> workerToWorkerResponseDTO(Object workerOrList) {
	    List<Worker> workerList = workerOrList instanceof List<?> worker ? 
	                              (List<Worker>) worker : 
	                              List.of((Worker) workerOrList);

	    return workerList.stream().map(obj -> {
	    	
	        WorkerResponseDTO dto = modelMapper.map(obj, WorkerResponseDTO.class);
	        
			OptionalDouble averageRating = obj.getAssignedJobs().stream()
					.filter(job -> job.getStatus().equals(JobStatus.COMPLETED)).map(Job::getReviews)
					.flatMap(List::stream).mapToDouble(Review::getRating).average();

	        dto.setRating(averageRating.isPresent() ? averageRating.getAsDouble() : null);
	        dto.setWorkerId(obj.getId());
	        return dto;
	    }).toList();
	}

	
	@SuppressWarnings("unchecked")
	public static List<ReviewResponseDTO> reviewToReviewResponseDTO(Object reviewOrList) {
	    List<Review> reviewList = reviewOrList instanceof List<?> review ? 
	                              (List<Review>) review : 
	                              List.of((Review) reviewOrList);

	    return reviewList.stream().map(obj -> {
	        ReviewResponseDTO dto = modelMapper.map(obj, ReviewResponseDTO.class);

	        dto.setRevieweeId(obj.getReviewee() != null ? obj.getReviewee().getId() : null);
	        dto.setReviewerId(obj.getReviewer() != null ? obj.getReviewer().getId() : null);

	        return dto;
	    }).toList();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<EmployerResponseDTO> employerToEmployerResponseDTO(Object employerOrList) {
		
		List<Employer> employerList = employerOrList instanceof List<?> employer ? (List<Employer>) employer : List.of((Employer) employerOrList);
		return employerList.stream().map(obj -> {
			EmployerResponseDTO dto = modelMapper.map(obj, EmployerResponseDTO.class);
			
			dto.setPaymentIds(obj.getPayments()!=null ? obj.getPayments().stream().map(Payment::getId).toList() : null);
			dto.setPostedJobsIds(obj.getPostedJobs()!=null ? obj.getPostedJobs().stream().map(Job::getJobId).toList() : null);
			dto.setReceivedReviewIds(obj.getReceivedReviews() != null ? obj.getReceivedReviews().stream().map(Review::getReviewId).toList() : null);
			dto.setWrittenReviewIds(obj.getWrittenReviews() !=null ? obj.getWrittenReviews().stream().map(Review::getReviewId).toList() : null);
			
			return dto;
			
		}).toList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<UserResponseDTO> userToUserResponseDTO(Object userOrList){
		List<UserInfo> userList = userOrList instanceof List<?> user ? (List<UserInfo>) user : List.of((UserInfo) userOrList);
		
		return userList.stream().map(obj->{
			UserResponseDTO dto = modelMapper.map(obj, UserResponseDTO.class);
			
			dto.setReceivedReviewIds(obj.getReceivedReviews()!=null ? obj.getReceivedReviews().stream().map(Review::getReviewId).toList(): null);
			dto.setWrittenReviewIds(obj.getWrittenReviews()!=null ? obj.getWrittenReviews().stream().map(Review::getReviewId).toList(): null);
			return dto;
			
		}).toList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<PaymentResponseDTO> paymentToPaymentResponseDTO(Object paymentOrList){
		List<Payment> paymentList = paymentOrList instanceof List<?> payment ? (List<Payment>) payment : List.of((Payment) paymentOrList);
		
			return paymentList.stream().map(obj-> {
				
			PaymentResponseDTO dto = modelMapper.map(obj, PaymentResponseDTO.class);
			
			dto.setEmployerId(obj.getEmployer()!=null ? obj.getEmployer().getId() : null);
			dto.setJobId(obj.getJob()!=null ? obj.getJob().getJobId() : null);
			dto.setWorkerId(obj.getWorker()!=null ? obj.getWorker().getId() : null);
			
			return dto;
			
		}).toList();
	}
	
}
