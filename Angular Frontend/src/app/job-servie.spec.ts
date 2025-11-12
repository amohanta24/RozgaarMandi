import { TestBed } from '@angular/core/testing';

import { JobServie } from './job-servie';

describe('JobServie', () => {
  let service: JobServie;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobServie);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
