import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent, MatDialogActions } from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog-componen',
  imports: [MatDialogContent, MatDialogActions],
  templateUrl: './error-dialog-component.html',
  styleUrl: './error-dialog-component.css'
})
export class ErrorDialogComponen {

  msg: string | undefined;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { message: string },
    private dialogRef: MatDialogRef<ErrorDialogComponen>
  ) {
    this.msg = data.message;
  }
  
  close(): void {
    this.dialogRef.close();
  }




  

}
