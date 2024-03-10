import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransporterHomeComponent } from './transporter-home.component';

describe('TransporterHomeComponent', () => {
  let component: TransporterHomeComponent;
  let fixture: ComponentFixture<TransporterHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ TransporterHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransporterHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
