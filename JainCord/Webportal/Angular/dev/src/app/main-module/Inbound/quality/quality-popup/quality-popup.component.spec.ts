import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QualityPopupComponent } from './quality-popup.component';

describe('QualityPopupComponent', () => {
  let component: QualityPopupComponent;
  let fixture: ComponentFixture<QualityPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QualityPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QualityPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
