import { JobService } from "../job-servie";
import { JobStatus } from "./Enum.model";

export interface Job {
  jobId: number;
  employerId: number;
  appliedWorkerIds: number[];      
  assignedWorkerId: number;
  jobTitle: string;
  jobDescription: string;
  duration: string;
  location: string;
  pay: number;
  status: JobStatus
  reviewsIds: number[];   
  applied: boolean;   
  lastSavedStatus?: JobStatus;     
}

