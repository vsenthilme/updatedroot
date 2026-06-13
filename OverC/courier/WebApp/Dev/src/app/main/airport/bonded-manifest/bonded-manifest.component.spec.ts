import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BondedManifestComponent } from './bonded-manifest.component';

describe('BondedManifestComponent', () => {
  let component: BondedManifestComponent;
  let fixture: ComponentFixture<BondedManifestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BondedManifestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BondedManifestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
