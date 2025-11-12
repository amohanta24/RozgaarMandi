package com.rozgaarmandi.Exception;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rozgaarmandi.Service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
	
	
	
	@ExceptionHandler
	public ProblemDetail handleException(Exception ex) {
		
		log.error(ex.getMessage(), ex);
		
		if(ex instanceof ExpiredJwtException)
			 return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "JWT token expired");
		
		if(ex instanceof MalformedJwtException)
			 return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
		
		if(ex instanceof AccessDeniedException)
			 return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "You are not authorized to access this API");
		
		if(ex instanceof AuthenticationException)
			return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "username or password is invalid"); 
		
		if (ex instanceof BusinessValidationException)
		    return ProblemDetail.forStatusAndDetail(((BusinessValidationException) ex).getStatus(), ((BusinessValidationException) ex).getMessage());
		
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

		
	}

}
