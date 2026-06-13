import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessOperationComponent } from './process-operation.component';

describe('ProcessOperationComponent', () => {
  let component: ProcessOperationComponent;
  let fixture: ComponentFixture<ProcessOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProcessOperationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
