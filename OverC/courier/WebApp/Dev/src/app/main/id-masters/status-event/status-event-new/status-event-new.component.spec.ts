import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusEventNewComponent } from './status-event-new.component';

describe('StatusEventNewComponent', () => {
  let component: StatusEventNewComponent;
  let fixture: ComponentFixture<StatusEventNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StatusEventNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StatusEventNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
