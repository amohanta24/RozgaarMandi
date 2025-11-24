package com.rozgaarmandi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Models.UserInfo.Role;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Models.WorkerJobApplication;
import com.rozgaarmandi.Repositories.JobRepository;
import com.rozgaarmandi.Repositories.WorkerRepository;

import ch.qos.logback.core.util.StringUtil;

@Service
public class JobService {


	@Autowired
	private JwtService jwt;
	
	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private WorkerRepository workerRepo;

	public Job postjob(Job job, String header) throws BusinessValidationException {
	  UserInfo userFromHeader = jwt.getUserFromHeader(header);
		job.setEmployer((Employer) userFromHeader);
		job.setStatus(JobStatus.OPEN);
		Job savedJob = jobRepo.save(job);
		return savedJob;
	}
	
	public Job applyJob(int jobId, String header) throws BusinessValidationException {
		Worker worker = (Worker) jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepo.findById(jobId);

		if (job.isPresent()) {
			if (job.get().getStatus().equals(JobStatus.OPEN)) {
				if (!job.get().getAppliedWorkers().contains(worker)) {
					WorkerJobApplication app = new WorkerJobApplication();
					app.setJob(job.get());
					app.setWorker(worker);
					job.get().getJobApplications().add(app);
					Job savedJob = jobRepo.save(job.get());
					
					return savedJob;
				}
				throw new BusinessValidationException(403, "You have already applied to this job");
			}
			throw new BusinessValidationException(403, "Job is not Open to apply");
		}
		throw new BusinessValidationException(400, "Job does not exist");
	}
	
	public List<Job> getAllOpenJob(String header) throws BusinessValidationException {
		Worker worker = (Worker) jwt.getUserFromHeader(header);
		List<Job> openJobs = jobRepo.findByStatusOrderByCreatedOnDesc(JobStatus.OPEN).stream().filter(obj -> !obj.getAppliedWorkers().contains(worker)).toList();
		return openJobs;
	}
	
	public Job assignJob(int jobId, String header, int workerId) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepo.findById(jobId);
		Optional<Worker> worker = workerRepo.findById(workerId);
		if(job.isPresent() && worker.isPresent()) {
			if(job.get().getEmployer().getId()==employer.getId()) {
				job.get().setStatus(JobStatus.IN_PROGRESS);
				job.get().setAssignedWorker(worker.get());
				Job savedJob = jobRepo.save(job.get());
				return savedJob;
			}
			throw new BusinessValidationException(403, "You are not authorized to assign this job");
		}
		
		throw new BusinessValidationException(400, "Job or Worker does not exist");
	}
	
	public Object deleteJob(int jobId, String header) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		Optional<Job> job = jobRepo.findById(jobId);
		if(job.isPresent()) {
			if(job.get().getEmployer().getId() == employer.getId()) {
				jobRepo.deleteById(jobId);
				return ResponseEntity.status(HttpStatus.OK).body("Job deleted successfully");
			}
			throw new BusinessValidationException(401, "You are not authorized to delete this job");
		}
		throw new BusinessValidationException(400, "Job does not exist");
	}
	
	
	
	public Job updateJob(int jobId, String header, Job newJob) throws BusinessValidationException {
		Employer employer = (Employer) jwt.getUserFromHeader(header);
		Optional<Job> existingJob = jobRepo.findById(jobId);
		if(existingJob.isPresent()) {
			if(existingJob.get().getEmployer().getId() == employer.getId()) {
				
				existingJob.get().setJobTitle(!StringUtil.isNullOrEmpty(newJob.getJobTitle()) ?  newJob.getJobTitle() : existingJob.get().getJobTitle());
				existingJob.get().setJobDescription(
						!StringUtil.isNullOrEmpty(newJob.getJobDescription()) ? newJob.getJobDescription()
								: existingJob.get().getJobDescription());
				existingJob.get().setDuration(!StringUtil.isNullOrEmpty(newJob.getDuration()) ?  newJob.getDuration() : existingJob.get().getDuration());
				existingJob.get().setPay(newJob.getPay() != null ?  newJob.getPay() : existingJob.get().getPay());
				
				Job savedJob = jobRepo.save(existingJob.get());
				return savedJob;
			}
			throw new BusinessValidationException(401, "You are not authorized to update this job");
		}
		throw new BusinessValidationException(400, "Job does not exist");
	}

	public Job changeStatus(int jobId, JobStatus status, String header) throws BusinessValidationException {

		Employer employer = null;
		Worker worker = null;
		UserInfo user = jwt.getUserFromHeader(header);
		Role role = user.getRole();
		if (user instanceof Employer) {
			employer = (Employer) user;
		} else
			worker = (Worker) user;

		Optional<Job> existingJob = jobRepo.findById(jobId);
		if (existingJob.isPresent()) {
			if (this.isBelongsToCorrectOwner(employer, worker, existingJob.get())) {
				if (this.isValidTransaction(existingJob.get().getStatus(), status, role)) {
					existingJob.get().setStatus(status);
					Job savedJob = jobRepo.save(existingJob.get());
					return savedJob;
				}
				throw new BusinessValidationException(403, "Invalid status updation");
			}
			throw new BusinessValidationException(401, "You are not authorized to update this job");
		}
		throw new BusinessValidationException(400, "Job does not exist");

	}

	private boolean isBelongsToCorrectOwner(Employer employer, Worker worker, Job job) {
		return  employer != null ? job.getEmployer().getId() == employer.getId() : job.getAssignedWorker().getId() == worker.getId();
	}

	private boolean isValidTransaction(JobStatus currentStatus, JobStatus nextStatus, Role role) {
		switch (role) {
		case EMPLOYER:
			switch (currentStatus) {
			case OPEN:
				return nextStatus == JobStatus.IN_PROGRESS || nextStatus == JobStatus.CANCELLED;

			case IN_PROGRESS:
				return nextStatus == JobStatus.CANCELLED;

			case WORK_DONE:
				return nextStatus == JobStatus.COMPLETED;

			default:
				return false;
			}

		case WORKER:
			switch (currentStatus) {
			case IN_PROGRESS:
				return nextStatus == JobStatus.WORK_DONE;
				
			case WORK_DONE:
				return nextStatus == JobStatus.IN_PROGRESS;

			default:
				return false;

			}
		default:
			return false;
		}
	}

	public Job getJobById(int id) throws BusinessValidationException {
		return jobRepo.findById(id).orElseThrow(() -> new BusinessValidationException(400, "Job Not Found"));
		
	}

	public Payment getPaymentDetails(int jobId) throws BusinessValidationException {
		 Job job = jobRepo.findById(jobId).orElseThrow(()-> new BusinessValidationException(400, "Job Not found"));
		 return job.getPayment();
	}
	
}
