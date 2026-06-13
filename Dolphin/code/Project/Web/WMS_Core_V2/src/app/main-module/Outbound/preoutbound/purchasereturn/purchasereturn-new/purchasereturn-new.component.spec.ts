import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasereturnNewComponent } from './purchasereturn-new.component';

describe('PurchasereturnNewComponent', () => {
  let component: PurchasereturnNewComponent;
  let fixture: ComponentFixture<PurchasereturnNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasereturnNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchasereturnNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
