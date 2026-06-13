import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleTrackingComponent } from './console-tracking.component';

describe('ConsoleTrackingComponent', () => {
  let component: ConsoleTrackingComponent;
  let fixture: ComponentFixture<ConsoleTrackingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleTrackingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleTrackingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
