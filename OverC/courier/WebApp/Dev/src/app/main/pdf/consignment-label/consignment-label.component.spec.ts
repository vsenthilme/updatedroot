import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentLabelComponent } from './consignment-label.component';

describe('ConsignmentLabelComponent', () => {
  let component: ConsignmentLabelComponent;
  let fixture: ComponentFixture<ConsignmentLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignmentLabelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignmentLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
