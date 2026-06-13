import { TestBed } from '@angular/core/testing';

import { CourierPartnerService } from './courier-partner.service';

describe('CourierPartnerService', () => {
  let service: CourierPartnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourierPartnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
