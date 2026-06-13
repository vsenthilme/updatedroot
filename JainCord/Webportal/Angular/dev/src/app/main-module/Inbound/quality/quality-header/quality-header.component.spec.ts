import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QualityHeaderComponent } from './quality-header.component';

describe('QualityHeaderComponent', () => {
  let component: QualityHeaderComponent;
  let fixture: ComponentFixture<QualityHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QualityHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QualityHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
