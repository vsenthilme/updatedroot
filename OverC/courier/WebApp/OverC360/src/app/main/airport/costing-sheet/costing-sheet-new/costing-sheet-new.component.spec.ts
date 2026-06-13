import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CostingSheetNewComponent } from './costing-sheet-new.component';

describe('CostingSheetNewComponent', () => {
  let component: CostingSheetNewComponent;
  let fixture: ComponentFixture<CostingSheetNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CostingSheetNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CostingSheetNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
