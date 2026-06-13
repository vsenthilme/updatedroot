import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelSalesReturnComponent } from './cancel-sales-return.component';

describe('CancelSalesReturnComponent', () => {
  let component: CancelSalesReturnComponent;
  let fixture: ComponentFixture<CancelSalesReturnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelSalesReturnComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelSalesReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
