package com.rozgaarmandi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Review;
import com.rozgaarmandi.RequestDTO.ReviewRequestDTO;
import com.rozgaarmandi.ResponseDTO.ReviewResponseDTO;
import com.rozgaarmandi.Service.ReviewService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
public class ReviewController {
	

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/getReviewById/{id}")
	public ResponseEntity<ReviewResponseDTO> getReviewById(@RequestParam int id) throws BusinessValidationException {
		 Review review = reviewService.getReviewById(id);
		 ReviewResponseDTO reviewResponseDTO = MapperUtils.reviewToReviewResponseDTO(review).get(0);
		 return new ResponseEntity<>(reviewResponseDTO,HttpStatus.OK);
		 
	} 
	
	@PostMapping("/job/postReview")
	public ResponseEntity<ReviewResponseDTO> postReview(@RequestParam int jobId, @RequestBody ReviewRequestDTO request , @RequestHeader("Authorization") String header) throws BusinessValidationException {
		 Review postedReview = reviewService.postReview(jobId,request, header);
		 ReviewResponseDTO reviewResponseDTO = MapperUtils.reviewToReviewResponseDTO(postedReview).get(0);
		 return new ResponseEntity<>(reviewResponseDTO,HttpStatus.OK);
		 
	}
	
	@GetMapping("/user/getAvgRating")
	public Object getAvgRating(@RequestHeader("Authorization") String header) throws BusinessValidationException {
		return reviewService.getAvgRating(header);
	}
	
	@GetMapping("/user/getAllReviews")
	public ResponseEntity<List<ReviewResponseDTO> > getAllReviews(@RequestHeader("Authorization") String header) throws BusinessValidationException {
		   List<Review> allReviews = reviewService.getAllReviews(header);
		   List<ReviewResponseDTO> response = MapperUtils.reviewToReviewResponseDTO(allReviews);
		   return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
