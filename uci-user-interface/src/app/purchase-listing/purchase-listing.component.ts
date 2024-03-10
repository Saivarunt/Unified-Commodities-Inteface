import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProductListing } from '../interfaces/product-listing';
import { PurchaseService } from '../services/purchase.service';
import { PurchaseInfo } from '../interfaces/purchase-info';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-purchase-listing',
  templateUrl: './purchase-listing.component.html',
  styleUrls: ['./purchase-listing.component.scss']
})
export class PurchaseListingComponent {
  purchaseProductApi: Subscription = new Subscription();
  productInterest!: ProductListing;
  price: number = 0;

  constructor(public dialogRef: MatDialogRef<PurchaseListingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {product: ProductListing} , private fb: FormBuilder, private purchaseService: PurchaseService, private router: Router) {}


    formResponse: FormGroup = this.fb.group({
      "quantity": ["", [Validators.required, Validators.minLength(1)]],
      "transportation_type": [""]
    })

    ngOnInit() {
      this.productInterest = this.data.product;
      
      if(this.data.product.transportation_type === 'Self') {
        this.formResponse.patchValue({
          transportation_type: this.data.product.transportation_type
        })
      }
    }

    purchase() {

      if(!this.formResponse.invalid){
        const interest = {
          _id: this.productInterest._id,
          quantity: this.formResponse.value.quantity,
          transportation_type: this.formResponse.value.transportation_type
        };
        
        this.purchaseProductApi = this.purchaseService.purchaseProduct(interest)
        .subscribe({
          next: (response) => {
          },
          error: (err) => {                       
            alert(err.error);
          }
        });
        
      }
      else{
        alert("Verify Inputs");
      }
      
    }
    
    purchasePrice() {     
      this.price = parseFloat(this.formResponse.value.quantity) * (this.productInterest.price / this.productInterest.quantity);
    }
    
    ngOnDestroy() {
      this.purchaseProductApi.unsubscribe();

      if(this.formResponse.value.transportation_type === "Self"){
        this.router.navigate(['home/delivery-details'])
      }
      else{
        this.router.navigate(['home/transportation'])
      }
    
    }
}
