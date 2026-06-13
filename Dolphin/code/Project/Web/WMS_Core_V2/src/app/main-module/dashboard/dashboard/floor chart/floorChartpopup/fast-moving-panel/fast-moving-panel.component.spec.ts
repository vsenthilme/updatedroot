import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FastMovingPanelComponent } from './fast-moving-panel.component';

describe('FastMovingPanelComponent', () => {
  let component: FastMovingPanelComponent;
  let fixture: ComponentFixture<FastMovingPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FastMovingPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FastMovingPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
