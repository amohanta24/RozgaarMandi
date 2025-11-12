import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployerCompletedJobs } from './employer-completed-jobs';

describe('EmployerCompletedJobs', () => {
  let component: EmployerCompletedJobs;
  let fixture: ComponentFixture<EmployerCompletedJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployerCompletedJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployerCompletedJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
