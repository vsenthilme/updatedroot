import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectedReasonComponent } from './rejected-reason.component';

describe('RejectedReasonComponent', () => {
  let component: RejectedReasonComponent;
  let fixture: ComponentFixture<RejectedReasonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RejectedReasonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectedReasonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
