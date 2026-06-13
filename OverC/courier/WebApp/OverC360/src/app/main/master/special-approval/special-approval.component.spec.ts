import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialApprovalComponent } from './special-approval.component';

describe('SpecialApprovalComponent', () => {
  let component: SpecialApprovalComponent;
  let fixture: ComponentFixture<SpecialApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SpecialApprovalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpecialApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
