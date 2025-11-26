import { Component, Inject } from '@angular/core';
import { MatDialogRef, MatDialogContent, MatDialogActions, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-logout-dialog-component',
  imports: [MatDialogContent, MatDialogActions],
  templateUrl: './dialog-component.html',
  styleUrl: './dialog-component.css'
})
export class DialogComponent {

    message : string | undefined;
    title: string|undefined;
    buttonText: string|undefined


  constructor(
    @Inject(MAT_DIALOG_DATA) public data:{message: string; title:string; buttonText:string}
    , private dialogRef: MatDialogRef<DialogComponent>) {
      this.message=data.message;
      this.title=data.title;
      this.buttonText=data.buttonText
    }


  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }
}
