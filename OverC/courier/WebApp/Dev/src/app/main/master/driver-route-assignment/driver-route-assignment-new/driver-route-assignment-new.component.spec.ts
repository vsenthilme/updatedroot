import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverRouteAssignmentNewComponent } from './driver-route-assignment-new.component';

describe('DriverRouteAssignmentNewComponent', () => {
  let component: DriverRouteAssignmentNewComponent;
  let fixture: ComponentFixture<DriverRouteAssignmentNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DriverRouteAssignmentNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DriverRouteAssignmentNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
