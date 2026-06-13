import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryPutwayComponent } from './inventory-putway.component';

describe('InventoryPutwayComponent', () => {
  let component: InventoryPutwayComponent;
  let fixture: ComponentFixture<InventoryPutwayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventoryPutwayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryPutwayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
