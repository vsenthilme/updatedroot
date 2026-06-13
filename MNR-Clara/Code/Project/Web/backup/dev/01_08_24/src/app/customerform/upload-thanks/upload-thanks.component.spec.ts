import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadThanksComponent } from './upload-thanks.component';

describe('UploadThanksComponent', () => {
  let component: UploadThanksComponent;
  let fixture: ComponentFixture<UploadThanksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadThanksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadThanksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
