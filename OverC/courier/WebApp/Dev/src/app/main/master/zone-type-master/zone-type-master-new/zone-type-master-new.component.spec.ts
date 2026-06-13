import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoneTypeMasterNewComponent } from './zone-type-master-new.component';

describe('ZoneTypeMasterNewComponent', () => {
  let component: ZoneTypeMasterNewComponent;
  let fixture: ComponentFixture<ZoneTypeMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ZoneTypeMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ZoneTypeMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
