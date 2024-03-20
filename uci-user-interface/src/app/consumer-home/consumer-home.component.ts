import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../services/product.service';
import { ProductListing } from '../interfaces/product-listing';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PurchaseListingComponent } from '../purchase-listing/purchase-listing.component';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';


@Component({
  selector: 'app-consumer-home',
  standalone: true,
  imports: [CommonModule, MatPaginatorModule, MatDialogModule, RouterModule, FormsModule],
  templateUrl: './consumer-home.component.html',
  styleUrls: ['./consumer-home.component.scss']
})
export class ConsumerHomeComponent {

  viewProductsApi: Subscription = new Subscription();
  searchApi: Subscription = new Subscription();
  products: ProductListing[] = [];
  interest: ProductListing | {} = {};
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;
  search: string = "";

  constructor(private productService: ProductService, public dialog: MatDialog, private router: Router, private al: AlertService) { }

  ngOnInit() {
    this.viewProducts(this.pageIndex);
  }

  viewProducts(page: number) {
    this.viewProductsApi = this.productService.viewAllProducts(page)
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

  openPurchaseDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(PurchaseListingComponent, {
      width: '55vh',
      height: '55vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: { product: this.interest }
    });
  }

  buyProduct(product_id: string) {
    this.interest = this.products.filter((product) => {
      return product._id === product_id;
    })[0];
    this.openPurchaseDialog('30ms', '30ms');
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
    this.viewProductsApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
