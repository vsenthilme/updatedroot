import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionHistorylinesComponent } from './transaction-historylines.component';

describe('TransactionHistorylinesComponent', () => {
  let component: TransactionHistorylinesComponent;
  let fixture: ComponentFixture<TransactionHistorylinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionHistorylinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionHistorylinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
