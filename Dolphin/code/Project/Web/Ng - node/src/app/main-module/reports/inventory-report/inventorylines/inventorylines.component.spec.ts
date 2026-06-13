import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorylinesComponent } from './inventorylines.component';

describe('InventorylinesComponent', () => {
  let component: InventorylinesComponent;
  let fixture: ComponentFixture<InventorylinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventorylinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventorylinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
