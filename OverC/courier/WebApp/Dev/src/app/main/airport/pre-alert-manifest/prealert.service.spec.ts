import { TestBed } from '@angular/core/testing';

import { PrealertService } from './prealert.service';

describe('PrealertService', () => {
  let service: PrealertService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrealertService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
