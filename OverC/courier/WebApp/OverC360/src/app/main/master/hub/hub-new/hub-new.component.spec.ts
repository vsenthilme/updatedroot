import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HubNewComponent } from './hub-new.component';

describe('HubNewComponent', () => {
  let component: HubNewComponent;
  let fixture: ComponentFixture<HubNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HubNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HubNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
