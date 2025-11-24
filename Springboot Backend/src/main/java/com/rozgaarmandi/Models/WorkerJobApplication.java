package com.rozgaarmandi.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "WORKER_JOB_APPLICATIONS")
@Data
public class WorkerJobApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private Job job;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;
}
