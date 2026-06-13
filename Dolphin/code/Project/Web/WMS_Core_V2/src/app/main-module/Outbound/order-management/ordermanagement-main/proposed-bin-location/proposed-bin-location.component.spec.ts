import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProposedBinLocationComponent } from './proposed-bin-location.component';

describe('ProposedBinLocationComponent', () => {
  let component: ProposedBinLocationComponent;
  let fixture: ComponentFixture<ProposedBinLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProposedBinLocationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProposedBinLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
