import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionOrderConfirmComponent } from './production-order-confirm.component';

describe('ProductionOrderConfirmComponent', () => {
  let component: ProductionOrderConfirmComponent;
  let fixture: ComponentFixture<ProductionOrderConfirmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductionOrderConfirmComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionOrderConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
