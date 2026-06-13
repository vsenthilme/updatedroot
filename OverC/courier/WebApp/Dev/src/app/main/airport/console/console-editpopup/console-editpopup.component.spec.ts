import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleEditpopupComponent } from './console-editpopup.component';

describe('ConsoleEditpopupComponent', () => {
  let component: ConsoleEditpopupComponent;
  let fixture: ComponentFixture<ConsoleEditpopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleEditpopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleEditpopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
