import { TestBed } from '@angular/core/testing';

import { AxinterfaceService } from './axinterface.service';

describe('AxinterfaceService', () => {
  let service: AxinterfaceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AxinterfaceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
