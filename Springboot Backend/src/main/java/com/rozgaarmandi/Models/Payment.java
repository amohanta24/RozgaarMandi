package com.rozgaarmandi.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Payment {
	
	public enum PaymentStatus{
		PENDING, PAID, FAILED
	}
	
	public enum MethodOfPayment{
		CASH, ONLINE
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "job_id")
    private Job job; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private Double amount;           
    private LocalDateTime paymentDate;
    private String transactionId;    
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MethodOfPayment methodOfPayment;          
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;    
    
    

    
}
