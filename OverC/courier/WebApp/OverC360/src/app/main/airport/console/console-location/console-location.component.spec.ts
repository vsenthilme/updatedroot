import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleLocationComponent } from './console-location.component';

describe('ConsoleLocationComponent', () => {
  let component: ConsoleLocationComponent;
  let fixture: ComponentFixture<ConsoleLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleLocationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
