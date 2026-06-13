import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortingMasterNewComponent } from './sorting-master-new.component';

describe('SortingMasterNewComponent', () => {
  let component: SortingMasterNewComponent;
  let fixture: ComponentFixture<SortingMasterNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SortingMasterNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SortingMasterNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
