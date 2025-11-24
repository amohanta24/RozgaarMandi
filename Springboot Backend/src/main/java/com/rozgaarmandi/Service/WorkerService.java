package com.rozgaarmandi.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Models.WorkerJobApplication;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.Repositories.WorkerRepository;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;

@Service
public class WorkerService {
	
	@Autowired
	private JobRepository jobRepo;
	@Autowired
	private WorkerRepository workerRepo;
	
	@Autowired
	private JwtService jwt;
	

	public  List<Job> getAppliedJobsByWorker(String header) throws BusinessValidationException {
		Worker worker = (Worker) jwt.getUserFromHeader(header);
		
		List<Job> appliedJobs = worker.getJobApplications().stream()
				.sorted(Comparator.comparing(WorkerJobApplication::getUpdatedOn).reversed())
				.map(WorkerJobApplication::getJob).filter(obj-> obj.getStatus().equals(JobStatus.OPEN)).toList();
		
		return appliedJobs;
	}

	public List<Job> getAssignedJobsByWorker(String header) throws BusinessValidationException {
		Worker worker = (Worker) jwt.getUserFromHeader(header);
		List<Job> assignedJobs = worker.getAssignedJobs().stream().filter(obj -> obj.getStatus().equals(JobStatus.IN_PROGRESS)
				|| obj.getStatus().equals(JobStatus.WORK_DONE)).toList();
		
		return assignedJobs;
	}
	
	public List<Job> getCompletedJobs(String header) throws BusinessValidationException {
		Worker worker = (Worker) jwt.getUserFromHeader(header);
		List<Job> completedJobs = worker.getAssignedJobs().stream().filter(obj-> obj.getStatus().equals(JobStatus.COMPLETED)).toList();
		return completedJobs;
	}
	

	public Worker getWorkerById(int id) throws BusinessValidationException {
		Optional<Worker> workerOptional = workerRepo.findById(id);
		if(workerOptional.isEmpty())
			throw new BusinessValidationException(400, "Worker Not found");
		
		return workerOptional.get();
		
	}

	public List<Payment> getPaymentDetailsByWorker(int workerId) throws BusinessValidationException {
			Optional<Worker> workerOptional = workerRepo.findById(workerId);
			if(workerOptional.isEmpty())
				throw new BusinessValidationException(400, "Worker Not found");
			return workerOptional.get().getPayments();
	}

	

}
