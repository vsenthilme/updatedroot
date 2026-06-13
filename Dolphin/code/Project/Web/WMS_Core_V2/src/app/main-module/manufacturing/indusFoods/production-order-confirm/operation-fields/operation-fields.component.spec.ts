import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperationFieldsComponent } from './operation-fields.component';

describe('OperationFieldsComponent', () => {
  let component: OperationFieldsComponent;
  let fixture: ComponentFixture<OperationFieldsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperationFieldsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperationFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
