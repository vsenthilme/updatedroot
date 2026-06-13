import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelSupplierInvoiceComponent } from './cancel-supplier-invoice.component';

describe('CancelSupplierInvoiceComponent', () => {
  let component: CancelSupplierInvoiceComponent;
  let fixture: ComponentFixture<CancelSupplierInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelSupplierInvoiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelSupplierInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
