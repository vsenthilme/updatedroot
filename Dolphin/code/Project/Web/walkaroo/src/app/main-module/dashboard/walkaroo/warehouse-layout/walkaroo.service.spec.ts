import { TestBed } from '@angular/core/testing';

import { WalkarooService } from './walkaroo.service';

describe('WalkarooService', () => {
  let service: WalkarooService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WalkarooService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
