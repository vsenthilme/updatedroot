import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryMappingNewComponent } from './country-mapping-new.component';

describe('CountryMappingNewComponent', () => {
  let component: CountryMappingNewComponent;
  let fixture: ComponentFixture<CountryMappingNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CountryMappingNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CountryMappingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
