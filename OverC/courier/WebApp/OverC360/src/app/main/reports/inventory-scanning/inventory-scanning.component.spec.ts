import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryScanningComponent } from './inventory-scanning.component';

describe('InventoryScanningComponent', () => {
  let component: InventoryScanningComponent;
  let fixture: ComponentFixture<InventoryScanningComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InventoryScanningComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InventoryScanningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
