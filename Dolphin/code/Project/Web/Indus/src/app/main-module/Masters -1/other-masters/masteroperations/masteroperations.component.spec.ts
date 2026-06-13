import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasteroperationsComponent } from './masteroperations.component';

describe('MasteroperationsComponent', () => {
  let component: MasteroperationsComponent;
  let fixture: ComponentFixture<MasteroperationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasteroperationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasteroperationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
