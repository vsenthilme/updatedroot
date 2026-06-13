import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomsCostingComponent } from './customs-costing.component';

describe('CustomsCostingComponent', () => {
  let component: CustomsCostingComponent;
  let fixture: ComponentFixture<CustomsCostingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomsCostingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomsCostingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
