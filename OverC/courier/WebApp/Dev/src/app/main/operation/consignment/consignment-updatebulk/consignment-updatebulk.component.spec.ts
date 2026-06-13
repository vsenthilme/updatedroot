import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentUpdatebulkComponent } from './consignment-updatebulk.component';

describe('ConsignmentUpdatebulkComponent', () => {
  let component: ConsignmentUpdatebulkComponent;
  let fixture: ComponentFixture<ConsignmentUpdatebulkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignmentUpdatebulkComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignmentUpdatebulkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
