import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Review } from './review';
import { JobStatus } from '../models/Enum.model';
import { Job } from '../models/jobresponse.model';
import { JobRequest } from '../models/JobRequest';
import { MyJobs } from '../components/employer/my-jobs/my-jobs';
import { MyJobsModel } from '../models/MyJobsModel.model';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {

  constructor( private httpClient:HttpClient){}

  private hostUrl = environment.hostUrl;
  private pathUrl = '/employer';

    
    getPostedJobsUri : string = `${this.hostUrl}${this.pathUrl}/jobs`;
    getApplicantsUri : string = `${this.hostUrl}${this.pathUrl}/job/applicants`;
    assignJobToWorkerUri : string = `${this.hostUrl}/job/assign`;
    getAssignedJobsUri : string = `${this.hostUrl}${this.pathUrl}/jobs/assigned`;
    getCompletedJobsUri : string = `${this.hostUrl}${this.pathUrl}/completedJobs`;
    getReviewByIdUri : string =  `${this.hostUrl}/review/getReviewById/"`;
    changeStatusUri : string = `${this.hostUrl}/job/changeStatus`;
    postJobUri : string = `${this.hostUrl}/job/postJob`;
    assignJobUri : string = `${this.hostUrl}/job/assign`;



    assignJob(jobId : number, workerId : number) : Observable<Job>{
      return this.httpClient.put<Job>(`${this.assignJobUri}?jobId=${jobId}&&workerId=${workerId}`, null);
    }

    postJob(jobRequest : JobRequest) : Observable<Job>{
      return this.httpClient.post<Job>(this.postJobUri,jobRequest);
    }

    getPostedJobs() : Observable<MyJobsModel[]>{
      return this.httpClient.get<MyJobsModel[]>(this.getPostedJobsUri);
    }

    getApplicants(jobId:any) : Observable<Job[]>{
      return this.httpClient.get<Job[]>(`${this.getApplicantsUri}?jobId=${jobId}`);
    }

    assignJobToWorker() : Observable<Job[]>{
      return this.httpClient.put<Job[]>(this.assignJobToWorkerUri,null);
    }

    getAssignedJobs(jobId:number) : Observable<Job[]>{
      return this.httpClient.get<Job[]>(this.getAssignedJobsUri);
    }   

    getCompletedJobs(): Observable<Job[]>{
      return this.httpClient.get<Job[]>(this.getCompletedJobsUri);
    }

    changeStatus(jobId:number, jobStatus:JobStatus) : Observable<Job>{
      return this.httpClient.put<Job>(`${this.changeStatusUri}?jobId=${jobId}&&jobStatus=${jobStatus}`,null )
    }
}
