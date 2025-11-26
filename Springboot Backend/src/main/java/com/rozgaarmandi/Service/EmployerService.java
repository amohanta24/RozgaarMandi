package com.rozgaarmandi.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.JobPostedByEmployerResponse;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Models.WorkerJobApplication;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Repositories.EmployerRepository;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.Repositories.WorkerRepository;
import com.rozgaarmandi.ResponseDTO.EmployerResponseDTO;
import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;
import com.rozgaarmandi.Utils.MapperUtils;
@Service
public class EmployerService {
	
	@Autowired
	private JwtService jwt;
	
	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private EmployerRepository empRepo;
	
	@Autowired
	private WorkerRepository workerRepo;
	
	public Employer getEmployerById(int id) throws BusinessValidationException {
		
		Optional<Employer> emp = empRepo.findById(id);
		if(emp.isEmpty())
			throw new BusinessValidationException(400, "Employer not found");
		
		return emp.get();
	}
	

	public List<JobPostedByEmployerResponse> getJobsByEmployer(String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		List<Job> postedJobs = employer.getPostedJobs().stream().filter(obj-> obj.getStatus().equals(JobStatus.OPEN)).sorted(Comparator.comparing(Job::getCreatedOn).reversed()).toList();
		
		List<JobResponseDTO> jobs = MapperUtils.jobToJobResponseDTO(postedJobs);
		
		List<JobPostedByEmployerResponse> responseJobList = jobs.stream().map(job -> {
			JobPostedByEmployerResponse response = new JobPostedByEmployerResponse();
			response.setJob(job);
			response.setApplicants(MapperUtils.workerToWorkerResponseDTO(workerRepo.findByIdIn(job.getAppliedWorkerIds())));
			return response;
		}).toList();
		
		return responseJobList;
		
		
		
	}

	public List<Worker> getApplicantsForJob(int jobId, String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepo.findById(jobId);
		if(job.isPresent()) {
			if(job.get().getEmployer().getId()==employer.getId()) {
				List<Worker> appliedWorkers = job.get().getJobApplications().stream()
						.map(WorkerJobApplication::getWorker).toList();
				return appliedWorkers;
			}
			throw new BusinessValidationException(401, "You are not authorized to view these details");
		}
		throw new BusinessValidationException(400, "Job does not exist");
	}

	public List<JobPostedByEmployerResponse> getAssignedJobsByEmployer(String header) throws BusinessValidationException {
	    Employer employer = (Employer) jwt.getUserFromHeader(header);
	    List<Job> assignedJobs = jobRepo.findByEmployerAndStatus(employer, Job.JobStatus.IN_PROGRESS);
	    List<JobResponseDTO> jobs = MapperUtils.jobToJobResponseDTO(assignedJobs);
	    
	    List<JobPostedByEmployerResponse> responseJobList = jobs.stream().map(job -> {
			JobPostedByEmployerResponse response = new JobPostedByEmployerResponse();
			response.setJob(job);
			response.setApplicants(MapperUtils.workerToWorkerResponseDTO(workerRepo.findByIdIn(job.getAppliedWorkerIds())));
			return response;
		}).toList();
	    
	    return responseJobList;
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
