import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegistrationService } from '../services/registration.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {
  registrationType: string = "";
  otpValid: boolean = false;
  enableOtp: boolean = false;
  getOtpApi: Subscription = new Subscription();
  validateOtpApi: Subscription = new Subscription();
  registerSupplierApi: Subscription = new Subscription();
  registerConsumerApi: Subscription = new Subscription();
  registerTransporterApi: Subscription = new Subscription();

  constructor (private authService: AuthService, private router:Router, private registrationService: RegistrationService, private fb: FormBuilder) {}

  formResponse = this.fb.group({
    username: ["", [Validators.required, Validators.minLength(1)]],
    mail: ["", [Validators.required, Validators.email]],
    otp: ["", [Validators.required, Validators.min(100000), Validators.max(999999)]],
    password: ["", [Validators.required, Validators.minLength(1)]],
    confirmpassword: ["", [Validators.required, Validators.minLength(1)]]
  })

  registrationTypeSet (type: string) {
    this.registrationType = type;
  }

  generateOtp() {
    Swal.showLoading();
    if(this.formResponse.get("mail")?.value === null || this.formResponse.get("mail")?.value === undefined || this.formResponse.get("mail")?.value === "") {
      Swal.close();
      alert("Enter email to get otp");
      return;
    }
    else{
      this.enableOtp = true;
      this.getOtpApi = this.authService.getOtp(this.formResponse.get("mail")?.value)
      .subscribe({
        next: (response) =>{
          Swal.close();         
          alert("Otp generated; Kindly check your email.");
        },
        error: (err) => {  
          console.log(err);
          Swal.close();
          alert(err.error);
        }
      })
    }
  }

  verifyOtp() {
    if(this.formResponse.get("otp")?.value === null || this.formResponse.get("otp")?.value === undefined || this.formResponse.get("otp")?.value === "") {
      alert("Enter otp to validate");
      return;
    }
    else{
      this.enableOtp = true;
      this.validateOtpApi = this.authService.validateOtp(this.formResponse.get("mail")?.value,this.formResponse.get("otp")?.value)
      .subscribe({
        next: (response) =>{
          this.otpValid = response.body!.valueOf();
          if(response.body!.valueOf() === false){
            alert("Invalid OTP");
          }
          else {
            this.formResponse.get('mail')?.disable({onlySelf:true});
          }
        },
        error: (err) => {
          alert(err);
        }
      })
    }
  }

  regitserResponseFunction () {
    
    if(this.formResponse.get("password")?.value === this.formResponse.get("confirmpassword")?.value){
      if(this.registrationType === "Supplier" && !this.formResponse.invalid){
        
        this.registerSupplierApi = this.registrationService.registerSupplier(this.formResponse.get('username')?.value, this.formResponse.get('mail')?.value, this.formResponse.get('password')?.value)
  
        .subscribe({
          next: (response) => {
            if(response.status !== 200) {
              throw new HttpErrorResponse({headers: response.headers, status: response.status, url: response.url?.toString()});
            }
  
          },
  
          error: (error) => {
            alert("Duplicate Credentials or Invalid input");
          },
  
          complete: () => {
            this.router.navigate(['login']);
          }
  
        })
  
      }
      else if(this.registrationType === "Consumer" && !this.formResponse.invalid) {
  
        this.registerConsumerApi = this.registrationService.registerConsumer(this.formResponse.get('username')?.value, this.formResponse.get('mail')?.value, this.formResponse.get('password')?.value)
  
        .subscribe({
  
          next: (response) => {
            if(response.status !== 200) {
              throw new HttpErrorResponse({headers: response.headers, status: response.status, url: response.url?.toString()});
            }
  
          },
  
          error: (error) => {
            alert("Duplicate Credentials or Invalid input");
          },
  
          complete: () => {
            this.router.navigate(['login']);
          }
  
        })
  
      }
      else if(this.registrationType === "Transporter" && !this.formResponse.invalid){
  
        this.registerTransporterApi = this.registrationService.registerTransporter(this.formResponse.get('username')?.value, this.formResponse.get('mail')?.value, this.formResponse.get('password')?.value)
  
        .subscribe({
  
          next: (response) => {
            if(response.status !== 200) {
              throw new HttpErrorResponse({headers: response.headers, status: response.status, url: response.url?.toString()});
            }
  
          },
  
          error: (error) => {
            alert("Duplicate Credentials or Invalid input");
          },
  
          complete: () => {
            this.router.navigate(['login']);
          }
  
        })
  
      }
      else{
        alert("Pick a registration type and check inputs provided!")
      }
    }
    else{
      alert("Passwords don't match")
    }
  }

  ngOnDestroy() {
    this.getOtpApi.unsubscribe()
    this.validateOtpApi.unsubscribe()
    this.registerSupplierApi.unsubscribe()
    this.registerConsumerApi.unsubscribe()
    this.registerTransporterApi.unsubscribe()
  }
}
