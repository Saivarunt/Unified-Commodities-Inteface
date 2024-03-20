import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, SetDisabledStateOption, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { ProfileResponse } from '../interfaces/profile-response';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { PaymentInformation } from '../interfaces/payment-information';
import { HomeModule } from '../home/home.module';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';
import { AlertService } from '../services/alert.service';
import { RazorpayResponse } from '../interfaces/razorpay-response';
import { TransactionDetailsResponse } from '../interfaces/transaction-details-response';
declare const Razorpay: any;



@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, FormsModule, HomeModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  getProfileApi: Subscription = new Subscription();
  createOrderApi: Subscription = new Subscription();
  updateProfileApi: Subscription = new Subscription();
  permissionsApi: Subscription = new Subscription();

  permissions: string[] = [];

  // verify subscription
  profile: ProfileResponse | null = null;
  username: string = sessionStorage.getItem("username") || "";
  supplier: boolean = sessionStorage.getItem("SUPPLIER") === "true";
  consumer: boolean = sessionStorage.getItem("CONSUMER") === "true";
  transporter: boolean = sessionStorage.getItem("TRANSPORTER") === "true";
  subscription_type: string | null = null;
  profile_image: File | string = "";
  success: boolean = false;
  payment_info: PaymentInformation = {
    razorpay_payment_id: "",
    razorpay_order_id: "",
    razorpay_signature: "",
    amount: 0
  };

  formResponse: FormGroup = this.fb.group({
    "full_name": [this.profile?.full_name || "", [Validators.required, Validators.minLength(2)]],
    "organization": [this.profile?.organization || "", [Validators.required, Validators.minLength(2)]],
    "email": new FormControl({ value: this.profile?.email || "", disabled: true }),
    "phone_number": [this.profile?.phone_number || "", [Validators.required, Validators.min(1), Validators.minLength(10), Validators.maxLength(10)]],
    "address": this.fb.group({
      "city": [this.profile?.address?.city || "", [Validators.required, Validators.minLength(2)]],
      "state": [this.profile?.address?.state || "", [Validators.required, Validators.minLength(2)]],
      "country": [this.profile?.address?.country || "", [Validators.required, Validators.minLength(2)]],
      "primary_address": [this.profile?.address?.primary_address || "", [Validators.required, Validators.minLength(2)]],
      "postal_code": [this.profile?.address?.postal_code || "", [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    }),
    "subscription_type": [this.subscription_type],
    "period": [this.profile?.subscription?.period || null],
  })

  constructor(private fb: FormBuilder, private userService: UserService, private authService: AuthService, private router: Router, private al: AlertService) {
    this.getProfileApi = this.userService.getProfileInfo()
      .subscribe({
        next: (response) => {
          this.profile = <ProfileResponse>response;
          this.formResponse.patchValue({
            full_name: response.full_name,
            organization: response.organization,
            email: response.email,
            phone_number: response.phone_number,
            address: {
              city: response.address?.city,
              state: response.address?.state,
              country: response.address?.country,
              primary_address: response.address?.primary_address,
              postal_code: response.address?.postal_code,

            },
            profile_image: response.profile_image,
            subscription_type: response.subscription?.subscription_type,
            period: response.subscription?.period
          })
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.message, "error");
        }
      });
  }


  openTransactionModel(response: TransactionDetailsResponse) {
    const options = {
      order_id: response.orderId,
      key: response.key,
      amount: response.amount,
      currency: response.currency,
      name: 'UCI',
      description: 'Payment of Subscription',
      image: 'https://img.freepik.com/free-vector/mobile-bank-users-transferring-money-currency-conversion-tiny-people-online-payment-cartoon-illustration_74855-14454.jpg',
      prefill: {
        name: sessionStorage.getItem("username"),
        email: this.profile?.email,
        contact: this.profile?.phone_number || ""
      },
      handler: (response: RazorpayResponse) => {
        if (response != null && response.razorpay_payment_id != null)
          this.processResponse(response);
        else
          alert('Payment failed');
      },
      notes: {
        address: 'Subscription'
      },
      theme: {
        color: '#F37254'
      },
      modal: {
        ondismiss: () => {
          console.log('Payment dismissed');
        }
      }
    };

    let razorpayObject: any = new Razorpay(options);
    razorpayObject.open();

  }

  processResponse(response: RazorpayResponse) {
    this.success = true;
    this.payment_info.razorpay_payment_id = response.razorpay_payment_id;
    this.payment_info.razorpay_order_id = response.razorpay_order_id;
    this.payment_info.razorpay_signature = response.razorpay_signature;
    this.formResponse.get('period')?.disable({ onlySelf: true });
    this.al.alertPrompt("Payment Successful", "Subscription payment successful and your plan has been activated", "success");
  }

  pay(amount: number) {
    this.payment_info.amount = amount;
    Swal.close();
    this.createOrderApi = this.userService.createOrder(amount)
      .subscribe({
        next: (response) => {
          this.openTransactionModel(response);
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");
        }
      })
  }

  payment($event: Event, type: string) {
    Swal.showLoading();
    const selectElement = $event.target as HTMLSelectElement
    if (type === "SUPPLIER") {
      if (selectElement.value === "Monthly") {
        this.pay(300);
      }
      else if (selectElement.value === "Yearly") {
        this.pay(3000);
      }
    }

    else if (type === "TRANSPORTER") {
      if (selectElement.value === "Monthly") {
        this.pay(160);
      }
      else if (selectElement.value === "Yearly") {
        this.pay(1800);
      }
    }
  }

  logoutUser() {
    sessionStorage.clear();
    this.authService.isLoggedIn = false;
    this.al.alertPrompt("Logged out", "Successfully logged out", "success");
  }

  addFile($event: Event) {
    const file = $event.target as HTMLInputElement;
    if (file.files) {
      this.profile_image = file.files[0];
    }
  }

  updatePermissions() {
    this.permissionsApi = this.userService.getByUsername()
      .subscribe({
        next: (response) => {

          response.permissions.forEach((permission) => {
            sessionStorage.setItem(permission.permission, "true");
            this.permissions.push(permission.permission);
          });
          console.log(this.permissions);

          this.authService.changeValues(this.permissions);
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");
        },
        complete: () => {
          if (this.supplier) {
            this.router.navigate(['home/supplier-home']);
          }
          else if (this.consumer) {
            this.router.navigate(['home/consumer-home']);
          }
          else if (this.transporter) {
            this.router.navigate(['home/transporter-home']);
          }
          else {
            this.router.navigate(['home']);
          }
        }
      });
  }

  updateProfile() {

    if (this.supplier && this.profile?.subscription?.subscription_type === undefined) {
      this.subscription_type = "SUPPLIER";
      this.formResponse.patchValue({ subscription_type: this.subscription_type });
    }
    else if (this.transporter && this.profile?.subscription?.subscription_type === undefined) {
      this.subscription_type = "TRANSPORTER";
      this.formResponse.patchValue({ subscription_type: this.subscription_type });
    }


    if (!this.formResponse.invalid) {
      const formData: FormData = new FormData();
      formData.append('profile_image', this.profile_image === "" ? new Blob([]) : this.profile_image);
      formData.append('full_name', this.formResponse.get("full_name")!.value);
      formData.append('organization', this.formResponse.get("organization")!.value);
      formData.append('email', this.formResponse.get("email")!.value);
      formData.append('phone_number', this.formResponse.get("phone_number")!.value);
      formData.append('address.city', this.formResponse.get("address")!.get('city')!.value);
      formData.append('address.state', this.formResponse.get("address")!.get('state')!.value);
      formData.append('address.country', this.formResponse.get("address")!.get('country')!.value);
      formData.append('address.primary_address', this.formResponse.get("address")!.get('primary_address')!.value);
      formData.append('address.postal_code', this.formResponse.get("address")!.get('postal_code')!.value);

      if (this.success) {
        formData.append('subscription_type', this.formResponse.get("subscription_type")!.value === undefined ? '' : this.formResponse.get("subscription_type")!.value);
        formData.append('period', this.formResponse.get("period")!.value === undefined ? '' : this.formResponse.get("period")!.value);
        formData.append('razorpay_payment_id', this.payment_info.razorpay_payment_id);
        formData.append('razorpay_order_id', this.payment_info.razorpay_order_id);
        formData.append('razorpay_signature', this.payment_info.razorpay_signature);
        formData.append('amount', this.payment_info.amount.toString());
      }

      this.updateProfileApi = this.userService.updateProfile(formData)
        .subscribe({
          next: (response) => {
            if (this.profile?.subscription === null || this.profile?.subscription === undefined) {
              this.updatePermissions();
            }
            else {
              this.al.alertPrompt("Profile Updated", "Updated profile information sucessfully!", "success")
            }
          },
          error: (err) => {
            this.al.alertPrompt("Error", err.error, "error");
          },
        })
    }
    else {
      this.al.alertPrompt("Invalid input", "Check if all form fields are filled", "error");
    }

  }

  ngOnDestroy() {
    this.getProfileApi.unsubscribe();
    this.createOrderApi.unsubscribe();
    this.updateProfileApi.unsubscribe();
    this.permissionsApi.unsubscribe();
  }
}
