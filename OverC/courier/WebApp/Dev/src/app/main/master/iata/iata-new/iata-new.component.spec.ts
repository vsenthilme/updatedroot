import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IataNewComponent } from './iata-new.component';

describe('IataNewComponent', () => {
  let component: IataNewComponent;
  let fixture: ComponentFixture<IataNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [IataNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(IataNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
