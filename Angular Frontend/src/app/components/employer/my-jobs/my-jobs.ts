import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../../services/employer-service';
import { Job } from '../../../models/jobresponse.model';
import { MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter } from "@angular/material/card";
import { MatChipSet, MatChip } from "@angular/material/chips";

@Component({
  selector: 'app-my-jobs',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatChipSet, MatChip, MatCardFooter],
  templateUrl: './my-jobs.html',
  styleUrl: './my-jobs.css'
})
export class MyJobs implements OnInit{

  constructor(private employerService:EmployerService){}

  jobList : Job[] = [];

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(){
    this.employerService.getPostedJobs().subscribe(jobs=>{
        this.jobList=jobs;
    });
  }

  onTabActivated() {
    this.loadJobs();
  }

  viewApplications( job: Job) {
    this.employerService.getApplicants().subscribe();
   
    }


}
