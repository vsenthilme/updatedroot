import { TestBed } from '@angular/core/testing';

import { StorageTypeMasterService } from './storage-type-master.service';

describe('StorageTypeMasterService', () => {
  let service: StorageTypeMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StorageTypeMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
