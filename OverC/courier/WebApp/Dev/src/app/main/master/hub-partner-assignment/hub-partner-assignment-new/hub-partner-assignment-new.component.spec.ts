import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HubPartnerAssignmentNewComponent } from './hub-partner-assignment-new.component';

describe('HubPartnerAssignmentNewComponent', () => {
  let component: HubPartnerAssignmentNewComponent;
  let fixture: ComponentFixture<HubPartnerAssignmentNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HubPartnerAssignmentNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HubPartnerAssignmentNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
