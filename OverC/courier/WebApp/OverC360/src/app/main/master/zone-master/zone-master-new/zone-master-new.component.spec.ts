import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoneMasterNewComponent } from './zone-master-new.component';

describe('ZoneMasterNewComponent', () => {
  let component: ZoneMasterNewComponent;
  let fixture: ComponentFixture<ZoneMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ZoneMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ZoneMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
