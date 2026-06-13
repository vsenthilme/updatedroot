import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignmentTypeNewComponent } from './consignment-type-new.component';

describe('ConsignmentTypeNewComponent', () => {
  let component: ConsignmentTypeNewComponent;
  let fixture: ComponentFixture<ConsignmentTypeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignmentTypeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignmentTypeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
