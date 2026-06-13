import { TestBed } from '@angular/core/testing';

import { DriverRouteAssignmentService } from './driver-route-assignment.service';

describe('DriverRouteAssignmentService', () => {
  let service: DriverRouteAssignmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriverRouteAssignmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
