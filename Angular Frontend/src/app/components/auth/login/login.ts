import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms'; 
import { loginRequest } from '../../../models/loginRequest.model';
import { Router, RouterModule } from '@angular/router';
import { NgIf } from '@angular/common';
import { ParseSourceFile } from '@angular/compiler';
import { LoginService } from '../../../services/login-service';
import { Observable } from 'rxjs';
import { Jwt } from '../../../models/jwtresponse.model';
import { HttpHeaders } from '@angular/common/http';
import { User } from '../../../models/userresponse.model';
import { routes } from '../../../app.routes';


@Component({
  selector: 'app-login',
  standalone: true, 
  imports: [FormsModule, ReactiveFormsModule, RouterModule], 
  templateUrl: './login.html',
  styleUrls: ['./login.css'] 
})
export class Login implements OnInit {

  constructor(private formBuilder : FormBuilder, private loginService : LoginService, private router : Router){
  }

  isShaking : boolean = false;
  isSubmitted : boolean = false;
  loginForm: FormGroup = new FormGroup({});

  ngOnInit() : void {
    this.loginForm = this.formBuilder.group({
      username : ['',Validators.required],
      password : ['',[Validators.required, Validators.minLength(8)]]
    })

    sessionStorage.clear();
  }
  
  onSubmit() {

  const usernameOrEmail = this.loginForm.get('username')?.value.trim();
  const password = this.loginForm.get('password')?.value.trim();

  let role = '';

    this.submitted()

    if(this.loginForm.invalid){
      this.triggerShake();
      return;
    }
    
    let req: loginRequest = new loginRequest();
    if(usernameOrEmail.includes("@"))
      req.email = usernameOrEmail
    else
      req.username = usernameOrEmail;
    req.password = password

     this.loginService.login(req).subscribe(obj =>{
      sessionStorage.setItem('token',obj.token);
      this.loginService.getUser().subscribe(obj => {
        switch(obj.role) {
          case 'EMPLOYER' : 
            this.router.navigate(['/employer']);
            break;
          case 'WORKER' : 
            this.router.navigate(['/worker']);
            break;
          case 'ADMIN' :
            this.router.navigate(['/admin']);
            break;
        }
       });
     });
     
  }

  triggerShake() {
    this.isShaking = true;
    setTimeout(() => (this.isShaking = false),300);
  }

  submitted(){
    this.isSubmitted = true;
    setTimeout(() => (this.isSubmitted = false),300);
  }
  
}

