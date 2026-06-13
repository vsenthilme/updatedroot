import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictMappingComponent } from './district-mapping.component';

describe('DistrictMappingComponent', () => {
  let component: DistrictMappingComponent;
  let fixture: ComponentFixture<DistrictMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DistrictMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DistrictMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
