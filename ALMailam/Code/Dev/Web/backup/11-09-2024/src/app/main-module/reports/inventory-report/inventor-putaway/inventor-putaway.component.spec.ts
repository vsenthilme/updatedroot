import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorPutawayComponent } from './inventor-putaway.component';

describe('InventorPutawayComponent', () => {
  let component: InventorPutawayComponent;
  let fixture: ComponentFixture<InventorPutawayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventorPutawayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventorPutawayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
