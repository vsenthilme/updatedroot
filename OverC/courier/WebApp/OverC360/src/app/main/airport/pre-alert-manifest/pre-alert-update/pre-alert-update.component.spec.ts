import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreAlertUpdateComponent } from './pre-alert-update.component';

describe('PreAlertUpdateComponent', () => {
  let component: PreAlertUpdateComponent;
  let fixture: ComponentFixture<PreAlertUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreAlertUpdateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreAlertUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
