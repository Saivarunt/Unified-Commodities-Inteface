import { Component } from '@angular/core';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { TransportationService } from '../services/transportation.service';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-transport-requests',
  templateUrl: './transport-requests.component.html',
  styleUrls: ['./transport-requests.component.scss']
})
export class TransportRequestsComponent {
  viewAllRequestsApi: Subscription = new Subscription();
  listTransportRequests: ListTransportationalRequest[] | [] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private transportationService: TransportationService) {}

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
        alert(err.error)
      }
    })
  }
  
  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.transportRequests(this.pageIndex);
  }

  ngOnDestroy() {
    this.viewAllRequestsApi.unsubscribe();
  }
}
