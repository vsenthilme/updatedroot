import { TestBed } from '@angular/core/testing';

import { CountryMappingService } from './country-mapping.service';

describe('CountryMappingService', () => {
  let service: CountryMappingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CountryMappingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
