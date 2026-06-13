import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeChildComponent } from './recipe-child.component';

describe('RecipeChildComponent', () => {
  let component: RecipeChildComponent;
  let fixture: ComponentFixture<RecipeChildComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeChildComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
