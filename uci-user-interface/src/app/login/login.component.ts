import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginApi: Subscription = new Subscription();
  permissions: string[] = [];
  constructor (private authService: AuthService, private router: Router, private fb: FormBuilder, private al: AlertService) {}

  formResponse = this.fb.group({
    username: ["", [Validators.required, Validators.minLength(1)]],
    password: ["", [Validators.required, Validators.minLength(1)]]
  });

  loginResponseFunction() {
    if(!this.formResponse.invalid) {
    this.loginApi = this.authService.loginUser(this.formResponse.get("username")?.value, this.formResponse.get("password")?.value)
      .subscribe({
        next: (response) => {
          sessionStorage.setItem("logged_in", "true")
          sessionStorage.setItem("username", response.body!.user.username);
          sessionStorage.setItem("jwt", response.body!.jwt);

          response.body!.user.authorities.forEach((role) => {
            sessionStorage.setItem(role.authority, "true");
          });

          response.body!.user.permissions.forEach((permission) => {
            sessionStorage.setItem(permission.permission, "true");
            this.permissions.push(permission.permission);
          });
          
          this.authService.changeValues(this.permissions);

          this.authService.isLoggedIn = true;
          this.al.alertPrompt("Logged In", `Welcome ${response.body!.user.username}`, "success")
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");
        },
        complete: () => {
          this.router.navigate(['profile']);
        }
      })
    }
    else {
      this.al.alertPrompt("Error","Invalid credentials!", "error");
    }
  }

  ngOnDestroy() {
    this.loginApi.unsubscribe();
  }
}
