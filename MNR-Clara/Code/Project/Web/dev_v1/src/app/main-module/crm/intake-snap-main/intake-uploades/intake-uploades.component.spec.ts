import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntakeUploadesComponent } from './intake-uploades.component';

describe('IntakeUploadesComponent', () => {
  let component: IntakeUploadesComponent;
  let fixture: ComponentFixture<IntakeUploadesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntakeUploadesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IntakeUploadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
