import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProfileResponse } from '../interfaces/profile-response';
import { ProfileUpdate } from '../interfaces/profile-update';
import { Pageable } from '../interfaces/pageable';
import { UserInfo } from '../interfaces/user-info';
import { TransactionDetailsResponse } from '../interfaces/transaction-details-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public url:string = environment.baseurl;
  constructor(private http: HttpClient) { }

  createOrder(amount: number): Observable<TransactionDetailsResponse> {
    return <Observable<TransactionDetailsResponse>> this.http.get(`${environment.baseurl}payment/create-transaction/${amount}`);
  }

  getProfileInfo(): Observable<ProfileResponse>{
    return <Observable<ProfileResponse>> this.http.get(`${this.url}user/profile/${sessionStorage.getItem("username")}`);
  }

  updateProfile(formData: FormData): Observable<HttpResponse<ProfileResponse>> {
    return <Observable<HttpResponse<ProfileResponse>>> this.http.post(`${this.url}user/profile`, formData, {observe:'response'});
  }

  getByUsername(): Observable<UserInfo> {
    return <Observable<UserInfo>> this.http.get(`${this.url}user/${sessionStorage.getItem("username")}`);
  }

  searchByUsername(username: string, page: number): Observable<Pageable> {
    return <Observable<Pageable>> this.http.get(`${this.url}user/search?username=${username}&page=${page}`);
  }
  
  viewAllUsers(page: number): Observable<Pageable>{
    return <Observable<Pageable>> this.http.get(`${this.url}user/?page=${page}`);
  }
}
