import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStockAdjustmentComponent } from './create-stock-adjustment.component';

describe('CreateStockAdjustmentComponent', () => {
  let component: CreateStockAdjustmentComponent;
  let fixture: ComponentFixture<CreateStockAdjustmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateStockAdjustmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateStockAdjustmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
