import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreinboundprodNewComponent } from './preinboundprod-new.component';

describe('PreinboundprodNewComponent', () => {
  let component: PreinboundprodNewComponent;
  let fixture: ComponentFixture<PreinboundprodNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreinboundprodNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreinboundprodNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
