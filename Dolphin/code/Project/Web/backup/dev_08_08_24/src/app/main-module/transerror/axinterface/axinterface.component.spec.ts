import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AxinterfaceComponent } from './axinterface.component';

describe('AxinterfaceComponent', () => {
  let component: AxinterfaceComponent;
  let fixture: ComponentFixture<AxinterfaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AxinterfaceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AxinterfaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
