import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignemntPopupComponent } from './assignemnt-popup.component';

describe('AssignemntPopupComponent', () => {
  let component: AssignemntPopupComponent;
  let fixture: ComponentFixture<AssignemntPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AssignemntPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AssignemntPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
