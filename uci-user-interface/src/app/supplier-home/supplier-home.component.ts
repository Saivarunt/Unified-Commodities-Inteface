import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../services/product.service';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { ListOrUpdateProductComponent } from '../list-or-update-product/list-or-update-product.component';
import { ProductListing } from '../interfaces/product-listing';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { Subscription } from 'rxjs';
import { PermissionDirective } from '../directives/permission.directive';


@Component({
  selector: 'app-supplier-home',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatPaginatorModule, RouterModule, MatButtonModule, PermissionDirective],
  templateUrl: './supplier-home.component.html',
  styleUrls: ['./supplier-home.component.scss']
})
export class SupplierHomeComponent {
  viewProductsApi: Subscription = new Subscription();
  products: ProductListing[] = [];
  interest: ProductListing | {} = {};
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private productService: ProductService, public dialog: MatDialog) {}

  ngOnInit() {
    this.productsOwned(this.pageIndex);
  }

  openListingDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(ListOrUpdateProductComponent, {
      width: '50vh',
      height: '50vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: null
    });
  }

  openUpdateDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(ListOrUpdateProductComponent, {
      width: '50vh',
      height: '50vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {product: this.interest, type: 'update'}
    });
  }

  openDeleteDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(ListOrUpdateProductComponent, {
      width: '30vh',
      height: '25vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {product: this.interest, type: 'delete'}
    });
  }

  productsOwned(page: number) {
    this.viewProductsApi = this.productService.viewProducts(page)
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
    this.productsOwned(this.pageIndex);
  }

  makeNewListing () {
    this.openListingDialog('30ms', '30ms');
  }
  
  updateListing(update_product_id: string) {   
    this.interest = this.products.filter((product) => {
      return product._id === update_product_id;
    })[0];
    this.openUpdateDialog('30ms', '30ms');
  }

  deleteListing(delete_product_id: string) {   
    this.interest = this.products.filter((product) => {
      return product._id === delete_product_id;
    })[0];
    this.openDeleteDialog('30ms', '30ms');
  }

  ngOnDestroy() {
    this.viewProductsApi.unsubscribe();
  }
}
