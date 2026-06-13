import { TestBed } from '@angular/core/testing';

import { ProvinceMappingService } from './province-mapping.service';

describe('ProvinceMappingService', () => {
  let service: ProvinceMappingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProvinceMappingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
