import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopiniComponent } from './shopini.component';

describe('ShopiniComponent', () => {
  let component: ShopiniComponent;
  let fixture: ComponentFixture<ShopiniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopiniComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopiniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
