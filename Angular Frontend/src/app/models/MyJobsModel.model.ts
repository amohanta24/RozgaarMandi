import { Job } from "./jobresponse.model";
import { Worker } from "./workerresponse.model";

export interface MyJobsModel{
    
    job : Job;
    applicants?: Worker[];
    viewApplicants: boolean|false;

}