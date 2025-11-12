package com.rozgaarmandi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rozgaarmandi.Exception.BusinessValidationException;
import com.rozgaarmandi.Models.Job;
import com.rozgaarmandi.Models.Payment;
import com.rozgaarmandi.Models.Worker;
import com.rozgaarmandi.ResponseDTO.JobResponseDTO;
import com.rozgaarmandi.ResponseDTO.PaymentResponseDTO;
import com.rozgaarmandi.ResponseDTO.WorkerResponseDTO;
import com.rozgaarmandi.Service.WorkerService;
import com.rozgaarmandi.Utils.MapperUtils;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;
    
    @GetMapping("getWorker/{id}")
    public ResponseEntity<WorkerResponseDTO> getWorkerById(@PathVariable int id) throws BusinessValidationException{
    	Worker workerById = workerService.getWorkerById(id);
    	WorkerResponseDTO response = MapperUtils.workerToWorkerResponseDTO(workerById).get(0);
    	 return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping("/appliedJobs")
    public ResponseEntity<List<JobResponseDTO>> getAppliedJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
        List<Job> appliedJobsByWorker = workerService.getAppliedJobsByWorker(header);
        List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(appliedJobsByWorker);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping("/assignedJobs")
    public ResponseEntity<List<JobResponseDTO>> getAssignedJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
        List<Job> assignedJobByWorker = workerService.getAssignedJobsByWorker(header);
        List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(assignedJobByWorker);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping("/completedJobs")
    public ResponseEntity<List<JobResponseDTO>> getCompletedJobs(@RequestHeader("Authorization") String header) throws BusinessValidationException {
        List<Job> completedJobsByWorker = workerService.getCompletedJobs(header);
        List<JobResponseDTO> response = MapperUtils.jobToJobResponseDTO(completedJobsByWorker);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping("/paymentDetails/{workerId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentDetailsByWorker(@PathVariable int workerId) throws BusinessValidationException{
    	List<Payment> paymentDetailsByWorker = workerService.getPaymentDetailsByWorker(workerId);
    	List<PaymentResponseDTO> response = MapperUtils.paymentToPaymentResponseDTO(paymentDetailsByWorker);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
  
}

