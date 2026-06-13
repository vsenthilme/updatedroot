import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DduInvoiceComponent } from './ddu-invoice.component';

describe('DduInvoiceComponent', () => {
  let component: DduInvoiceComponent;
  let fixture: ComponentFixture<DduInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DduInvoiceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DduInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
