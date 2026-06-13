import { TestBed } from '@angular/core/testing';

import { DistrictMappingService } from './district-mapping.service';

describe('DistrictMappingService', () => {
  let service: DistrictMappingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DistrictMappingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
