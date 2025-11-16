import { Component, ViewChild } from '@angular/core';
import { MatTab, MatTabChangeEvent, MatTabGroup, MatTabsModule } from "@angular/material/tabs";
import { OpendJobs } from "../../worker/open-jobs/open-jobs";
import { AssignedJobs } from "../../worker/assigned-jobs/assigned-jobs";
import { CompletedJobs } from "../../completed-jobs/completed-jobs";
import { PostJob } from "../post-job/post-job";
import { MyJobs } from "../my-jobs/my-jobs";
import { EmployerAssignedJobs } from "../employer-assigned-jobs/employer-assigned-jobs";
import { EmployerCompletedJobs } from "../employer-completed-jobs/employer-completed-jobs";
import { MatToolbar } from "@angular/material/toolbar";
import { MatIcon } from "@angular/material/icon";
import { NavbarComponent } from "../../shared/navbar/navbar";

@Component({
  selector: 'app-employer-dashboard',
  imports: [MatTab, MatTabGroup, MatTabsModule, PostJob, MyJobs, EmployerAssignedJobs, EmployerCompletedJobs, MatToolbar, MatIcon, NavbarComponent],
  templateUrl: './employer-dashboard.html',
  styleUrl: './employer-dashboard.css'
})
export class EmployerDashboard {

  @ViewChild(MyJobs) myjobs!: MyJobs;
  @ViewChild(EmployerAssignedJobs) employerAssignedJobs!: EmployerAssignedJobs;
  @ViewChild(EmployerCompletedJobs) employerCompletedJobs!: EmployerCompletedJobs;
  @ViewChild(PostJob) postJob!: PostJob;

drawer: any;

    selectedTabIndex=0;
    tabSwitchedAfterJobPosted = false;
  

  onTabSwitch(event:MatTabChangeEvent){
    switch(event.index){
      case 0: 
        this.postJob.onTabActivated();
        break;
      case 1:
        this.myjobs.onTabActivated();
        break;
      case 2:
        this.employerAssignedJobs.onTabActivated();
        break;
      case 3:
        this.employerCompletedJobs.onTabActivated();
        break;
      default:
        break;
    }
  }

  swithToTab(index : number){
    this.selectedTabIndex = index;
    this.tabSwitchedAfterJobPosted = true;
  }


}
