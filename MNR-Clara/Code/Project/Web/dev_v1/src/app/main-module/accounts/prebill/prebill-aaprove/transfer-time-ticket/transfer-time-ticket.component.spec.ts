import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferTimeTicketComponent } from './transfer-time-ticket.component';

describe('TransferTimeTicketComponent', () => {
  let component: TransferTimeTicketComponent;
  let fixture: ComponentFixture<TransferTimeTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferTimeTicketComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferTimeTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
