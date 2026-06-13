import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoneTypeMasterComponent } from './zone-type-master.component';

describe('ZoneTypeMasterComponent', () => {
  let component: ZoneTypeMasterComponent;
  let fixture: ComponentFixture<ZoneTypeMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ZoneTypeMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ZoneTypeMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
