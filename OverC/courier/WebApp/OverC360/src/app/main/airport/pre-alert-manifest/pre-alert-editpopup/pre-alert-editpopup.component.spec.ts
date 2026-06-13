import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreAlertEditpopupComponent } from './pre-alert-editpopup.component';

describe('PreAlertEditpopupComponent', () => {
  let component: PreAlertEditpopupComponent;
  let fixture: ComponentFixture<PreAlertEditpopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreAlertEditpopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreAlertEditpopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
