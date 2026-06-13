import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CcrEditComponent } from './ccr-edit.component';

describe('CcrEditComponent', () => {
  let component: CcrEditComponent;
  let fixture: ComponentFixture<CcrEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CcrEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CcrEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
