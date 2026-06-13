import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BillModeNewComponent } from './bill-mode-new.component';

describe('BillModeNewComponent', () => {
  let component: BillModeNewComponent;
  let fixture: ComponentFixture<BillModeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BillModeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BillModeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
