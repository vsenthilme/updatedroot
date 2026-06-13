import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterrecipeTableComponent } from './masterrecipe-table.component';

describe('MasterrecipeTableComponent', () => {
  let component: MasterrecipeTableComponent;
  let fixture: ComponentFixture<MasterrecipeTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterrecipeTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterrecipeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
