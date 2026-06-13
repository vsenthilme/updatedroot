import { TestBed } from '@angular/core/testing';

import { SubProductService } from './sub-product.service';

describe('SubProductService', () => {
  let service: SubProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
