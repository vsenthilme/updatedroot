import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PutawayRejectedComponent } from './putaway-rejected.component';

describe('PutawayRejectedComponent', () => {
  let component: PutawayRejectedComponent;
  let fixture: ComponentFixture<PutawayRejectedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PutawayRejectedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PutawayRejectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
