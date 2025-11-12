import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedJobs } from './completed-jobs';

describe('CompletedJobs', () => {
  let component: CompletedJobs;
  let fixture: ComponentFixture<CompletedJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompletedJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompletedJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
