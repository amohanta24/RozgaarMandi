import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Jwt } from '../models/jwtresponse.model';
import { loginRequest } from '../models/loginRequest.model';
import { Observable } from 'rxjs';
import { User } from '../models/userresponse.model';
import { environment } from '../../environments/environment.development';
import { signUpRequest } from '../models/signUpRequest.model';
import { UserResponse } from '../models/UserResponse';


@Injectable({
  providedIn: 'root'
})
export class LoginService {


  constructor(private httpClient : HttpClient){
  }

  private hostUrl = environment.hostUrl;
  loginUri : string =  `${this.hostUrl}/public/login`;
  getUserUri : string = `${this.hostUrl}/user/getUser`;
  signUpUri : string = `${this.hostUrl}/public/signUp`;
  
  login(loginRequest : loginRequest) : Observable<Jwt>{
    return this.httpClient.post<Jwt>(this.loginUri,loginRequest);
  }

  getUser() : Observable<User> {
    return this.httpClient.get<User>(this.getUserUri);
  }

  signUp(signUpReq: signUpRequest) : Observable<UserResponse>{
    return this.httpClient.post<UserResponse>(this.signUpUri,signUpReq);
  }
  
}
