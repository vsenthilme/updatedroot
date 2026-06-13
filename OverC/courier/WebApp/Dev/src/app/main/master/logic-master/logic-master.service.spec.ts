import { TestBed } from '@angular/core/testing';

import { LogicMasterService } from './logic-master.service';

describe('LogicMasterService', () => {
  let service: LogicMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogicMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
