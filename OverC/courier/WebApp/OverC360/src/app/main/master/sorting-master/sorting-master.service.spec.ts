import { TestBed } from '@angular/core/testing';

import { SortingMasterService } from './sorting-master.service';

describe('SortingMasterService', () => {
  let service: SortingMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SortingMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
