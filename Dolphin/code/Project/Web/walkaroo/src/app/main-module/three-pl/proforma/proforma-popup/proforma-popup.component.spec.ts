import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProformaPopupComponent } from './proforma-popup.component';

describe('ProformaPopupComponent', () => {
  let component: ProformaPopupComponent;
  let fixture: ComponentFixture<ProformaPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProformaPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProformaPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
