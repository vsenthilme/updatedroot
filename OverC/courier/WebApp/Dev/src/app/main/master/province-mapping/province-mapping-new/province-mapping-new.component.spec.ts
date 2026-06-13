import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvinceMappingNewComponent } from './province-mapping-new.component';

describe('ProvinceMappingNewComponent', () => {
  let component: ProvinceMappingNewComponent;
  let fixture: ComponentFixture<ProvinceMappingNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProvinceMappingNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProvinceMappingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
