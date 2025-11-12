import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatCard, MatCardTitle, MatCardHeader, MatCardContent, MatCardActions } from "@angular/material/card";
import { MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInputModule } from '@angular/material/input';
import { postJobFormDialog } from './post-job-form-dialog';

@Component({
  selector: 'app-post-job',
  templateUrl: './post-job.html',
  styleUrls: ['./post-job.css'],
  imports: [MatCard, MatCardTitle, MatCardHeader, MatCardContent, MatFormField, MatLabel, MatIcon, MatCardActions, MatFormFieldModule, FormsModule, ReactiveFormsModule, MatInputModule]
})
export class PostJob implements OnInit {

  postJobForm!: FormGroup;
  readonly dialog = inject(MatDialog);

  constructor(private formBuilder: FormBuilder) {}

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
        this.dialog.open(postJobFormDialog, {
                  data: this.postJobForm.value,
                  width: '400px'
                });
              }
    }
  }
}
