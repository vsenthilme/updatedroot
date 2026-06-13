import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleNewComponent } from './console-new.component';

describe('ConsoleNewComponent', () => {
  let component: ConsoleNewComponent;
  let fixture: ComponentFixture<ConsoleNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
