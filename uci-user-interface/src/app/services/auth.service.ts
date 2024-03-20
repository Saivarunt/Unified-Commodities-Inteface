import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../interfaces/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public url: string = environment.baseurl;
  isLoggedIn: boolean = sessionStorage.getItem("logged_in") === "true";


  public permissionsShared = new BehaviorSubject<string[]>([]);
  observeValues = this.permissionsShared.asObservable();

  changeValues(data: string[]) {
    this.permissionsShared.next(data);
  }

  constructor(private http: HttpClient) { }

  getOtp(mail: string | null | undefined): Observable<HttpResponse<{ response: string }>> {
    return <Observable<HttpResponse<{ response: string }>>>this.http.post(`${this.url}auth/generate-otp/${mail}`, {}, { observe: 'response' })
  }

  validateOtp(mail: string | null | undefined, otp: string | null | undefined): Observable<HttpResponse<boolean>> {
    return <Observable<HttpResponse<boolean>>>this.http.post(`${this.url}auth/verify-otp/?mail_id=${mail}&otp=${otp}`, {}, { observe: 'response' });
  }

  loginUser(username: string | null | undefined, password: string | null | undefined): Observable<HttpResponse<LoginResponse>> {
    return <Observable<HttpResponse<LoginResponse>>>this.http.post(`${this.url}auth/login`, { username, password }, { observe: 'response' });
  }

  isAuthenticated() {
    return this.isLoggedIn;
  }

  hasRequiredRole(requiredRoles: string[]): boolean {
    return requiredRoles.filter((role) => sessionStorage.getItem(role)).length > 0;
  }

  hasRequirePermission(requiredPermissions: string[]): boolean {
    return requiredPermissions.filter((permission) => sessionStorage.getItem(permission)).length > 0;
  }
}
