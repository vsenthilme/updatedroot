import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CcrNewComponent } from './ccr-new.component';

describe('CcrNewComponent', () => {
  let component: CcrNewComponent;
  let fixture: ComponentFixture<CcrNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CcrNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CcrNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
