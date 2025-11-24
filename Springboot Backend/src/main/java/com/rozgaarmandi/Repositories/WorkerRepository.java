package com.rozgaarmandi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer>{
	
	List<Worker> findByIdIn(List<Integer> workerIds);
	
	

}
