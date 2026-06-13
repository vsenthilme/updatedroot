import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreAlertBulkComponent } from './pre-alert-bulk.component';

describe('PreAlertBulkComponent', () => {
  let component: PreAlertBulkComponent;
  let fixture: ComponentFixture<PreAlertBulkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreAlertBulkComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreAlertBulkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
