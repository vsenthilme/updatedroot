import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CcrComponent } from './ccr.component';

describe('CcrComponent', () => {
  let component: CcrComponent;
  let fixture: ComponentFixture<CcrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CcrComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CcrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
