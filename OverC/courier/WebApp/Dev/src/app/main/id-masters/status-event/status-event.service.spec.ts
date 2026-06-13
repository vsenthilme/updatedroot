import { TestBed } from '@angular/core/testing';

import { StatusEventService } from './status-event.service';

describe('StatusEventService', () => {
  let service: StatusEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
