import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictNewComponent } from './district-new.component';

describe('DistrictNewComponent', () => {
  let component: DistrictNewComponent;
  let fixture: ComponentFixture<DistrictNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DistrictNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DistrictNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
