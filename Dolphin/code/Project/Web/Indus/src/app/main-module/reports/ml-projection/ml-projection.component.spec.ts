import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MlProjectionComponent } from './ml-projection.component';

describe('MlProjectionComponent', () => {
  let component: MlProjectionComponent;
  let fixture: ComponentFixture<MlProjectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MlProjectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MlProjectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
