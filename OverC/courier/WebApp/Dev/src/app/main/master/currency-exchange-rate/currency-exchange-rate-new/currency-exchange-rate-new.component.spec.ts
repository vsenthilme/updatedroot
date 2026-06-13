import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrencyExchangeRateNewComponent } from './currency-exchange-rate-new.component';

describe('CurrencyExchangeRateNewComponent', () => {
  let component: CurrencyExchangeRateNewComponent;
  let fixture: ComponentFixture<CurrencyExchangeRateNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CurrencyExchangeRateNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CurrencyExchangeRateNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
