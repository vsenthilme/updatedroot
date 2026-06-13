import { TestBed } from '@angular/core/testing';

import { ClearanceChargesService } from './clearance-charges.service';

describe('ClearanceChargesService', () => {
  let service: ClearanceChargesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClearanceChargesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
