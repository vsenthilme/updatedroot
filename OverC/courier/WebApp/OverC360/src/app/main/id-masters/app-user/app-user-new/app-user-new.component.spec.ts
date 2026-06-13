import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppUserNewComponent } from './app-user-new.component';

describe('AppUserNewComponent', () => {
  let component: AppUserNewComponent;
  let fixture: ComponentFixture<AppUserNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppUserNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AppUserNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
