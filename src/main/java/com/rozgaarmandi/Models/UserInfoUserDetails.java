package com.rozgaarmandi.Models;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.qos.logback.core.util.StringUtil;

public class UserInfoUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private  List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails(UserInfo user) {
		this.username =  !StringUtil.isNullOrEmpty(user.getEmail()) ? user.getEmail() : user.getPassword();
		this.password = user.getPassword();
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
}
