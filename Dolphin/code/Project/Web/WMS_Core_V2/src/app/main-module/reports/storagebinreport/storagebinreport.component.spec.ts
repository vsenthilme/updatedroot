import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoragebinreportComponent } from './storagebinreport.component';

describe('StoragebinreportComponent', () => {
  let component: StoragebinreportComponent;
  let fixture: ComponentFixture<StoragebinreportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoragebinreportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoragebinreportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
