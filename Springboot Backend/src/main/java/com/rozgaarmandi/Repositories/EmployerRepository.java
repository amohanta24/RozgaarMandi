package com.rozgaarmandi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer>{

}
