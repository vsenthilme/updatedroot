import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpstatusNewComponent } from './opstatus-new.component';

describe('OpstatusNewComponent', () => {
  let component: OpstatusNewComponent;
  let fixture: ComponentFixture<OpstatusNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OpstatusNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OpstatusNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
