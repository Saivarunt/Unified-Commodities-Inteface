import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProductListing } from '../interfaces/product-listing';
import { PurchaseService } from '../services/purchase.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-purchase-listing',
  templateUrl: './purchase-listing.component.html',
  styleUrls: ['./purchase-listing.component.scss']
})
export class PurchaseListingComponent {
  purchaseProductApi: Subscription = new Subscription();
  productInterest!: ProductListing;
  price: number = 0;
  close: boolean = false;

  constructor(public dialogRef: MatDialogRef<PurchaseListingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { product: ProductListing }, private fb: FormBuilder, private purchaseService: PurchaseService, private router: Router, private al: AlertService) { }


  formResponse: FormGroup = this.fb.group({
    "quantity": ["", [Validators.required, Validators.minLength(1)]],
    "transportation_type": [""]
  })

  ngOnInit() {
    this.productInterest = this.data.product;

    if (this.data.product.transportation_type === 'Self') {
      this.formResponse.patchValue({
        transportation_type: this.data.product.transportation_type
      })
    }
  }

  purchase() {

    if (!this.formResponse.invalid) {
      const interest = {
        _id: this.productInterest._id,
        quantity: this.formResponse.value.quantity,
        transportation_type: this.formResponse.value.transportation_type
      };

      this.purchaseProductApi = this.purchaseService.purchaseProduct(interest)
        .subscribe({
          next: (response) => {
            this.al.alertPrompt("Purchase Initiated", "Purchase request was sent.", "success");
          },
          error: (err) => {
            this.al.alertPrompt("Error", err.error, "error");;
          }
        });

    }
    else {
      this.al.alertPrompt("Invalid inputs", "Please check inputs provided!", "error");
    }

  }

  dialogueClosed() {
    this.close = true;
  }

  purchasePrice() {
    this.price = parseFloat(((this.formResponse.value.quantity) * (this.productInterest.price / this.productInterest.quantity)).toFixed(2));
  }

  ngOnDestroy() {
    this.purchaseProductApi.unsubscribe();

    if (this.formResponse.value.transportation_type === "Self" && !this.close) {
      this.router.navigate(['home/delivery-details'])
    }
    else if (this.formResponse.value.transportation_type !== "" && !this.close) {
      this.router.navigate(['home/transportation'])
    }

  }
}
