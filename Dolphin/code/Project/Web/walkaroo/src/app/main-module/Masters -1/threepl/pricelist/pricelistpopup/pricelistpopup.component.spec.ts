import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PricelistpopupComponent } from './pricelistpopup.component';

describe('PricelistpopupComponent', () => {
  let component: PricelistpopupComponent;
  let fixture: ComponentFixture<PricelistpopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PricelistpopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PricelistpopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
