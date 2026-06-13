import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceTypeNewComponent } from './service-type-new.component';

describe('ServiceTypeNewComponent', () => {
  let component: ServiceTypeNewComponent;
  let fixture: ComponentFixture<ServiceTypeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServiceTypeNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ServiceTypeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
