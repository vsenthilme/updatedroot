import { TestBed } from '@angular/core/testing';

import { BillModeService } from './bill-mode.service';

describe('BillModeService', () => {
  let service: BillModeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BillModeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
