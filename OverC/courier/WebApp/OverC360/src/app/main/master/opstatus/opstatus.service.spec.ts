import { TestBed } from '@angular/core/testing';

import { OpstatusService } from './opstatus.service';

describe('OpstatusService', () => {
  let service: OpstatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpstatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
