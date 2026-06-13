import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryputwayComponent } from './inventoryputway.component';

describe('InventoryputwayComponent', () => {
  let component: InventoryputwayComponent;
  let fixture: ComponentFixture<InventoryputwayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventoryputwayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryputwayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
