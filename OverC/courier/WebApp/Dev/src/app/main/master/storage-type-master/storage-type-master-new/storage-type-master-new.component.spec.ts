import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorageTypeMasterNewComponent } from './storage-type-master-new.component';

describe('StorageTypeMasterNewComponent', () => {
  let component: StorageTypeMasterNewComponent;
  let fixture: ComponentFixture<StorageTypeMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StorageTypeMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StorageTypeMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
