import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomTableSelectionComponent } from './custom-table-selection.component';

describe('CustomTableSelectionComponent', () => {
  let component: CustomTableSelectionComponent;
  let fixture: ComponentFixture<CustomTableSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomTableSelectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomTableSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
