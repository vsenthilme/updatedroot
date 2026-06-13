import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreinboundprodComponent } from './preinboundprod.component';

describe('PreinboundprodComponent', () => {
  let component: PreinboundprodComponent;
  let fixture: ComponentFixture<PreinboundprodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreinboundprodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreinboundprodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
