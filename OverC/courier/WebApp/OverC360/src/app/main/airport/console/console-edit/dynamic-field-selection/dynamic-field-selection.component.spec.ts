import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicFieldSelectionComponent } from './dynamic-field-selection.component';

describe('DynamicFieldSelectionComponent', () => {
  let component: DynamicFieldSelectionComponent;
  let fixture: ComponentFixture<DynamicFieldSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DynamicFieldSelectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DynamicFieldSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
