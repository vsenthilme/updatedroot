import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortingMasterComponent } from './sorting-master.component';

describe('SortingMasterComponent', () => {
  let component: SortingMasterComponent;
  let fixture: ComponentFixture<SortingMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SortingMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SortingMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
