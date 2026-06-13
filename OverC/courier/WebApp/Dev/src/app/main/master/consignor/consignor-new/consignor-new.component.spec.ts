import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignorNewComponent } from './consignor-new.component';

describe('ConsignorNewComponent', () => {
  let component: ConsignorNewComponent;
  let fixture: ComponentFixture<ConsignorNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsignorNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsignorNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
