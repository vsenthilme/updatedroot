import { TestBed } from '@angular/core/testing';

import { RateParameterService } from './rate-parameter.service';

describe('RateParameterService', () => {
  let service: RateParameterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RateParameterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
