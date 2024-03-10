import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProductListing } from '../interfaces/product-listing';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pageable } from '../interfaces/pageable';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  url: string = environment.baseurl;

  constructor(private http: HttpClient) { }

  listProduct(product: FormData): Observable<HttpResponse<ProductListing>>{  
    return  <Observable<HttpResponse<ProductListing>>> this.http.post(`${this.url}products/product-listing`, product, {observe: 'response'});
  }

  updateProductListing(product: FormData): Observable<HttpResponse<ProductListing>>{  
    return  <Observable<HttpResponse<ProductListing>>> this.http.put(`${this.url}products/update-product-listing`, product, {observe: 'response'});
  }

  viewProducts(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}products/owned?page=${page}`);
  } 

  viewAllProducts(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}products/?page=${page}`);
  } 

  deleteProductListing(product_id: string): Observable<Boolean> {
    return <Observable<Boolean>> this.http.delete(`${this.url}products/delete?product_id=${product_id}`);
  }

  viewLifecycle(page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}products/lifecycle?page=${page}`);
  } 

  updateSupplierStatus(_id: string): Observable<HttpResponse<Boolean>>{
    return <Observable<HttpResponse<Boolean>>> this.http.post(`${this.url}products/supplier-status?_id=${_id}`, {}, {observe: 'response'});
  }

  updateConsumerStatus(_id: string): Observable<HttpResponse<Boolean>>{
    return <Observable<HttpResponse<Boolean>>> this.http.post(`${this.url}products/consumer-status?_id=${_id}`, {}, {observe: 'response'});
  }

  searchValue(value: string, page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}products/search?productname=${value}&page=${page}`);
  } 

  rateTransporter(rating: number, _id: string): Observable<HttpResponse<Boolean>>{
    return <Observable<HttpResponse<Boolean>>> this.http.post(`${this.url}products/rate-transporter?rating=${rating}&_id=${_id}`, {}, {observe: 'response'});
  }

  rateProduct(rating: number, _id: string): Observable<HttpResponse<Boolean>>{
    return <Observable<HttpResponse<Boolean>>> this.http.post(`${this.url}products/rate-product?rating=${rating}&_id=${_id}`, {}, {observe: 'response'});
  }
}
