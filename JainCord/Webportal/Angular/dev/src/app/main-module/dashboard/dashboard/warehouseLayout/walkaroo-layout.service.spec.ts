import { TestBed } from '@angular/core/testing';

import { WalkarooLayoutService } from './walkaroo-layout.service';

describe('WalkarooLayoutService', () => {
  let service: WalkarooLayoutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WalkarooLayoutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
