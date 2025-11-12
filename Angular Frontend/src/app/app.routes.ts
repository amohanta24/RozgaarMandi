import { NgModule } from '@angular/core';
import { Login } from './components/auth/login/login';
import { Signup } from './components/auth/signup/signup';
import { Routes } from '@angular/router';
import { EmployerDashboard } from './components/employer/employer-dashboard/employer-dashboard';
import { WorkerDashboardComponent } from './components/worker/worker-dashboard/worker-dashboard';
import { NavbarComponent } from './components/shared/navbar/navbar';



export const routes: Routes = [
    {path:"", component: Login},
    {path:"signup",component: Signup},
    {path:"worker", component:WorkerDashboardComponent},
    {path:"employer", component:EmployerDashboard},
    {path:"navbar", component:NavbarComponent}
];
 