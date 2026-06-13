import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddliinesComponent } from './addliines.component';

describe('AddliinesComponent', () => {
  let component: AddliinesComponent;
  let fixture: ComponentFixture<AddliinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddliinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddliinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
