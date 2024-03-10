import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryProposalsComponent } from './delivery-proposals.component';

describe('DeliveryProposalsComponent', () => {
  let component: DeliveryProposalsComponent;
  let fixture: ComponentFixture<DeliveryProposalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ DeliveryProposalsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeliveryProposalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
