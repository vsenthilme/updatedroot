import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClearancePopupComponent } from './clearance-popup.component';

describe('ClearancePopupComponent', () => {
  let component: ClearancePopupComponent;
  let fixture: ComponentFixture<ClearancePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClearancePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClearancePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
