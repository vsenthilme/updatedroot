import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CityMappingNewComponent } from './city-mapping-new.component';

describe('CityMappingNewComponent', () => {
  let component: CityMappingNewComponent;
  let fixture: ComponentFixture<CityMappingNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CityMappingNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CityMappingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
