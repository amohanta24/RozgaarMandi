import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenJobs } from './open-jobs';

describe('OpenJobs', () => {
  let component: OpenJobs;
  let fixture: ComponentFixture<OpenJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpenJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpenJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
