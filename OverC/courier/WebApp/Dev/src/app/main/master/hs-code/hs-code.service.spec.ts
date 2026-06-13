import { TestBed } from '@angular/core/testing';

import { HsCodeService } from './hs-code.service';

describe('HsCodeService', () => {
  let service: HsCodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HsCodeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
