import { TestBed } from '@angular/core/testing';

import { CustomsChargesMasterService } from './customs-charges-master.service';

describe('CustomsChargesMasterService', () => {
  let service: CustomsChargesMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomsChargesMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
