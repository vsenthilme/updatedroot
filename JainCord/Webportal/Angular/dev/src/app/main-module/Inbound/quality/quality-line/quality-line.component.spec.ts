import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QualityLineComponent } from './quality-line.component';

describe('QualityLineComponent', () => {
  let component: QualityLineComponent;
  let fixture: ComponentFixture<QualityLineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QualityLineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QualityLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
