import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColdChainLayoutComponent } from './cold-chain-layout.component';

describe('ColdChainLayoutComponent', () => {
  let component: ColdChainLayoutComponent;
  let fixture: ComponentFixture<ColdChainLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColdChainLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ColdChainLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
