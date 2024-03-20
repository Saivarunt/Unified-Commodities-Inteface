import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PurchaseInfo } from '../interfaces/purchase-info';
import { Observable } from 'rxjs';
import { PurchaseResponse } from '../interfaces/purchase-response';
import { Pageable } from '../interfaces/pageable';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  url: string = environment.baseurl;

  constructor(private http: HttpClient) { }

  purchaseProduct(interest: PurchaseInfo): Observable<HttpResponse<PurchaseResponse>> {
    return <Observable<HttpResponse<PurchaseResponse>>>this.http.post(`${this.url}purchase/make-purchase`, interest, { observe: 'response' });
  }

  viewAllPurchases(page: number): Observable<Pageable> {
    return <Observable<Pageable>>this.http.get(`${this.url}purchase/?page=${page}`)
  }

  searchPurchase(search: string, page: number): Observable<Pageable> {
    return <Observable<Pageable>>this.http.get(`${this.url}purchase/search?productName=${search}&page=${page}`)
  }
}
