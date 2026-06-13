import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubProductsValuesComponent } from './sub-products-values.component';

describe('SubProductsValuesComponent', () => {
  let component: SubProductsValuesComponent;
  let fixture: ComponentFixture<SubProductsValuesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubProductsValuesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubProductsValuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
