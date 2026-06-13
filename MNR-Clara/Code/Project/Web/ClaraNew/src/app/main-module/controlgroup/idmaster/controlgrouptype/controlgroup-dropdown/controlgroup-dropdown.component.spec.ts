import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlgroupDropdownComponent } from './controlgroup-dropdown.component';

describe('ControlgroupDropdownComponent', () => {
  let component: ControlgroupDropdownComponent;
  let fixture: ComponentFixture<ControlgroupDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ControlgroupDropdownComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlgroupDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
