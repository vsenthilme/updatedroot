import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadTypeNewComponent } from './load-type-new.component';

describe('LoadTypeNewComponent', () => {
  let component: LoadTypeNewComponent;
  let fixture: ComponentFixture<LoadTypeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadTypeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoadTypeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
