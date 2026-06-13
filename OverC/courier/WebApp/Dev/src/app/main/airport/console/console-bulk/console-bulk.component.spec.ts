import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleBulkComponent } from './console-bulk.component';

describe('ConsoleBulkComponent', () => {
  let component: ConsoleBulkComponent;
  let fixture: ComponentFixture<ConsoleBulkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleBulkComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleBulkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
