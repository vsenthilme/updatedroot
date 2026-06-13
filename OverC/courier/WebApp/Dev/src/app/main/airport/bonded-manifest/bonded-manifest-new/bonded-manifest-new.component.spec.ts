import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BondedManifestNewComponent } from './bonded-manifest-new.component';

describe('BondedManifestNewComponent', () => {
  let component: BondedManifestNewComponent;
  let fixture: ComponentFixture<BondedManifestNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BondedManifestNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BondedManifestNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
