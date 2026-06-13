import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleTransferComponent } from './console-transfer.component';

describe('ConsoleTransferComponent', () => {
  let component: ConsoleTransferComponent;
  let fixture: ComponentFixture<ConsoleTransferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleTransferComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
