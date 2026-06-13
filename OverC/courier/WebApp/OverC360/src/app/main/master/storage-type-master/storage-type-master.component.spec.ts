import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorageTypeMasterComponent } from './storage-type-master.component';

describe('StorageTypeMasterComponent', () => {
  let component: StorageTypeMasterComponent;
  let fixture: ComponentFixture<StorageTypeMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StorageTypeMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StorageTypeMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
