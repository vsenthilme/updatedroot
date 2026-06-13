import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterchildComponent } from './masterchild.component';

describe('MasterchildComponent', () => {
  let component: MasterchildComponent;
  let fixture: ComponentFixture<MasterchildComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterchildComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterchildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
