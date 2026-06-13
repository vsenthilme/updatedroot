import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasteroperationNewComponent } from './masteroperation-new.component';

describe('MasteroperationNewComponent', () => {
  let component: MasteroperationNewComponent;
  let fixture: ComponentFixture<MasteroperationNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasteroperationNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasteroperationNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
