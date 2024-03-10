import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginApi: Subscription = new Subscription();
  constructor (private authService: AuthService, private router: Router, private fb: FormBuilder) {}

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
          });


          this.authService.isLoggedIn = true;
        },
        error: (err) => {
          alert(err.message);
        },
        complete: () => {
          this.router.navigate(['profile']);
        }
      })
    }
    else {
      alert("Invalid credentials!");
    }
  }

  ngOnDestroy() {
    this.loginApi.unsubscribe();
  }
}
