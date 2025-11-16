import { Component, inject, Input, OnInit, output } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatCard, MatCardTitle, MatCardHeader, MatCardContent, MatCardActions } from "@angular/material/card";
import { MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInputModule } from '@angular/material/input';
import { postJobFormDialog } from './post-job-form-dialog';
import { JobRequest } from '../../../models/JobRequest';
import { EmployerService } from '../../../services/employer-service';
import { JobStatus } from '../../../models/Enum.model';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-post-job',
  templateUrl: './post-job.html',
  styleUrls: ['./post-job.css'],
  imports: [MatCard, MatCardTitle, MatCardHeader, MatCardContent, MatFormField, MatLabel, MatIcon, MatCardActions, MatFormFieldModule, FormsModule, ReactiveFormsModule, MatInputModule]
})
export class PostJob implements OnInit {

  onTabActivated() {
     if(this.tabSwitchedAfterJobPosted){
         this.postJobForm.reset();
          this.postJobForm.markAsPristine();
          this.postJobForm.markAsUntouched();
     }
     
  }

   switchTabEvent = output<number>();
   @Input() tabSwitchedAfterJobPosted = false;

  postJobForm!: FormGroup;
  readonly dialog = inject(MatDialog);

  constructor(private formBuilder: FormBuilder, private employerService : EmployerService, private snackBar : MatSnackBar) {}

  ngOnInit(): void {
    this.postJobForm = this.formBuilder.group({
      jobTitle: ['', Validators.required],
      jobDescription: ['', Validators.required],
      duration: ['', Validators.required],
      location: ['', Validators.required], 
      pay: ['', Validators.required]
    });
  }

  onSubmit(event?: Event): void {

    if (this.postJobForm.valid) {
      if (this.dialog.openDialogs.length === 0) {

       const dialogRef = this.dialog.open(postJobFormDialog, {
                  data: this.postJobForm.value,
                  width: '400px'
                });

        dialogRef.afterClosed().subscribe(result => {
          if(result){
           const jobReq = new JobRequest();

           jobReq.jobTitle = this.postJobForm.get('jobTitle')?.value;           
           jobReq.jobDescription = this.postJobForm.get('jobDescription')?.value;           
           jobReq.duration = this.postJobForm.get('duration')?.value;           
           jobReq.location = this.postJobForm.get('location')?.value;           
           jobReq.pay = this.postJobForm.get('pay')?.value;     
           
           this.employerService.postJob(jobReq).subscribe(response => { 
            this.snackBar.open("Job Posted Sucessfully", 'close', {duration:3000});
            this.postJobForm.reset();
            this.postJobForm.markAsUntouched();
            this.postJobForm.reset();
            this.postJobForm.markAsPristine();
            this.switchTabEvent.emit(1);
           });
        }
      });
    }
  }
}
}
 
