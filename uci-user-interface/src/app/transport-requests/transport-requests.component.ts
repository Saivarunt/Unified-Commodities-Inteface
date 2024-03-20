import { Component } from '@angular/core';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { TransportationService } from '../services/transportation.service';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-transport-requests',
  templateUrl: './transport-requests.component.html',
  styleUrls: ['./transport-requests.component.scss']
})
export class TransportRequestsComponent {
  viewAllRequestsApi: Subscription = new Subscription();
  searchApi: Subscription = new Subscription();
  listTransportRequests: ListTransportationalRequest[] | [] = [];
  // requests: = 
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;
  search: string = "";

  constructor(private transportationService: TransportationService, private al: AlertService) {}

  ngOnInit() {
    this.transportRequests(this.pageIndex)
  }

  transportRequests(page: number) {
    this.viewAllRequestsApi = this.transportationService.viewAllRequests(page)
    .subscribe({
      next: (response) => {
        this.listTransportRequests = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
      },
      error: (err) => {
       this.al.alertPrompt("Error", err.error, "error");
      }
    })
  }
  
  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.transportRequests(this.pageIndex);
  }

  trigger = this.debounce(() => {     
    this.searchApi = this.transportationService.searchRequester(this.search, this.pageIndex > 0 ? 0 : this.pageIndex)
      .subscribe({
        next: (response) => {
          if(response !== null) {
            this.listTransportRequests  = response.content;
            this.pageSize = response.size;
            this.totalElements = response.totalElements;
          }
          else{
            this.listTransportRequests = [];
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
    this.viewAllRequestsApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
