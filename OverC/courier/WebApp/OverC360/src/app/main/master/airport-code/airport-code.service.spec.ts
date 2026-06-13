import { TestBed } from '@angular/core/testing';

import { AirportCodeService } from './airport-code.service';

describe('AirportCodeService', () => {
  let service: AirportCodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirportCodeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
