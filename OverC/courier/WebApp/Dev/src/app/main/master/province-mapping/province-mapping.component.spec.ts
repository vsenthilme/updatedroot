import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvinceMappingComponent } from './province-mapping.component';

describe('ProvinceMappingComponent', () => {
  let component: ProvinceMappingComponent;
  let fixture: ComponentFixture<ProvinceMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProvinceMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProvinceMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
