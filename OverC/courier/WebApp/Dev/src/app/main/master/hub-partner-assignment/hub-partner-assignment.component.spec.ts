import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HubPartnerAssignmentComponent } from './hub-partner-assignment.component';

describe('HubPartnerAssignmentComponent', () => {
  let component: HubPartnerAssignmentComponent;
  let fixture: ComponentFixture<HubPartnerAssignmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HubPartnerAssignmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HubPartnerAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
