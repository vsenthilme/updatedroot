import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationNewComponent } from './notification-new.component';

describe('NotificationNewComponent', () => {
  let component: NotificationNewComponent;
  let fixture: ComponentFixture<NotificationNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NotificationNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
