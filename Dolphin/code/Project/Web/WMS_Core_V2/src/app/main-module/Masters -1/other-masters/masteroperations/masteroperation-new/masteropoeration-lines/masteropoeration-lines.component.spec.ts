import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasteropoerationLinesComponent } from './masteropoeration-lines.component';

describe('MasteropoerationLinesComponent', () => {
  let component: MasteropoerationLinesComponent;
  let fixture: ComponentFixture<MasteropoerationLinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasteropoerationLinesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasteropoerationLinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
