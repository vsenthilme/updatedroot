import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpstatusComponent } from './opstatus.component';

describe('OpstatusComponent', () => {
  let component: OpstatusComponent;
  let fixture: ComponentFixture<OpstatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OpstatusComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OpstatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
