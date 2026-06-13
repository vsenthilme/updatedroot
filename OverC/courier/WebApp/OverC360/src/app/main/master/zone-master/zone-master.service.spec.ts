import { TestBed } from '@angular/core/testing';

import { ZoneMasterService } from './zone-master.service';

describe('ZoneMasterService', () => {
  let service: ZoneMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZoneMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
