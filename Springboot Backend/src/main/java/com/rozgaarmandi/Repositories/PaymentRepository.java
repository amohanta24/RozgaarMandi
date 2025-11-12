package com.rozgaarmandi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rozgaarmandi.Models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
