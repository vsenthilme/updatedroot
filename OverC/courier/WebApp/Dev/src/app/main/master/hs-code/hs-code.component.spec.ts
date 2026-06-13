import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HsCodeComponent } from './hs-code.component';

describe('HsCodeComponent', () => {
  let component: HsCodeComponent;
  let fixture: ComponentFixture<HsCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HsCodeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HsCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
