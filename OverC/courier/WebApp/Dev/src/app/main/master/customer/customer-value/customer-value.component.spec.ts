import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerValueComponent } from './customer-value.component';

describe('CustomerValueComponent', () => {
  let component: CustomerValueComponent;
  let fixture: ComponentFixture<CustomerValueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerValueComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomerValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
