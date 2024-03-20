import { Component } from '@angular/core';
import { TransportationService } from '../services/transportation.service';
import { PageEvent } from '@angular/material/paginator';
import { Proposals } from '../interfaces/proposals';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

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
  searchApi: Subscription = new Subscription();
  search: string = "";

  constructor(private transportationService: TransportationService, private al: AlertService) {}

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
      this.al.alertPrompt("Error", err.error, "error");
      }
    })
  }
  
  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.transporterProposals(this.pageIndex);
  }

  trigger = this.debounce(() => {     
    this.searchApi = this.transportationService.searchTransporter(this.search, this.pageIndex > 0 ? 0 : this.pageIndex)
      .subscribe({
        next: (response) => {
          if(response !== null) {
            this.proposals  = response.content;
            this.pageSize = response.size;
            this.totalElements = response.totalElements;
          }
          else{
            this.proposals = [];
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
    this.viewAllProposalsApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
