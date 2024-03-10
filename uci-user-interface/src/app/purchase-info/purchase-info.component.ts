import { Component } from '@angular/core';
import { PurchaseService } from '../services/purchase.service';
import { Purchases } from '../interfaces/purchases';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-purchase-info',
  templateUrl: './purchase-info.component.html',
  styleUrls: ['./purchase-info.component.scss']
})
export class PurchaseInfoComponent {
  viewAllPurchasesApi: Subscription  = new Subscription();
  purchases: Purchases[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;

  constructor(private purchaseService: PurchaseService) {}

  ngOnInit() {
    this.viewPurchases(this.pageIndex);
  }

  viewPurchases(page: number) {
    this.viewAllPurchasesApi = this.purchaseService.viewAllPurchases(page)
    .subscribe({
      next: (response) => {
        this.purchases  = response.content;
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
    this.viewPurchases(this.pageIndex);
  }

  ngOnDestroy() {
    this.viewAllPurchasesApi.unsubscribe();
  }
}
