import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomChecklistComponent } from './custom-checklist.component';

describe('CustomChecklistComponent', () => {
  let component: CustomChecklistComponent;
  let fixture: ComponentFixture<CustomChecklistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomChecklistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomChecklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
