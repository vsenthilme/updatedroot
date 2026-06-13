import { TestBed } from '@angular/core/testing';

import { HubPartnerAssignmentService } from './hub-partner-assignment.service';

describe('HubPartnerAssignmentService', () => {
  let service: HubPartnerAssignmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HubPartnerAssignmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
