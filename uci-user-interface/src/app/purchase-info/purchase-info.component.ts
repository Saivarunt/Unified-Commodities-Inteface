import { Component } from '@angular/core';
import { PurchaseService } from '../services/purchase.service';
import { Purchases } from '../interfaces/purchases';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-purchase-info',
  templateUrl: './purchase-info.component.html',
  styleUrls: ['./purchase-info.component.scss']
})
export class PurchaseInfoComponent {
  viewAllPurchasesApi: Subscription  = new Subscription();
  searchApi: Subscription  = new Subscription();
  purchases: Purchases[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;
  search: string = "";

  constructor(private purchaseService: PurchaseService, private al: AlertService) {}

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
        this.al.alertPrompt("Error", err.error, "error");;
      }
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.viewPurchases(this.pageIndex);
  }

  trigger = this.debounce(() => {     
    this.searchApi = this.purchaseService.searchPurchase(this.search, this.pageIndex > 0 ? 0 : this.pageIndex)
      .subscribe({
        next: (response) => {
          if(response !== null) {
            this.purchases  = response.content;
            this.pageSize = response.size;
            this.totalElements = response.totalElements;
          }
          else{
            this.purchases = [];
          }
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      });
  }, 1000)


  debounce(cb: Function, delay: number) {
    let interval = setTimeout(() => {}, 0);
    return (...args: any) =>{
        clearTimeout(interval);
        interval = setTimeout(() => {
            cb(...args)
        }, delay)
    }
  }

  searchProduct() {
    if(this.search[this.search.length - 1] !== " "){
      this.trigger();
    }
  }
  ngOnDestroy() {
    this.viewAllPurchasesApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
