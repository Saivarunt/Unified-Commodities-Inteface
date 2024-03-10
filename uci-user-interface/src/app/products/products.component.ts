import { Component } from '@angular/core';
import { ProductService } from '../services/product.service';
import { ProductListing } from '../interfaces/product-listing';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent {
  viewAllProductsApi: Subscription = new Subscription();
  products: ProductListing[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 30;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.viewProducts(this.pageIndex);
  }

  viewProducts(page: number) {
    this.viewAllProductsApi = this.productService.viewAllProducts(page)
    .subscribe({
      next: (response) => {
        this.products  = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.viewProducts(this.pageIndex);
  }

  ngOnDestroy() {
    this.viewAllProductsApi.unsubscribe();
  }
}
