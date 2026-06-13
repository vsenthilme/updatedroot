import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentStatusPopupComponent } from './consignment-status-popup.component';

describe('ConsignmentStatusPopupComponent', () => {
  let component: ConsignmentStatusPopupComponent;
  let fixture: ComponentFixture<ConsignmentStatusPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignmentStatusPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignmentStatusPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
