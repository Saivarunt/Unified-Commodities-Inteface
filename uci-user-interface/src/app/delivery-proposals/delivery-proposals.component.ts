import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransportationService } from '../services/transportation.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { TransportProposalListing } from '../interfaces/transport-proposal-listing';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { MatButtonModule } from '@angular/material/button';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-delivery-proposals',
  standalone: true,
  imports: [CommonModule, MatPaginatorModule, MatButtonModule],
  templateUrl: './delivery-proposals.component.html',
  styleUrls: ['./delivery-proposals.component.scss']
})
export class DeliveryProposalsComponent {
  fetchRequestDetailsApi: Subscription  = new Subscription();
  fetchProposalsMadeApi: Subscription = new Subscription();
  madeProposals: TransportProposalListing[] | [] = [] ;
  requestInfo!: ListTransportationalRequest;
  proposalsByRequest: {proposal: TransportProposalListing, request: ListTransportationalRequest}[] = [];

  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private transportationService: TransportationService) {}

  ngOnInit() {
    this.fetchProposalsMade(this.pageIndex);
  }

  fetchRequestDetails(proposal: TransportProposalListing, request_id: string) {
    this.fetchRequestDetailsApi = this.transportationService.viewRequestById(request_id)
          .subscribe({
            next: (response) => {
              this.requestInfo = response;
              this.proposalsByRequest = this.proposalsByRequest?.concat(({proposal, request:this.requestInfo}))
            },
            error: (err) => {
              alert(err.error);
            }
          })
  }
  
  fetchProposalsMade(page: number) {
    this.fetchProposalsMadeApi = this.transportationService.viewMadeProposals(page)
    .subscribe({
      next: (response) => {
        this.madeProposals = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
        
        this.madeProposals.forEach((val) => {
          this.fetchRequestDetails(val, val.request_id);
        })

      },
      error: (err) => {
        alert(err.error);
      }
    })
  }
  
  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.fetchProposalsMade(this.pageIndex);
  }

  ngOnDestroy() {
    this.fetchProposalsMadeApi.unsubscribe();
    this.fetchRequestDetailsApi.unsubscribe();
  }
}
