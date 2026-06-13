import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverRouteAssignmentComponent } from './driver-route-assignment.component';

describe('DriverRouteAssignmentComponent', () => {
  let component: DriverRouteAssignmentComponent;
  let fixture: ComponentFixture<DriverRouteAssignmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DriverRouteAssignmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DriverRouteAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
