import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialApprovalNewComponent } from './special-approval-new.component';

describe('SpecialApprovalNewComponent', () => {
  let component: SpecialApprovalNewComponent;
  let fixture: ComponentFixture<SpecialApprovalNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SpecialApprovalNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpecialApprovalNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
