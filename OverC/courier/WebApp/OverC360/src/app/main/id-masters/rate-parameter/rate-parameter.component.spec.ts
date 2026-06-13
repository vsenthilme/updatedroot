import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateParameterComponent } from './rate-parameter.component';

describe('RateParameterComponent', () => {
  let component: RateParameterComponent;
  let fixture: ComponentFixture<RateParameterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RateParameterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RateParameterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
