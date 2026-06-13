import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterrecipeNewComponent } from './masterrecipe-new.component';

describe('MasterrecipeNewComponent', () => {
  let component: MasterrecipeNewComponent;
  let fixture: ComponentFixture<MasterrecipeNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterrecipeNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterrecipeNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
