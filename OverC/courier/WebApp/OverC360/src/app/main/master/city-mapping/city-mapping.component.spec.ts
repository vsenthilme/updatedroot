import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CityMappingComponent } from './city-mapping.component';

describe('CityMappingComponent', () => {
  let component: CityMappingComponent;
  let fixture: ComponentFixture<CityMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CityMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CityMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
