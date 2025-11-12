import { Component } from '@angular/core';
import { MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter } from "@angular/material/card";
import { MatChipSet, MatChip } from "@angular/material/chips";
import { WorkerService } from '../../../services/worker-service';
import { Job } from '../../../models/jobresponse.model';
import { MatSelectionList } from "../../../../../node_modules/@angular/material/list/index";
import { FormsModule } from '@angular/forms';
import { JobStatus } from '../../../models/Enum.model';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-assigned-jobs',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardFooter, MatChipSet, MatChip,FormsModule,TitleCasePipe],
  templateUrl: './assigned-jobs.html',
  styleUrl: './assigned-jobs.css'
})
export class AssignedJobs {

  jobStatus: JobStatus[] = [JobStatus.IN_PROGRESS,JobStatus.WORK_DONE]
   constructor(private workerService:WorkerService){}
    
     joblist: Job[] =[];
     totalJobCount:number = 0;
     
     loadJobs() : void{
      this.workerService.getAssignedJobs().subscribe(jobs =>{
        this.joblist=jobs.map(job =>({
          ...job,
          lastSavedStatus:job.status
        }));
      });
    }
      ngOnInit(): void {
        this.loadJobs();
      }
  
      onTabActivated(){
        this.loadJobs();
      }

      onStatusChange(job: Job) {
        if (job.status !== job.lastSavedStatus) {
          this.workerService.changeStatus(job.jobId, job.status).subscribe(updatedJob => {
            job.lastSavedStatus = updatedJob.status;
          });
        }
      }


}
