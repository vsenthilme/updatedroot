import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourierPartnerComponent } from './courier-partner.component';

describe('CourierPartnerComponent', () => {
  let component: CourierPartnerComponent;
  let fixture: ComponentFixture<CourierPartnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CourierPartnerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CourierPartnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
