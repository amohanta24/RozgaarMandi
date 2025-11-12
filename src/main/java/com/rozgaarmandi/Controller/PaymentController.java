package com.rozgaarmandi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.ResponseDTO.PaymentResponseDTO;
import com.rozgaarmandi.Service.PaymentService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	
	@GetMapping("/getPayment/{paymentId}")
	public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable int paymentId) throws BusinessValidationException {
		Payment paymentById = paymentService.getPaymentById(paymentId);
		PaymentResponseDTO response = MapperUtils.paymentToPaymentResponseDTO(paymentById).get(0);
		return new ResponseEntity<>(response , HttpStatus.OK);
	}
	

}
