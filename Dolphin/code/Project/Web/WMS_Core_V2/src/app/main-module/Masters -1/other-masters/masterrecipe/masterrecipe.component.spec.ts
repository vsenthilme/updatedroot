import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterrecipeComponent } from './masterrecipe.component';

describe('MasterrecipeComponent', () => {
  let component: MasterrecipeComponent;
  let fixture: ComponentFixture<MasterrecipeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterrecipeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterrecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
