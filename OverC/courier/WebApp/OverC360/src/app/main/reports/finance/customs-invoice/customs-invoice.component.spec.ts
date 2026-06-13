import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsInvoiceComponent } from './customs-invoice.component';

describe('CustomsInvoiceComponent', () => {
  let component: CustomsInvoiceComponent;
  let fixture: ComponentFixture<CustomsInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsInvoiceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
