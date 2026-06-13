import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClearanceChargesComponent } from './clearance-charges.component';

describe('ClearanceChargesComponent', () => {
  let component: ClearanceChargesComponent;
  let fixture: ComponentFixture<ClearanceChargesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClearanceChargesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClearanceChargesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
