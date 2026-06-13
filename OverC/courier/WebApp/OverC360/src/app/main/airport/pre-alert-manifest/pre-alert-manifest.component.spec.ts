import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreAlertManifestComponent } from './pre-alert-manifest.component';

describe('PreAlertManifestComponent', () => {
  let component: PreAlertManifestComponent;
  let fixture: ComponentFixture<PreAlertManifestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreAlertManifestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreAlertManifestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
