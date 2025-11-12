import { Component } from '@angular/core';
import { MatDialogRef, MatDialogContent, MatDialogActions } from '@angular/material/dialog';

@Component({
  selector: 'app-logout-dialog-component',
  imports: [MatDialogContent, MatDialogActions],
  templateUrl: './logout-dialog-component.html',
  styleUrl: './logout-dialog-component.css'
})
export class LogoutDialogComponent {
  constructor(private dialogRef: MatDialogRef<LogoutDialogComponent>) {}

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }
}
