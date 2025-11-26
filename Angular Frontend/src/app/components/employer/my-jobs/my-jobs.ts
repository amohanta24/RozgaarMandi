import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../../services/employer-service';
import { Job } from '../../../models/jobresponse.model';
import { MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter, MatCardSubtitle } from "@angular/material/card";
import { MatChipSet, MatChip } from "@angular/material/chips";
import { MyJobsModel } from '../../../models/MyJobsModel.model';
import { MatIcon } from "@angular/material/icon";
import { Worker } from '../../../models/workerresponse.model';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogComponent } from '../../shared/dialog-component/dialog-component';
import { Dialog } from '@angular/cdk/dialog';


@Component({
  selector: 'app-my-jobs',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatChipSet, MatChip, MatCardFooter, MatCardSubtitle, MatIcon,],
  templateUrl: './my-jobs.html',
  styleUrl: './my-jobs.css'
})
export class MyJobs implements OnInit{



  constructor(private employerService:EmployerService, private dialog : MatDialog){}

  jobList : MyJobsModel[] = [];

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

  toggleApplicants(job: MyJobsModel) {
    job.viewApplicants=!job.viewApplicants;
  }

  getStars(rating: number | null | undefined): string[] {

    if (rating == null || isNaN(rating)) {
      return Array(5).fill('star_border');
    }
  
    const stars: string[] = [];
  
    for (let i = 1; i <= 5; i++) {
      if (rating >= i) stars.push('star');
      else if (rating >= i - 0.5) stars.push('star_half');
      else stars.push('star_border');
    }
  
    return stars;
  }

  assignJob(job:MyJobsModel,applicant:Worker){
    this.employerService.assignJob(job.job.jobId,applicant.workerId).subscribe(response => {
      job.job.assignedWorkerId=response.assignedWorkerId;
    });
  }

  unassignJob(job:MyJobsModel, applicant:Worker){
    if(job.job.assignedWorkerId!=null){
      this.employerService.unassignJob(job.job.jobId,applicant.workerId).subscribe(response => {
        job.job.assignedWorkerId=response.assignedWorkerId;
      });
    }
    }
  

  deleteJob(job: MyJobsModel) {

    const dialogRef = this.dialog.open(DialogComponent, {
      width : '400px',
      data : {
        title : 'Delete Confirmation',
        message: 'Are you sure you want to delete this job ?',
        buttonText : 'Delete'
      }
    });
    
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.employerService.deleteJob(job.job.jobId).subscribe({
          next:(res) => {
            this.loadJobs();
          }
        });
      }
    });
    
  }


}
