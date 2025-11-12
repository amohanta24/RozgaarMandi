import { Component } from '@angular/core';
import { WorkerService } from '../../services/worker-service';
import { Job } from '../../models/jobresponse.model';
import { MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter } from "@angular/material/card";
import { MatChipSet, MatChip } from "@angular/material/chips";

@Component({
  selector: 'app-completed-jobs',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter, MatChipSet, MatChip],
  templateUrl: './completed-jobs.html',
  styleUrl: './completed-jobs.css'
})
export class CompletedJobs {

  
    constructor(private workerService:WorkerService){}
    
     joblist: Job[] =[];
     totalJobCount:number = 0;
     
     loadJobs() : void{
      this.workerService.getCompletedJobs().subscribe(jobs =>{
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
