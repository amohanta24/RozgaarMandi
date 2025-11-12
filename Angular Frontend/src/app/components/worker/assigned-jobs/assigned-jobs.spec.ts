import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignedJobs } from './assigned-jobs';

describe('AssignedJobs', () => {
  let component: AssignedJobs;
  let fixture: ComponentFixture<AssignedJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssignedJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssignedJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
