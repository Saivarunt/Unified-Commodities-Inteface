import { Component } from '@angular/core';
import { TransportationService } from '../services/transportation.service';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { TransportProposalListing } from '../interfaces/transport-proposal-listing';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';
import { AlertService } from '../services/alert.service';

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
  interval = setTimeout(() => {}, 0)

  listTransportRequests: ListTransportationalRequest[] | [] = [];
  proposalsPerRequest!:TransportProposalListing[];
  proposals:{request: ListTransportationalRequest,proposal: TransportProposalListing}[] = [];

  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(private transportationService: TransportationService, private al: AlertService) {}

  ngOnInit() {
    this.transportRequests(this.pageIndex);
  }

  transportRequests(page: number) {
    this.viewMadeRequestsApi = this.transportationService.viewMadeRequests(page)
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
        this.al.alertPrompt("Error", err.error, "error");
      }
    })
  }


  proposalsForRequests(request: ListTransportationalRequest, request_id: string, page: number) {
    this.viewProposalsForRequestsApi = this.transportationService.viewProposalsForRequests(request_id, page)
    .subscribe({
      next: (response) => {
        this.proposalsPerRequest = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
        
        this.proposalsPerRequest.forEach((proposal) => {

          if(proposal.status === "PENDING"){
            this.proposals = this.proposals?.concat({request, proposal});
          }

        })

        this.totalElements = this.proposals.length;
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

  approveProposal(proposal_id:string) {
    Swal.showLoading();
    this.approveProposalApi = this.transportationService.approveProposal(proposal_id)
    .subscribe({
      next: (response) => {
        Swal.close();
        this.al.alertPrompt("APPROVED", "Proposal was approved!", "success");
        clearTimeout(this.interval);
        this.interval = setTimeout(() => window.location.reload(), 1800);
      },
      error: (err) => {
        this.al.alertPrompt("Error", err.error, "error");;
      }
    })
  }

  rejectProposal(proposal_id:string) {
    Swal.showLoading();
    this.rejectProposalApi = this.transportationService.rejectProposal(proposal_id)
    .subscribe({
      next: (response) => {
        Swal.close();
        this.al.alertPrompt("REJECTED", "Proposal was rejected as requested!", "success");
        clearTimeout(this.interval);
        this.interval = setTimeout(() => window.location.reload(), 1800);
      },
      error: (err) => {
        this.al.alertPrompt("Error", err.error, "error");;
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
