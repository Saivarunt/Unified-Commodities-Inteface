import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransporterProposalsComponent } from './transporter-proposals.component';

describe('TransporterProposalsComponent', () => {
  let component: TransporterProposalsComponent;
  let fixture: ComponentFixture<TransporterProposalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransporterProposalsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransporterProposalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
