import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PickingProductivityComponent } from './picking-productivity.component';

describe('PickingProductivityComponent', () => {
  let component: PickingProductivityComponent;
  let fixture: ComponentFixture<PickingProductivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PickingProductivityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PickingProductivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
