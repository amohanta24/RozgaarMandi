package com.rozgaarmandi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Job.JobStatus;
import com.rozgaarmandi.Models.Review;
import com.rozgaarmandi.Models.Worker;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>{

	List<Job> findByStatusOrderByCreatedOnDesc(JobStatus status);

	List<Job> findByEmployerAndStatus(Employer employer, JobStatus status);
	
	List<Job> findByAssignedWorkerAndStatus(Worker worker, JobStatus status);

	
}
