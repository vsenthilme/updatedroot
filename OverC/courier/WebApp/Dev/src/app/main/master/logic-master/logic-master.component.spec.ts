import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogicMasterComponent } from './logic-master.component';

describe('LogicMasterComponent', () => {
  let component: LogicMasterComponent;
  let fixture: ComponentFixture<LogicMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LogicMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LogicMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
