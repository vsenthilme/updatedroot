import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvinceNewComponent } from './province-new.component';

describe('ProvinceNewComponent', () => {
  let component: ProvinceNewComponent;
  let fixture: ComponentFixture<ProvinceNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProvinceNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProvinceNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
