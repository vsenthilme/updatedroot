import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateParameterNewComponent } from './rate-parameter-new.component';

describe('RateParameterNewComponent', () => {
  let component: RateParameterNewComponent;
  let fixture: ComponentFixture<RateParameterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RateParameterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RateParameterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
