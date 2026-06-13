import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsChargesMasterComponent } from './customs-charges-master.component';

describe('CustomsChargesMasterComponent', () => {
  let component: CustomsChargesMasterComponent;
  let fixture: ComponentFixture<CustomsChargesMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsChargesMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsChargesMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
