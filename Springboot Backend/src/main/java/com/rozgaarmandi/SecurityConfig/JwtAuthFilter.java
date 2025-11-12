package com.rozgaarmandi.SecurityConfig;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.rozgaarmandi.Service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JwtService jwtService;
	
	@Value("#{'${auth.enpoints.public}'.split(',')}")
	private List<String> publicEndpoints;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
	    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

	    try {
	        String token = null;
	        UserDetails user = null;

	        String header = request.getHeader("Authorization");

	        if (publicEndpoints.stream().noneMatch(request.getRequestURI()::contains)
	                && (header == null || header.isEmpty())) {
	            throw new AccessDeniedException("Token is required");
	        }

	        if (header != null && !header.isEmpty() && header.startsWith("Bearer ")) {
	            token = header.substring(7);
	            user = this.jwtService.validateToken(token);
	        }

	        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UsernamePasswordAuthenticationToken auth =
	                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
	            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(auth);
	        }

	        filterChain.doFilter(wrappedRequest, wrappedResponse);

	    } catch (Exception e) {
	        log.error("JWT Filter Error: {}", e.getMessage());
	        resolver.resolveException(request, response, null, e);
	    } finally {
	        logRequestResponse(wrappedRequest, wrappedResponse);
	        wrappedResponse.copyBodyToResponse(); 
	    }
	}
	
	private void logRequestResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response)
	        throws IOException {String requestBody = new String(request.getContentAsByteArray(), request.getCharacterEncoding());
	        String responseBody = new String(response.getContentAsByteArray(), response.getCharacterEncoding());

	     // Get Authorization header
	     String authorizationHeader = request.getHeader("Authorization");

	     log.info("\n==================== API LOG ====================\n" +
	                     "Method: {}\n" +
	                     "URI: {}\n" +
	                     "Authorization: {}\n" +
	                     "Response Status: {}\n" +
	                     "Response Body: {}\n" +
	                     "==================================================",
	             request.getMethod(),
	             request.getRequestURI(),
	             authorizationHeader != null ? authorizationHeader : "N/A",
	             response.getStatus(),
	             responseBody
	     );}


	
}
