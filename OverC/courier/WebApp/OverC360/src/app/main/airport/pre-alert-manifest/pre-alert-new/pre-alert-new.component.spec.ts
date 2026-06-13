import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreAlertNewComponent } from './pre-alert-new.component';

describe('PreAlertNewComponent', () => {
  let component: PreAlertNewComponent;
  let fixture: ComponentFixture<PreAlertNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreAlertNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreAlertNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
