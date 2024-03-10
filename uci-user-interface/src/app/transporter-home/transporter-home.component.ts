import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { TransportationService } from '../services/transportation.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { Subscription } from 'rxjs';
import { PermissionDirective } from '../directives/permission.directive';

@Component({
  selector: 'app-transporter-home',
  standalone: true,
  imports: [CommonModule, RouterModule, MatPaginatorModule, PermissionDirective],
  templateUrl: './transporter-home.component.html',
  styleUrls: ['./transporter-home.component.scss']
})

export class TransporterHomeComponent {

  listTransportRequests: ListTransportationalRequest[] | [] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;
  viewAllRequestsApi: Subscription = new Subscription();
  makeTransportProposalApi: Subscription = new Subscription();

  constructor(private transportationService: TransportationService, private router: Router) {}

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
        
        this.listTransportRequests = this.listTransportRequests.filter((val) => {
          return val.status !== "INITIATED";
        });       
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

  makeProposal(request_id: string) {
    this.makeTransportProposalApi = this.transportationService.makeTransportProposal(request_id)
    .subscribe({
      next: (response) => {
        this.router.navigate(['home/delivery-proposals'])
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  ngOnDestroy() {
    this.viewAllRequestsApi.unsubscribe();
    this.makeTransportProposalApi.unsubscribe();
  }
}
