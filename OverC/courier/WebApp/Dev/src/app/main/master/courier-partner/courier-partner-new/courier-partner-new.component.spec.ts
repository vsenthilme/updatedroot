import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourierPartnerNewComponent } from './courier-partner-new.component';

describe('CourierPartnerNewComponent', () => {
  let component: CourierPartnerNewComponent;
  let fixture: ComponentFixture<CourierPartnerNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CourierPartnerNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CourierPartnerNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
