import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryMappingComponent } from './country-mapping.component';

describe('CountryMappingComponent', () => {
  let component: CountryMappingComponent;
  let fixture: ComponentFixture<CountryMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CountryMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CountryMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
