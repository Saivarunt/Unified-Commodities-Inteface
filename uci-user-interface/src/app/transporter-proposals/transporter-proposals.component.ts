import { Component } from '@angular/core';
import { TransportationService } from '../services/transportation.service';
import { PageEvent } from '@angular/material/paginator';
import { Proposals } from '../interfaces/proposals';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-transporter-proposals',
  templateUrl: './transporter-proposals.component.html',
  styleUrls: ['./transporter-proposals.component.scss']
})
export class TransporterProposalsComponent {
  proposals: Proposals[] | [] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;
  displayedColumns: string [] = ["Product Name", "Owner", "Organization", "Description", "Buyer", "Buyer Organization", "Quantity", "Transporter", "Transporter Organization", "Status",]
  viewAllProposalsApi: Subscription = new Subscription();

  constructor(private transportationService: TransportationService) {}

  ngOnInit() {
    this.transporterProposals(this.pageIndex)
  }

  transporterProposals(page: number) {
    this.viewAllProposalsApi = this.transportationService.viewAllProposals(page)
    .subscribe({
      next: (response) => {
        this.proposals = response.content;
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
    this.transporterProposals(this.pageIndex);
  }

  ngOnDestroy() {
    this.viewAllProposalsApi.unsubscribe();
  }
}
