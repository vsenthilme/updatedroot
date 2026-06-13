import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsCalculationReportLinesComponent } from './customs-calculation-report-lines.component';

describe('CustomsCalculationReportLinesComponent', () => {
  let component: CustomsCalculationReportLinesComponent;
  let fixture: ComponentFixture<CustomsCalculationReportLinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsCalculationReportLinesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsCalculationReportLinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
