package com.rozgaarmandi.Controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.RequestDTO.JobRequestDTO;
import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.PaymentResponseDTO;
import com.rozgaarmandi.Service.JobService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobService jobService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/getJob/{id}")
	public ResponseEntity<JobResponseDTO> getJob(@PathVariable int id) throws BusinessValidationException {
		Job jobById = jobService.getJobById(id);
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(jobById).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EMPLOYER')")
	@PostMapping("/postJob")
	public ResponseEntity<JobResponseDTO> postJob(@RequestBody JobRequestDTO request, @RequestHeader("Authorization") String header) throws BusinessValidationException {
		Job job = mapper.map(request, Job.class);
		Job savedJob = jobService.postjob(job, header);
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(savedJob).get(0);
		return new ResponseEntity<JobResponseDTO>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('WORKER')")
	@PutMapping("/apply/{jobId}")
	public ResponseEntity<JobResponseDTO> applyJob(@PathVariable int jobId, @RequestHeader("Authorization") String header) throws BusinessValidationException {
		Job appliedJob = jobService.applyJob(jobId, header);
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(appliedJob).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('WORKER')")
	@GetMapping("/openJobs")
	public ResponseEntity<List<JobResponseDTO>> getOpenJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
		List<Job> allOpenJob = this.jobService.getAllOpenJob(header);
		List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(allOpenJob);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EMPLOYER')")
	@PutMapping("/assign")
	public ResponseEntity<JobResponseDTO> assignJob(@RequestParam String jobId, @RequestParam String workerId,
			@RequestHeader("Authorization") String header) throws BusinessValidationException {
		Job assignedJob = jobService.assignJob(Integer.valueOf(jobId), header, Integer.valueOf(workerId));
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(assignedJob).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('EMPLOYER')")
	@DeleteMapping("/delete")
	public Object deleteJob(@RequestParam int jobId, @RequestHeader("Authorization") String header)
			throws BusinessValidationException {
		return jobService.deleteJob(jobId, header);
	}

	@PreAuthorize("hasAuthority('EMPLOYER')")
	@PutMapping("/update")
	public ResponseEntity<JobResponseDTO> updateJob(@RequestParam int jobId,
			@RequestHeader("Authorization") String header, @RequestBody JobResponseDTO request)
			throws BusinessValidationException {
		Job newJob = mapper.map(request, Job.class);
		Job updatedJob = jobService.updateJob(jobId, header, newJob);
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(updatedJob).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('WORKER') || hasAuthority('EMPLOYER')")
	@PutMapping("/changeStatus")
	public ResponseEntity<JobResponseDTO> changeStatus(@RequestParam int jobId,
			@RequestHeader("Authorization") String header, @RequestParam JobStatus jobStatus)
			throws BusinessValidationException {
		Job changedJob = jobService.changeStatus(jobId, jobStatus, header);
		JobResponseDTO response = MapperUtils.jobToJobResponseDTO(changedJob).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/paymentDetails/{jobId}")
	public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable int jobId)
			throws BusinessValidationException {
		Payment payment = jobService.getPaymentDetails(jobId);
		PaymentResponseDTO response = MapperUtils.paymentToPaymentResponseDTO(payment).get(0);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
