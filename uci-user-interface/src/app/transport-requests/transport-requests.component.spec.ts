import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportRequestsComponent } from './transport-requests.component';

describe('TransportRequestsComponent', () => {
  let component: TransportRequestsComponent;
  let fixture: ComponentFixture<TransportRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransportRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransportRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
