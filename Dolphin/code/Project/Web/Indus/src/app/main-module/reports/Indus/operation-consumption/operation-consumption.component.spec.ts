import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperationConsumptionComponent } from './operation-consumption.component';

describe('OperationConsumptionComponent', () => {
  let component: OperationConsumptionComponent;
  let fixture: ComponentFixture<OperationConsumptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperationConsumptionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperationConsumptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
