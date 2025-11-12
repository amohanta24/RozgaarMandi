package com.rozgaarmandi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Repositories.EmployerRepository;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.ResponseDTO.EmployerResponseDTO;
@Service
public class EmployerService {
	
	@Autowired
	private JwtService jwt;
	
	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private EmployerRepository empRepo;
	
	public Employer getEmployerById(int id) throws BusinessValidationException {
		
		Optional<Employer> emp = empRepo.findById(id);
		if(emp.isEmpty())
			throw new BusinessValidationException(400, "Employer not found");
		
		return emp.get();
	}
	

	public List<Job> getJobsByEmployer(String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		List<Job> postedJobs = employer.getPostedJobs();
		return postedJobs;
	}

	public List<Worker> getApplicantsForJob(int jobId, String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepo.findById(jobId);
		if(job.isPresent()) {
			if(job.get().getEmployer().getId()==employer.getId()) {
				List<Worker> appliedWorkers = job.get().getAppliedWorkers();
				return appliedWorkers;
			}
			throw new BusinessValidationException(401, "You are not authorized to view these details");
		}
		throw new BusinessValidationException(400, "Job does not exist");
	}

	public List<Job> getAssignedJobsByEmployer(String header) throws BusinessValidationException {
	    Employer employer = (Employer) jwt.getUserFromHeader(header);
	    List<Job> jobs = jobRepo.findByEmployerAndStatus(employer, Job.JobStatus.IN_PROGRESS);
	    return jobs;
	}


	public List<Payment> getPaymentDetails(int employerId) throws BusinessValidationException {
		Optional<Employer> employerById = empRepo.findById(employerId);
		if(employerById.isEmpty())
			throw new BusinessValidationException(400, "Employer Not found");
		return employerById.get().getPayments();
	}


	public List<Job> getCompletedJobs(String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		List<Job> postedJobs = employer.getPostedJobs().stream().filter(obj-> obj.getStatus().equals(JobStatus.WORK_DONE) || obj.getStatus().equals(JobStatus.COMPLETED)).toList();
		return postedJobs;
	}

	

}
