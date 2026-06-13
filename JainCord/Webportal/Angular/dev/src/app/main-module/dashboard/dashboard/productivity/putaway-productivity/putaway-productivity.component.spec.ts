import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PutawayProductivityComponent } from './putaway-productivity.component';

describe('PutawayProductivityComponent', () => {
  let component: PutawayProductivityComponent;
  let fixture: ComponentFixture<PutawayProductivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PutawayProductivityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PutawayProductivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
