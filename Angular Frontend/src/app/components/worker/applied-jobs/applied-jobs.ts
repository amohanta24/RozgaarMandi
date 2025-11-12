import { Component, Injectable } from '@angular/core';
import { MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatChipSet, MatChip } from '@angular/material/chips';
import { MatList, MatListItem, MatListItemLine, MatListItemTitle } from '@angular/material/list';
import { WorkerService } from '../../../services/worker-service';
import { Job } from '../../../models/jobresponse.model';

@Component({
  selector: 'app-applied-jobs',
  imports: [MatCard, MatList, MatListItem, MatListItemLine, MatListItemTitle, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter, MatChipSet, MatChip],
  templateUrl: './applied-jobs.html',
  styleUrl: './applied-jobs.css'
})

export class AppliedJobs {

  constructor(private workerService:WorkerService){}
  
   joblist: Job[] =[];
   totalJobCount:number = 0;
   
   loadJobs() : void{
    this.workerService.getAppliedJobs().subscribe(jobs =>{
      this.joblist=jobs;
    });
  }
    ngOnInit(): void {
      this.loadJobs();
    }

    onTabActivated(){
      this.loadJobs();
    }
    


}
