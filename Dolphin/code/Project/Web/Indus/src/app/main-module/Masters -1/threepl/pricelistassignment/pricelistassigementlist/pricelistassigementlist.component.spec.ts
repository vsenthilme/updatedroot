import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PricelistassigementlistComponent } from './pricelistassigementlist.component';

describe('PricelistassigementlistComponent', () => {
  let component: PricelistassigementlistComponent;
  let fixture: ComponentFixture<PricelistassigementlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PricelistassigementlistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PricelistassigementlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
