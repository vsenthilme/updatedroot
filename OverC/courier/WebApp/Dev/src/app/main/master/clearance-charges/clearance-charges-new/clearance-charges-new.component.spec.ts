import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClearanceChargesNewComponent } from './clearance-charges-new.component';

describe('ClearanceChargesNewComponent', () => {
  let component: ClearanceChargesNewComponent;
  let fixture: ComponentFixture<ClearanceChargesNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClearanceChargesNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClearanceChargesNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
