package com.rozgaarmandi.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Repositories.UserRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;
	
	@Value("${jwt.secret}")
	private String secret;

	
	public UserDetails validateToken(String token) throws JwtException{
		UserDetails userDetails = null;
			String username = this.getUserNameFromToken(token);
			userDetails = this.userService.loadUserByUsername(username);
			return userDetails;
	}

	private String getUserNameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.getKey()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	private Key getKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public UserInfo getUserFromHeader(String header) throws BusinessValidationException {
		String token = header.substring(7);
		 String username = this.getUserNameFromToken(token);
		  return this.userRepo.findByIsActiveAndEmailOrPhoneNumber(true, username,username).orElseThrow(() -> new BusinessValidationException(400, "User Does Not exist"));
	}
	
}

