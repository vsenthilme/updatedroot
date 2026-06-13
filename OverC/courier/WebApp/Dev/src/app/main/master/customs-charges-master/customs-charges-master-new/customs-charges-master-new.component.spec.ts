import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsChargesMasterNewComponent } from './customs-charges-master-new.component';

describe('CustomsChargesMasterNewComponent', () => {
  let component: CustomsChargesMasterNewComponent;
  let fixture: ComponentFixture<CustomsChargesMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsChargesMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsChargesMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
