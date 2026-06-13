import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirportCodeComponent } from './airport-code.component';

describe('AirportCodeComponent', () => {
  let component: AirportCodeComponent;
  let fixture: ComponentFixture<AirportCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AirportCodeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AirportCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
