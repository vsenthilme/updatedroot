import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WalkarooLayoutComponent } from './walkaroo-layout.component';

describe('WalkarooLayoutComponent', () => {
  let component: WalkarooLayoutComponent;
  let fixture: ComponentFixture<WalkarooLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WalkarooLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WalkarooLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
