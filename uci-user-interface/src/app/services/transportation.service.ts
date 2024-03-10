import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pageable } from '../interfaces/pageable';
import { TransportProposalListing } from '../interfaces/transport-proposal-listing';
import { ListTransportationalRequest } from '../interfaces/list-transportational-request';

@Injectable({
  providedIn: 'root'
})
export class TransportationService {
  url: string = environment.baseurl;
  constructor(private http: HttpClient) { }

  viewAllRequests(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}transport/requests?page=${page}`);
  }

  viewRequestById(request_id: string): Observable<ListTransportationalRequest> {
    return <Observable<ListTransportationalRequest>> this.http.get(`${this.url}transport/requests-by-id?_id=${request_id}`);
  }

  viewMadeRequests(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}transport/requests-made?page=${page}`);
  }

  viewMadeProposals(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}transport/proposals-made?page=${page}`);
  }

  viewProposalsForRequests(_id: string, page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}transport/proposal-request/${_id}?page=${page}`);
  }

  makeTransportProposal(request_id: string):Observable<HttpResponse<TransportProposalListing>> {
    return <Observable<HttpResponse<TransportProposalListing>>> this.http.post(`${this.url}transport/make-proposal?_id=${request_id}`, {}, {observe:'response'});
  }

  approveProposal(proposal_id: string):Observable<HttpResponse<TransportProposalListing>> {
    return <Observable<HttpResponse<TransportProposalListing>>> this.http.post(`${this.url}transport/approve-proposal?_id=${proposal_id}`, {}, {observe:'response'});
  }

  rejectProposal(proposal_id: string):Observable<HttpResponse<TransportProposalListing>> {
    return <Observable<HttpResponse<TransportProposalListing>>> this.http.post(`${this.url}transport/reject-proposal?_id=${proposal_id}`, {}, {observe:'response'});
  }
  
  viewAllProposals(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}transport/proposals?page=${page}`);
  }
 
}
