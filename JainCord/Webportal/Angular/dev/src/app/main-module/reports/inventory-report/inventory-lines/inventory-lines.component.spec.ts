import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryLinesComponent } from './inventory-lines.component';

describe('InventoryLinesComponent', () => {
  let component: InventoryLinesComponent;
  let fixture: ComponentFixture<InventoryLinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventoryLinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryLinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
