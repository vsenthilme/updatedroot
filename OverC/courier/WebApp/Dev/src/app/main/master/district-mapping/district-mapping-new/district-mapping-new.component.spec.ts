import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictMappingNewComponent } from './district-mapping-new.component';

describe('DistrictMappingNewComponent', () => {
  let component: DistrictMappingNewComponent;
  let fixture: ComponentFixture<DistrictMappingNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DistrictMappingNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DistrictMappingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
