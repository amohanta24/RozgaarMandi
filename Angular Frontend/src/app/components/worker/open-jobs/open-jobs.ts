import { Component, OnInit } from '@angular/core';
import { WorkerService } from '../../../services/worker-service';
import { Job } from '../../../models/jobresponse.model';
import { MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter } from '@angular/material/card';
import { MatList, MatListItem, MatListItemLine, MatListItemTitle } from '@angular/material/list';
import { MatChipSet, MatChip } from '@angular/material/chips';

@Component({
  selector: 'app-open-jobs',
  imports: [MatCard, MatList, MatListItem, MatListItemLine, MatListItemTitle, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter, MatChipSet, MatChip],
  templateUrl: './open-jobs.html',
  styleUrl: './open-jobs.css'
})
export class OpendJobs implements OnInit{


  constructor(private workerService:WorkerService){}

 joblist: Job[] =[];
 totalJobCount:number = 0;

 
  ngOnInit(): void {
    this.loadOpenJobs();
  }

  loadOpenJobs():void{
    this.workerService.getOpenJobs().subscribe(jobs =>{
      this.joblist=jobs;
    });

  }

  onApply(job:Job){
    job.applied=false;
    this.workerService.applyToJob(job.jobId).subscribe(()=>{
      job.applied=true;
    });
  }

  onTabActivated(){
    this.loadOpenJobs();
  }

}
