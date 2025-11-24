package com.rozgaarmandi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.JobPostedByEmployerResponse;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.ResponseDTO.EmployerResponseDTO;
import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.PaymentResponseDTO;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;
import com.rozgaarmandi.Service.EmployerService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/employer")
public class EmployerController {
	
	@Autowired
	private EmployerService employerService;
	
	@GetMapping("getEmployer/{id}")
	public ResponseEntity<EmployerResponseDTO> getEmployerById(@PathVariable int id) throws BusinessValidationException{
		Employer employerById = employerService.getEmployerById(id);
		EmployerResponseDTO response = MapperUtils.employerToEmployerResponseDTO(employerById).get(0);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	@GetMapping("/jobs")
    public ResponseEntity<List<JobPostedByEmployerResponse>> getMyJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
            List<JobPostedByEmployerResponse> jobsByEmployer = employerService.getJobsByEmployer(header);
            return new ResponseEntity<>(jobsByEmployer, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	@GetMapping("/job/applicants")
    public  ResponseEntity<List<WorkerResponseDTO>> getApplicants(@RequestParam int jobId, @RequestHeader("Authorization") String header) throws BusinessValidationException {
         List<Worker> applicantsForJob = employerService.getApplicantsForJob(jobId, header);
         List<WorkerResponseDTO> response = MapperUtils.workerToWorkerResponseDTO(applicantsForJob);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	@GetMapping("/jobs/assigned")
    public ResponseEntity<List<JobResponseDTO>> getAssignedJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
         List<Job> assignedJobsByEmployer = employerService.getAssignedJobsByEmployer(header);
         List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(assignedJobsByEmployer);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	 @GetMapping("/completedJobs")
	    public ResponseEntity<List<JobResponseDTO>> getCompletedJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
	        List<Job> completedJobs = employerService.getCompletedJobs(header);
	        List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(completedJobs);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	 }
	
	@PreAuthorize("hasAuthority('EMPLOYER')")
	@GetMapping("/paymentDetails/{employerId}")
	public ResponseEntity<List<PaymentResponseDTO>> getPaymentDetails(@PathVariable int employerId) throws BusinessValidationException{
		List<Payment> paymentDetails = employerService.getPaymentDetails(employerId);
		List<PaymentResponseDTO> response = MapperUtils.paymentToPaymentResponseDTO(paymentDetails);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}
