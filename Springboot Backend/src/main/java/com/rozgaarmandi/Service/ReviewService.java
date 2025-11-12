package com.rozgaarmandi.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.Review;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.Repositories.ReviewRepository;
import com.rozgaarmandi.RequestDTO.ReviewRequestDTO;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepo;
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JwtService jwt;

	public Review postReview(int jobId, ReviewRequestDTO request, String header) throws BusinessValidationException {
		UserInfo reviewer = jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepository.findById(jobId);
		UserInfo reviewee = reviewer instanceof Employer ? job.get().getAssignedWorker() : job.get().getEmployer();
		boolean isValidUser = job.isPresent()
				? (reviewer instanceof Employer ? job.get().getEmployer()!=null && job.get().getEmployer().getId()  == reviewer.getId()
						: job.get().getAssignedWorker() !=null && job.get().getAssignedWorker().getId() == reviewer.getId())
				: false;
		
		if(job.isPresent() && reviewee !=null && reviewer!=null && job.get().getStatus().equals(JobStatus.COMPLETED)) {
			if(isValidUser && this.validateRequest(reviewee, job.get(), reviewer)) {
				Review review = new Review();
				review.setReviewee(reviewee);
				review.setReviewer(reviewer);
				review.setComment(request.getComment());
				review.setJob(job.get());
				review.setRating(request.getRating());
				
				Review savedReview = reviewRepo.save(review);
				return savedReview;
			}
			throw new BusinessValidationException(401, "You are not authorized to post review for this job");
		}
		throw new BusinessValidationException(400, "Job Does not exist or Not yet completed");
	}


	private boolean validateRequest(UserInfo reviewee, Job job, UserInfo reviewer) {
		int revieweeId = reviewee.getId();
		int reviewerId = reviewer.getId();
		return (job.getAssignedWorker().getId() == revieweeId && job.getEmployer().getId() == reviewerId)
				|| (job.getAssignedWorker().getId() == reviewerId && job.getEmployer().getId() == revieweeId);
	}


	public Object getAvgRating(String header) throws BusinessValidationException {
		UserInfo user = jwt.getUserFromHeader(header);
		List<Job> jobs = user instanceof Employer ? jobRepository.findByEmployerAndStatus((Employer) user, JobStatus.COMPLETED) : jobRepository.findByAssignedWorkerAndStatus((Worker) user, JobStatus.COMPLETED);
		OptionalDouble average = jobs.stream().map(Job::getReviews).flatMap(List::stream).mapToDouble(Review::getRating).average();
		return average.isPresent() ? new ResponseEntity<>(average.getAsDouble(), HttpStatus.OK) :  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}
	
	public List<Review>  getAllReviews(String header) throws BusinessValidationException {
		UserInfo user = jwt.getUserFromHeader(header);
		List<Job> jobs = user instanceof Employer ? jobRepository.findByEmployerAndStatus((Employer) user, JobStatus.COMPLETED) : jobRepository.findByAssignedWorkerAndStatus((Worker) user, JobStatus.COMPLETED);
		 List<Review> allReviews = jobs.stream().flatMap(job -> job.getReviews().stream()).collect(Collectors.toList());
		return allReviews;
	}


	public Review getReviewById(int id) throws BusinessValidationException {
		return reviewRepo.findById(id).orElseThrow(()-> new BusinessValidationException(400, "Review Not Found"));
	}

}
