package com.rozgaarmandi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Repositories.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;

	public Payment getPaymentById(int paymentId) throws BusinessValidationException {
		Optional<Payment> paymentOptional = paymentRepo.findById(paymentId);
		if(paymentOptional.isEmpty())
			throw new BusinessValidationException(400, "Invalid Payment Id");
		
		return paymentOptional.get();
	}

}
