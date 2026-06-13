import { TestBed } from '@angular/core/testing';

import { ConsignmentTypeService } from './consignment-type.service';

describe('ConsignmentTypeService', () => {
  let service: ConsignmentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsignmentTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
