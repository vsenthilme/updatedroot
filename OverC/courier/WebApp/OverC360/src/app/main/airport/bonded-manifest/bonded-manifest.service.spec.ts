import { TestBed } from '@angular/core/testing';

import { BondedManifestService } from './bonded-manifest.service';

describe('BondedManifestService', () => {
  let service: BondedManifestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BondedManifestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
