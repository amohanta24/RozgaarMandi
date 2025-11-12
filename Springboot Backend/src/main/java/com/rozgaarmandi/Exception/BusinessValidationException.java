package com.rozgaarmandi.Exception;

import org.springframework.http.HttpStatus;

public class BusinessValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int code;
	
	public BusinessValidationException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	
	public HttpStatus getStatus() {
		return HttpStatus.valueOf(code);
	}
	
	

}
