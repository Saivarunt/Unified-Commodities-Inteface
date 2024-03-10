import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { RegistrationResponse } from '../interfaces/registration-response';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private url:string = environment.baseurl;

  constructor(private http:HttpClient) { }

  registerSupplier(username: string | null | undefined, email: string | null | undefined, password: string | null | undefined): Observable<HttpResponse<RegistrationResponse>>{
    
    return <Observable<HttpResponse<RegistrationResponse>>> this.http.post(`${this.url}auth/supplier-registration`, {username,email,password}, {observe:'response'});
  }

  registerConsumer(username: string | null | undefined, email: string | null | undefined, password: string | null | undefined): Observable<HttpResponse<RegistrationResponse>>{
    
    return <Observable<HttpResponse<RegistrationResponse>>> this.http.post(`${this.url}auth/consumer-registration`, {username,email,password}, {observe:'response'});
  }

  registerTransporter(username: string | null | undefined, email: string | null | undefined, password: string | null | undefined): Observable<HttpResponse<RegistrationResponse>>{
    
    return <Observable<HttpResponse<RegistrationResponse>>> this.http.post(`${this.url}auth/transporter-registration`, {username,email,password}, {observe:'response'});
  }
}
