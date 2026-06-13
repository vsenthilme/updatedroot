import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesorderlinesComponent } from './salesorderlines.component';

describe('SalesorderlinesComponent', () => {
  let component: SalesorderlinesComponent;
  let fixture: ComponentFixture<SalesorderlinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SalesorderlinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesorderlinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
