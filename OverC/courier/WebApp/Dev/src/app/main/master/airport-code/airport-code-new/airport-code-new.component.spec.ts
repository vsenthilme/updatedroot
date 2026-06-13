import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirportCodeNewComponent } from './airport-code-new.component';

describe('AirportCodeNewComponent', () => {
  let component: AirportCodeNewComponent;
  let fixture: ComponentFixture<AirportCodeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AirportCodeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AirportCodeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
