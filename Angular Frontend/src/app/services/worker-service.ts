import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Job } from '../models/jobresponse.model';
import { Review } from './review';
import { JobStatus } from '../models/Enum.model';


@Injectable({
  providedIn: 'root'
})
export class WorkerService {

  constructor( private httpClient:HttpClient){}

  private hostUrl = environment.hostUrl;
  private pathUrl = '/worker';

    getReviewByIdUri : string =  `${this.hostUrl}/review/getReviewById/"`;
    getOpenJobsUri : string = `${this.hostUrl}/job/openJobs`;
    getAppliedJobsUri : string = `${this.hostUrl}${this.pathUrl}/appliedJobs`;
    applyToJobUri : string = `${this.hostUrl}/job/apply/`;
    assignedJobsUri : string = `${this.hostUrl}${this.pathUrl}/assignedJobs`;
    completedJobsUri : string = `${this.hostUrl}${this.pathUrl}/completedJobs`;
    changeStatusUri : string = `${this.hostUrl}/job/changeStatus`;
  
    

    getOpenJobs() : Observable<Job[]>{
      return this.httpClient.get<Job[]>(this.getOpenJobsUri);
    }

    getAppliedJobs() : Observable<Job[]>{
      return this.httpClient.get<Job[]>(this.getAppliedJobsUri);
    }

    getReviewById() : Observable<Review>{
      return this.httpClient.get<Review>(this.getReviewByIdUri);
    }

    applyToJob(jobId:number) : Observable<Review[]>{
      return this.httpClient.put<Review[]>(`${this.applyToJobUri}${jobId}`,null);
    }

    getAssignedJobs() : Observable<Job[]>{
    return this.httpClient.get<Job[]>(this.assignedJobsUri);
  }

    getCompletedJobs(): Observable<Job[]>{
      return this.httpClient.get<Job[]>(this.completedJobsUri);
    }

    changeStatus(jobId:number, jobStatus:JobStatus) : Observable<Job>{
      return this.httpClient.put<Job>(`${this.changeStatusUri}?jobId=${jobId}&&jobStatus=${jobStatus}`,null )
    }
}
  

