import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubProductNewComponent } from './sub-product-new.component';

describe('SubProductNewComponent', () => {
  let component: SubProductNewComponent;
  let fixture: ComponentFixture<SubProductNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubProductNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubProductNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
