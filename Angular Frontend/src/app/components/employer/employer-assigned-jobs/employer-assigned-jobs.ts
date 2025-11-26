import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../../services/employer-service';
import { MyJobsModel } from '../../../models/MyJobsModel.model';
import { MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardSubtitle, MatCardTitle } from '@angular/material/card';
import { MatChipSet, MatChip } from '@angular/material/chips';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-employer-assigned-jobs',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatChipSet, MatChip, MatCardFooter, MatCardSubtitle, MatIcon,],
  templateUrl: './employer-assigned-jobs.html',
  styleUrl: './employer-assigned-jobs.css'
})
export class EmployerAssignedJobs implements OnInit{
deleteJob(_t2: MyJobsModel) {
throw new Error('Method not implemented.');
}

  constructor(private employerService:EmployerService){}


  jobList : MyJobsModel[] =[];


  ngOnInit(): void {
    this.loadJobs();
  }
  
  onTabActivated() {
   this.loadJobs();
  }

  loadJobs() {
    this.employerService.getAssignedJobs().subscribe(jobs=>{
      this.jobList=jobs;
    });
  }



}
 

