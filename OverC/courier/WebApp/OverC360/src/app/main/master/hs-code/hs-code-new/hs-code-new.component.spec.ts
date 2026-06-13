import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HsCodeNewComponent } from './hs-code-new.component';

describe('HsCodeNewComponent', () => {
  let component: HsCodeNewComponent;
  let fixture: ComponentFixture<HsCodeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HsCodeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HsCodeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
