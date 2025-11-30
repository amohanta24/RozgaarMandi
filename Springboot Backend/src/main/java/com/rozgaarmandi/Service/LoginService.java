package com.rozgaarmandi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Employer;
import com.rozgaarmandi.Models.UserInfo;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.Repositories.EmployerRepository;
import com.rozgaarmandi.Repositories.UserRepository;
import com.rozgaarmandi.Repositories.WorkerRepository;
import com.rozgaarmandi.RequestDTO.SignUpDTO;
import com.rozgaarmandi.RequestDTO.loginDTO;
import com.rozgaarmandi.ResponseDTO.JwtResponse;

import ch.qos.logback.core.util.StringUtil;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployerRepository employerRepo;
	
	@Autowired
	private WorkerRepository workerRepo;
	

	public Object login(loginDTO login) {
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				!StringUtil.isNullOrEmpty(login.getEmail()) ? login.getEmail() : login.getPhoneNumber(),
				login.getPassword());
		authManager.authenticate(auth);
		String token = jwtService.generateToken(!StringUtil.isNullOrEmpty(login.getEmail()) ? login.getEmail() : login.getPhoneNumber());

		JwtResponse jwtResponse = new JwtResponse(200, token);
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}

	public UserInfo signUp(SignUpDTO request) throws BusinessValidationException {
        if (userRepo.existsByEmail(request.getEmail()) || userRepo.existsByPhoneNumber(request.getPhoneNumber()) || userRepo.existsByUsername(request.getPhoneNumber())) {
            throw new BusinessValidationException(409, "Email or phone number already registered");
        }

        UserInfo user = new UserInfo();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setIsActive(true);
        user.setIsEmailVerified(false);
        user.setIsPhoneVerified(false);

        switch (request.getRole()) {
            case EMPLOYER:
                Employer employer = new Employer();
                employer.setUsername(user.getUsername());
                employer.setEmail(user.getEmail());
                employer.setPhoneNumber(user.getPhoneNumber());
                employer.setPassword(user.getPassword());
                employer.setRole(user.getRole());
                employer.setIsActive(user.getIsActive());
                employer.setIsEmailVerified(user.getIsEmailVerified());
                employer.setIsPhoneVerified(user.getIsPhoneVerified());
                
                employer.setEmployerType(request.getEmployerType());
                employer.setContactPersonName(request.getContactPersonName());
                employer.setContactPersonNumber(request.getContactPersonNumber());
                employer.setGstNumber(request.getGstNumber());
                employer.setBusinessDescription(request.getBusinessDescription());
                
                employer.setFirstName(request.getEmployerFirstName());
                employer.setLastName(request.getEmployerLastName());
               
                return employerRepo.save(employer);

            case WORKER:
                Worker worker = new Worker();
                worker.setUsername(user.getUsername());
                worker.setEmail(user.getEmail());
                worker.setPhoneNumber(user.getPhoneNumber());
                worker.setPassword(user.getPassword());
                worker.setRole(user.getRole());
                worker.setIsActive(user.getIsActive());
                worker.setIsEmailVerified(user.getIsEmailVerified());
                worker.setIsPhoneVerified(user.getIsPhoneVerified());
                
                worker.setSkills(request.getSkills());
                
                worker.setFirstName(request.getWorkerFirstName());
                worker.setLastName(request.getWorkerLastName());
                worker.setLocation(request.getLocation());               
                
                return workerRepo.save(worker);

            case ADMIN:
                return userRepo.save(user);

            default:
                throw new BusinessValidationException(400 ,"Invalid role specified");
        }
    }
}


