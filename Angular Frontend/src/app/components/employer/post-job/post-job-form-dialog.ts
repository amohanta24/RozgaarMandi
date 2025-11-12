import { ChangeDetectionStrategy, Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { MatLabel, MatFormField } from "@angular/material/form-field";


@Component({
    selector: 'dialog-content-example-dialog',
    templateUrl: 'post-job-form-dialog.html',
    imports: [MatDialogModule,MatDialogContent, MatDialogActions,MatButtonModule,MatButton],
    changeDetection: ChangeDetectionStrategy.OnPush,
    styleUrl:'post-job-form-dialog.css'
  })

export class postJobFormDialog implements OnInit{
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<postJobFormDialog>
  ) {}
  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }}