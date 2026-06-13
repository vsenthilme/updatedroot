import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BinningproductivityreportComponent } from './binningproductivityreport.component';

describe('BinningproductivityreportComponent', () => {
  let component: BinningproductivityreportComponent;
  let fixture: ComponentFixture<BinningproductivityreportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BinningproductivityreportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BinningproductivityreportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
