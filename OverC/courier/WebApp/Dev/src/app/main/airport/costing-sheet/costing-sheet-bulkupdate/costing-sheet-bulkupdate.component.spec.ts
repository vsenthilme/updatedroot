import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CostingSheetBulkupdateComponent } from './costing-sheet-bulkupdate.component';

describe('CostingSheetBulkupdateComponent', () => {
  let component: CostingSheetBulkupdateComponent;
  let fixture: ComponentFixture<CostingSheetBulkupdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CostingSheetBulkupdateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CostingSheetBulkupdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
