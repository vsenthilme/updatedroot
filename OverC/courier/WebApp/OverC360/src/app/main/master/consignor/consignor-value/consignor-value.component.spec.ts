import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignorValueComponent } from './consignor-value.component';

describe('ConsignorValueComponent', () => {
  let component: ConsignorValueComponent;
  let fixture: ComponentFixture<ConsignorValueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignorValueComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignorValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
