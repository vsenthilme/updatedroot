import { TestBed } from '@angular/core/testing';

import { ZoneTypeMasterService } from './zone-type-master.service';

describe('ZoneTypeMasterService', () => {
  let service: ZoneTypeMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZoneTypeMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
