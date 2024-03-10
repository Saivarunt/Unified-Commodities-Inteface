import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOrUpdateProductComponent } from './list-or-update-product.component';

describe('ListOrUpdateProductComponent', () => {
  let component: ListOrUpdateProductComponent;
  let fixture: ComponentFixture<ListOrUpdateProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListOrUpdateProductComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListOrUpdateProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
