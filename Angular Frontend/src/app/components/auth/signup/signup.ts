import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { signUpRequest } from '../../../models/signUpRequest.model';
import { LoginService } from '../../../services/login-service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponen } from '../../error-dialog-component/error-dialog-component';
import { DialogComponent } from '../../shared/dialog-component/dialog-component';


@Component({
  selector: 'app-signup',
  imports: [FormsModule, ReactiveFormsModule, CommonModule, MatToolbarModule],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup implements OnInit {

  roles = ['EMPLOYER', 'WORKER'];
  employerTypes = ['INDIVIDUAL', 'BUSINESS'];

  signUpForm!: FormGroup;
  isSubmitted = false;
  isShaking = false;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.initRoleListener();
    this.initEmployerTypeListener();
  }

  private initForm() {
    this.signUpForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10)]],

      selectedRole: ['', Validators.required],
      selectedEmployerType: [''],

      // employer fields
      employerFirstName: [''],
      employerLastName: [''],
      businessName: [''],
      contactPersonName: [''],
      contactPersonNumber: [''],
      gstNumber: [''],
      businessDescription: [''],

      // worker fields
      workerFirstName: [''],
      workerLastName: [''],
      skills: [''],
      location:['']
    });
  }

  private initRoleListener() {
    this.signUpForm.get('selectedRole')?.valueChanges.subscribe(role => {
      this.clearAllDynamicValidators();

      if (role === 'WORKER') {
        this.applyValidators(['workerFirstName', 'workerLastName', 'skills','location'], Validators.required);
      }

      if (role === 'EMPLOYER') {
        this.applyValidators(['selectedEmployerType'], Validators.required);
      }

      this.refreshFormValidity();
    });
  }

  private initEmployerTypeListener() {
    this.signUpForm.get('selectedEmployerType')?.valueChanges.subscribe(type => {
      this.clearEmployerValidators();

      if (type === 'INDIVIDUAL') {
        this.applyValidators(['employerFirstName', 'employerLastName'], Validators.required);
      }

      if (type === 'BUSINESS') {
        this.applyValidators(
          ['businessName', 'contactPersonName', 'contactPersonNumber', 'businessDescription'],
          Validators.required
        );
      }

      this.refreshFormValidity();
    });
  }

  private applyValidators(fields: string[], validator: any) {
    fields.forEach(f => {
      const ctrl = this.signUpForm.get(f);
      if (ctrl) {
        ctrl.setValidators([validator]);
        ctrl.updateValueAndValidity({ emitEvent: false });
      }
    });
  }

  private clearAllDynamicValidators() {
    const fields = [
      'employerFirstName', 'employerLastName', 'skills', 'businessName',
      'contactPersonName', 'contactPersonNumber', 'gstNumber',
      'businessDescription', 'selectedEmployerType',
      'workerFirstName', 'workerLastName','location'
    ];

    fields.forEach(f => {
      const ctrl = this.signUpForm.get(f);
      if (ctrl) {
        ctrl.clearValidators();
        ctrl.setValue('');
        ctrl.updateValueAndValidity({ emitEvent: false });
      }
    });
  }

  private clearEmployerValidators() {
    const fields = [
      'employerFirstName', 'employerLastName', 'businessName',
      'contactPersonName', 'contactPersonNumber',
      'businessDescription'
    ];

    fields.forEach(f => {
      const ctrl = this.signUpForm.get(f);
      if (ctrl) {
        ctrl.clearValidators();
        ctrl.setValue('');
        ctrl.updateValueAndValidity({ emitEvent: false });
      }
    });
  }

  private refreshFormValidity() {
    setTimeout(() => this.signUpForm.updateValueAndValidity(), 0);
  }

  submitted() {
    this.isSubmitted = true;
    setTimeout(() => (this.isSubmitted = false), 300);
  }

  triggerShake() {
    this.isShaking = true;
    setTimeout(() => (this.isShaking = false), 300);
  }

  onSubmit() {
    this.submitted();

    const req = this.buildRequest();

    this.loginService.signUp(req).subscribe({
      next: () => {
       const dialogRef =  this.dialog.open(DialogComponent, {
          data: { message:'Please log in to continue.' , title : 'Signup successful!', buttonText: 'Login'},
          width: '350px'
        });
        setTimeout(() => {
          this.router.navigate(['']); 
          dialogRef.close();
        }, 2000);
      },
    });
  }

  private buildRequest(): signUpRequest {
    const f = this.signUpForm.value;

    const req = new signUpRequest();
    req.username = f.username;
    req.password = f.password;
    req.email = f.email;
    req.role = f.selectedRole;
    req.phoneNumber = f.phoneNumber;

    if (f.selectedRole === 'EMPLOYER') {
      req.employerType = f.selectedEmployerType;
      req.employerFirstName = f.employerFirstName;
      req.employerLastName = f.employerLastName;
      req.businessName = f.businessName;
      req.contactPersonName = f.contactPersonName;
      req.contactPersonNumber = f.contactPersonNumber;
      req.gstNumber = f.gstNumber;
      req.businessDescription = f.businessDescription;
    }

    if (f.selectedRole === 'WORKER') {
      req.workerFirstName = f.workerFirstName;
      req.workerLastName = f.workerLastName;
      req.skills = f.skills;
      req.location = f.location;
    }

    return req;
  }
}
