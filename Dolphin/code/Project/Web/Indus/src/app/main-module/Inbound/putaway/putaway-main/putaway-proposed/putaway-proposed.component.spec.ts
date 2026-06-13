import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PutawayProposedComponent } from './putaway-proposed.component';

describe('PutawayProposedComponent', () => {
  let component: PutawayProposedComponent;
  let fixture: ComponentFixture<PutawayProposedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PutawayProposedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PutawayProposedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
