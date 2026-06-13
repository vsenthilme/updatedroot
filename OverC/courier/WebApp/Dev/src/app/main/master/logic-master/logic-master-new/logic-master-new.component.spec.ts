import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogicMasterNewComponent } from './logic-master-new.component';

describe('LogicMasterNewComponent', () => {
  let component: LogicMasterNewComponent;
  let fixture: ComponentFixture<LogicMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LogicMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LogicMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
