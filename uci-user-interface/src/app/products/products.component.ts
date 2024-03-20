import { Component } from '@angular/core';
import { ProductService } from '../services/product.service';
import { ProductListing } from '../interfaces/product-listing';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent {
  viewAllProductsApi: Subscription = new Subscription();
  searchApi: Subscription = new Subscription();
  products: ProductListing[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;
  search: string = "";

  constructor(private productService: ProductService, private al: AlertService) { }

  ngOnInit() {
    this.viewProducts(this.pageIndex);
  }

  viewProducts(page: number) {
    this.viewAllProductsApi = this.productService.viewAllProducts(page)
      .subscribe({
        next: (response) => {
          this.products = response.content;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.viewProducts(this.pageIndex);
  }

  trigger = this.debounce(() => {
    this.searchApi = this.productService.searchProduct(this.search, this.pageIndex > 0 ? 0 : this.pageIndex)
      .subscribe({
        next: (response) => {
          this.products = response.content;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      });
  }, 1000)


  debounce(cb: Function, delay: number) {
    let interval = setTimeout(() => { }, 0);
    return (...args: any) => {
      clearTimeout(interval);
      interval = setTimeout(() => {
        cb(...args)
      }, delay)
    }
  }

  searchProduct() {
    if (this.search[this.search.length - 1] !== " ") {
      this.trigger();
    }
  }

  ngOnDestroy() {
    this.viewAllProductsApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
