import { TestBed } from '@angular/core/testing';

import { SpecialApprovalService } from './special-approval.service';

describe('SpecialApprovalService', () => {
  let service: SpecialApprovalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecialApprovalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
