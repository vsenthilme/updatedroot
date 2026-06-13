import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CcrEditpopupComponent } from './ccr-editpopup.component';

describe('CcrEditpopupComponent', () => {
  let component: CcrEditpopupComponent;
  let fixture: ComponentFixture<CcrEditpopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CcrEditpopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CcrEditpopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
