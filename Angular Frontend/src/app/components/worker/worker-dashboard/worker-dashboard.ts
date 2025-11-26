import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { LoginService } from '../../../services/login-service';
import { WorkerService } from '../../../services/worker-service';
import { MatTab, MatTabChangeEvent, MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable, Observer } from 'rxjs';
import { CompletedJobs } from "../../completed-jobs/completed-jobs";
import { OpendJobs } from "../open-jobs/open-jobs";
import { AssignedJobs } from "../assigned-jobs/assigned-jobs";
import { PostJob } from "../../employer/post-job/post-job";
import { MyJobs } from "../../employer/my-jobs/my-jobs";
import { EmployerAssignedJobs } from "../../employer/employer-assigned-jobs/employer-assigned-jobs";
import { EmployerCompletedJobs } from "../../employer/employer-completed-jobs/employer-completed-jobs";
import { AppliedJobs } from "../applied-jobs/applied-jobs";
import { NavbarComponent } from "../../shared/navbar/navbar";
@Component({
  imports: [FormsModule, ReactiveFormsModule, MatTab, MatTabGroup, MatTabsModule, AppliedJobs, CompletedJobs, OpendJobs, AssignedJobs, AppliedJobs, NavbarComponent],
  selector: 'app-worker-dashboard',
  templateUrl: './worker-dashboard.html',
  styleUrls: ['./worker-dashboard.css']
})
export class WorkerDashboardComponent implements OnInit {
  ngOnInit(): void {
  }

  @ViewChild(OpendJobs) openJobs!: OpendJobs;
  @ViewChild(AppliedJobs) appliedJobs!: AppliedJobs;
  @ViewChild(AssignedJobs) assignedJobs!: AssignedJobs;
  @ViewChild(CompletedJobs) completedJobs!: CompletedJobs;
  

  onTabSwitch(event:MatTabChangeEvent){
    switch(event.index){
      case 0:
        this.openJobs.onTabActivated();
        break;
      case 1:
        this.appliedJobs.onTabActivated();
        break;
      case 2:
        this.assignedJobs.onTabActivated();
        break;
      case 3:
        this.completedJobs.onTabActivated();
        break;
      default:
        break;
    }
  }
 
  
}
