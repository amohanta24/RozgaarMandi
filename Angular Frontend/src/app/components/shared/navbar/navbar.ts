import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIcon } from "@angular/material/icon";
import { MatToolbar } from "@angular/material/toolbar";
import { MatDialog } from '@angular/material/dialog';
import { LogoutDialogComponent } from '../logout-dialog-component/logout-dialog-component';

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
    const dialogRef = this.dialog.open(LogoutDialogComponent, {
      width: '400px',
      panelClass: 'logout-dialog-panel',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        localStorage.clear();
        window.location.href = '/'; 
      }
    });
  }
  
  

}
