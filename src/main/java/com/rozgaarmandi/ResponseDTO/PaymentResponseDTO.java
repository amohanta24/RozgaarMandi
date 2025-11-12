package com.rozgaarmandi.ResponseDTO;

import java.time.LocalDateTime;

import com.rozgaarmandi.Models.Payment.MethodOfPayment;
import com.rozgaarmandi.Models.Payment.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponseDTO {
	
	private Integer jobId; 
    private Integer employerId;
    private Integer workerId;
    private Double amount;           
    private LocalDateTime paymentDate;
    private String transactionId;    
    private MethodOfPayment methodOfPayment;          
    private PaymentStatus status;  

}
