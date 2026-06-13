import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreparationDetailsComponent } from './preparation-details.component';

describe('PreparationDetailsComponent', () => {
  let component: PreparationDetailsComponent;
  let fixture: ComponentFixture<PreparationDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreparationDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreparationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
