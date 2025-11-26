import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIcon } from "@angular/material/icon";
import { MatToolbar } from "@angular/material/toolbar";
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent} from '../dialog-component/dialog-component';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MatIcon, MatToolbar],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class NavbarComponent {
  constructor(private dialog: MatDialog) {}

  onPowerClick(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '400px',
      data: {message:"Are you sure you want to logout?" , title : "Logout Confirmation" , buttonText: 'Logout'}});

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        localStorage.clear();
        window.location.href = '/'; 
      }
    });
  }
  
  

}
