import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsInvoiceCreateComponent } from './customs-invoice-create.component';

describe('CustomsInvoiceCreateComponent', () => {
  let component: CustomsInvoiceCreateComponent;
  let fixture: ComponentFixture<CustomsInvoiceCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsInvoiceCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsInvoiceCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
