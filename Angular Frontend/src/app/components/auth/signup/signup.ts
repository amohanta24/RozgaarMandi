import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmployerType, Role } from '../../../models/Enum.model';
import { MatToolbarModule } from '@angular/material/toolbar';
import { signUpRequest } from '../../../models/signUpRequest.model';
import { LoginService } from '../../../services/login-service';
import { Router } from '@angular/router';
import { ErrorDialogComponen } from '../../error-dialog-component/error-dialog-component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-signup',
  imports: [FormsModule, ReactiveFormsModule, CommonModule,MatToolbarModule , ],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup implements OnInit {

roles = ['EMPLOYER','WORKER'];
employerTypes = ['INDIVIDUAL', 'BUSINESS'];
signUpForm: FormGroup = new FormGroup({});
isSubmitted : boolean = false;
isShaking : boolean = false;

   constructor(private formGroupBuilder : FormBuilder, private loginService : LoginService, private router : Router, private dialog: MatDialog){

   }

   ngOnInit(): void {
    this.signUpForm = this.formGroupBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10)]],
      selectedRole: ['', [Validators.required]],
      selectedEmployerType: [''],
      employerFirstName: [''],
      employerLastName: [''],
      workerFirstName:[''],
      workerLastName:[''],
      skills: [''],
      businessName: [''],
      contactPersonName: [''],
      contactPersonNumber: [''],
      gstNumber: [''],
      businessDescription: [''],
    });
  

    this.signUpForm.get('selectedRole')?.valueChanges.subscribe(role => {

      this.clearAllDynamicValidators();
  
      if (role === 'WORKER') {
        this.signUpForm.get('employerFirstName')?.setValidators([Validators.required]);
        this.signUpForm.get('employerLastName')?.setValidators([Validators.required]);
        this.signUpForm.get('skills')?.setValidators([Validators.required]);
      }
  
      if (role === 'EMPLOYER') {
        this.signUpForm.get('selectedEmployerType')?.setValidators([Validators.required]);
      }
  
      this.updateValidity();
    });
  

    this.signUpForm.get('selectedEmployerType')?.valueChanges.subscribe(type => {

      this.clearEmployerValidators();
  
      if (type === 'INDIVIDUAL') {
        this.signUpForm.get('employerFirstName')?.setValidators([Validators.required]);
        this.signUpForm.get('employerLastName')?.setValidators([Validators.required]);
      }
  
      if (type === 'BUSINESS') {
        this.signUpForm.get('businessName')?.setValidators([Validators.required]);
        this.signUpForm.get('contactPersonName')?.setValidators([Validators.required]);
        this.signUpForm.get('contactPersonNumber')?.setValidators([Validators.required]);
        this.signUpForm.get('businessDescription')?.setValidators([Validators.required]);
      }
  
      this.updateValidity();
    });
  }

private clearAllDynamicValidators() {
  const fields = [
    'employerFirstName', 'employerLastName', 'skills', 'businessName',
    'contactPersonName', 'contactPersonNumber',
    'gstNumber', 'businessDescription', 'selectedEmployerType'
  ];

  fields.forEach(f => {
    this.signUpForm.get(f)?.clearValidators();
  });
}

private clearEmployerValidators() {
  const fields = [
    'employerFirstName', 'employerLastName', 'businessName',
    'contactPersonName', 'contactPersonNumber', 'businessDescription'
  ];
  fields.forEach(f => this.signUpForm.get(f)?.clearValidators());
}



private updateValidity() {
  Object.keys(this.signUpForm.controls).forEach(key => {
    this.signUpForm.get(key)?.updateValueAndValidity({ emitEvent: false });
  });
}

  onSubmit(){
    this.submitted();

    const request : signUpRequest = this.createSignUpRequest();

    this.loginService.signUp(request).subscribe({
      next: () => {
        this.dialog.open(ErrorDialogComponen, {
          data: { message: 'Signup successful! Please log in to continue.' },
          width: '400px'
        });
        setTimeout(() => this.router.navigate(['']), 2000);
      },
      error: (err) => console.error('Signup failed:', err)
    });
  }



  createSignUpRequest() : signUpRequest {
    const request = new signUpRequest();
  
    request.username = this.signUpForm.get('username')?.value;
    request.password = this.signUpForm.get('password')?.value;
    request.email = this.signUpForm.get('email')?.value;
    request.role = this.signUpForm.get('selectedRole')?.value;
    request.phoneNumber = this.signUpForm.get('phoneNumber')?.value;
  
    const selectedRole = this.signUpForm.get('selectedRole')?.value;
  
    if (selectedRole?.includes('EMPLOYER')) {
      request.employerType = this.signUpForm.get('selectedEmployerType')?.value;
      request.employerFirstName = this.signUpForm.get('employerFirstName')?.value;
      request.employerLastName = this.signUpForm.get('employerLastName')?.value;
      request.businessName = this.signUpForm.get('businessName')?.value;
      request.contactPersonName = this.signUpForm.get('contactPersonName')?.value;
      request.contactPersonNumber = this.signUpForm.get('contactPersonNumber')?.value;
      request.gstNumber = this.signUpForm.get('gstNumber')?.value;
      request.businessDescription = this.signUpForm.get('businessDescription')?.value;
    }
  
    if (selectedRole?.includes('WORKER')) {
      request.workerFirstName = this.signUpForm.get('workerFirstName')?.value;
      request.workerLastName = this.signUpForm.get('workerLastName')?.value;
      request.skills = this.signUpForm.get('skills')?.value;
    }
  
    return request;
  }

  submitted(){
    this.isSubmitted = true;
    setTimeout(() => (this.isSubmitted = false),300);
  }

  triggerShake() {
    this.isShaking = true;
    setTimeout(() => (this.isShaking = false),300);
  }


}


