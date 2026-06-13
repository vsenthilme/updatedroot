import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PalletizationCreateComponent } from './palletization-create.component';

describe('PalletizationCreateComponent', () => {
  let component: PalletizationCreateComponent;
  let fixture: ComponentFixture<PalletizationCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PalletizationCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PalletizationCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
