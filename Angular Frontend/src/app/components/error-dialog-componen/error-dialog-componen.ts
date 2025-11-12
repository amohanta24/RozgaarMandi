import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent, MatDialogActions } from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog-componen',
  imports: [MatDialogContent, MatDialogActions],
  templateUrl: './error-dialog-componen.html',
  styleUrl: './error-dialog-componen.css'
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
