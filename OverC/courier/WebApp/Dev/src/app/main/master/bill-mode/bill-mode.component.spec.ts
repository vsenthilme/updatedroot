import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BillModeComponent } from './bill-mode.component';

describe('BillModeComponent', () => {
  let component: BillModeComponent;
  let fixture: ComponentFixture<BillModeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BillModeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BillModeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
