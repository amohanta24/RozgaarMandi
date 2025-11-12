import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployerAssignedJobs } from './employer-assigned-jobs';

describe('EmployerAssignedJobs', () => {
  let component: EmployerAssignedJobs;
  let fixture: ComponentFixture<EmployerAssignedJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployerAssignedJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployerAssignedJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
