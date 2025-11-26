import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorDialogComponen } from './error-dialog-component';

describe('ErrorDialogComponen', () => {
  let component: ErrorDialogComponen;
  let fixture: ComponentFixture<ErrorDialogComponen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorDialogComponen]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ErrorDialogComponen);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
