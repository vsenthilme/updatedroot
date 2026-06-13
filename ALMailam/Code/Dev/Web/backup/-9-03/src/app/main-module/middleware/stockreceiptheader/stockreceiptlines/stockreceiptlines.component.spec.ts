import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockreceiptlinesComponent } from './stockreceiptlines.component';

describe('StockreceiptlinesComponent', () => {
  let component: StockreceiptlinesComponent;
  let fixture: ComponentFixture<StockreceiptlinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockreceiptlinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockreceiptlinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
