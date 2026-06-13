import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PickingDialogComponent } from './picking-dialog.component';

describe('PickingDialogComponent', () => {
  let component: PickingDialogComponent;
  let fixture: ComponentFixture<PickingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PickingDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PickingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
