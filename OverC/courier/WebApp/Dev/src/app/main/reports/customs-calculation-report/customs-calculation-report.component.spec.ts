import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsCalculationReportComponent } from './customs-calculation-report.component';

describe('CustomsCalculationReportComponent', () => {
  let component: CustomsCalculationReportComponent;
  let fixture: ComponentFixture<CustomsCalculationReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsCalculationReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsCalculationReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
