import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionOrderNewComponent } from './production-order-new.component';

describe('ProductionOrderNewComponent', () => {
  let component: ProductionOrderNewComponent;
  let fixture: ComponentFixture<ProductionOrderNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductionOrderNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionOrderNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
