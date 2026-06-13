import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentTypeComponent } from './consignment-type.component';

describe('ConsignmentTypeComponent', () => {
  let component: ConsignmentTypeComponent;
  let fixture: ComponentFixture<ConsignmentTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignmentTypeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignmentTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
