import { Component } from '@angular/core';
import { TransportationService } from '../services/transportation.service';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { TransportProposalListing } from '../interfaces/transport-proposal-listing';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-transportation',
  templateUrl: './transportation.component.html',
  styleUrls: ['./transportation.component.scss'],
  standalone: true,
  imports: [CommonModule,MatPaginatorModule]
})
export class TransportationComponent {
  viewMadeRequestsApi: Subscription = new Subscription();
  viewProposalsForRequestsApi  : Subscription = new Subscription();
  approveProposalApi: Subscription = new Subscription();
  rejectProposalApi: Subscription = new Subscription();

  listTransportRequests: ListTransportationalRequest[] | [] = [];
  proposalsPerRequest!:TransportProposalListing[];
  proposals:{request: ListTransportationalRequest,proposal: TransportProposalListing}[] = [];

  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private transportationService: TransportationService) {}

  ngOnInit() {
    this.transportRequests(this.pageIndex);
  }

  transportRequests(page: number) {
    this.transportationService.viewMadeRequests(page)
    .subscribe({
      next: (response) => {
        this.listTransportRequests = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;

        this.listTransportRequests.forEach((request) => {
          this.proposalsForRequests(request, request._id, page);
        })
      },
      error: (err) => {
        alert(err.error)
      }
    })
  }


  proposalsForRequests(request: ListTransportationalRequest, request_id: string, page: number) {
    this.transportationService.viewProposalsForRequests(request_id, page)
    .subscribe({
      next: (response) => {
        this.proposalsPerRequest = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
        
        this.proposalsPerRequest.forEach((proposal) => {

          if(proposal.status === ""){
            this.proposals = this.proposals?.concat({request, proposal});
            
          }

        })
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

  approveProposal(proposal_id:string) {
    Swal.showLoading();
    this.transportationService.approveProposal(proposal_id)
    .subscribe({
      next: (response) => {
        Swal.close();
        alert("APPROVED");
        window.location.reload();
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  rejectProposal(proposal_id:string) {
    Swal.showLoading();
    this.transportationService.rejectProposal(proposal_id)
    .subscribe({
      next: (response) => {
        Swal.close();
        alert("REJECTED");
        window.location.reload();
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  ngOnDestroy() {
    this.viewMadeRequestsApi.unsubscribe();
    this.viewProposalsForRequestsApi.unsubscribe();
    this.approveProposalApi.unsubscribe();
    this.rejectProposalApi.unsubscribe();
  }
}
