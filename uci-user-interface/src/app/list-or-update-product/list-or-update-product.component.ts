import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProductService } from '../services/product.service';
import { ProductListing } from '../interfaces/product-listing';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-list-or-update-product',
  templateUrl: './list-or-update-product.component.html',
  styleUrls: ['./list-or-update-product.component.scss']
})
export class ListOrUpdateProductComponent {
  listProductApi: Subscription = new Subscription();
  updateProductApi: Subscription = new Subscription();
  deleteProductApi: Subscription = new Subscription();
  product_image: File | string = "";
  product_id: string = ""; 
  type: string = "add";


  formResponse: FormGroup = this.fb.group({
    "product_name": ["", [Validators.required, Validators.minLength(2)]],
    "description": ["", [Validators.required, Validators.minLength(2)]],
    "price": ["", [Validators.required, Validators.min(1)]],
    "quantity": ["", [Validators.required, Validators.minLength(1)]],
    "transportation_type": [""]
  })

  constructor(public dialogRef: MatDialogRef<ListOrUpdateProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {product: ProductListing, type: string} | null, private fb: FormBuilder, private productService: ProductService, private router: Router) {}

  addFile($event:any){
    this.product_image = $event.target.files[0];
  }

  ngOnInit() {
    
    if(this.data !== null){
      this.formResponse.patchValue({
        product_name: this.data.product.product_name,
        description: this.data.product.description,
        price: this.data.product.price,
        quantity: this.data.product.quantity,
        transportation_type: this.data.product.transportation_type,
      });

      this.product_id= this.data.product._id;
      this.type = this.data.type;
    }
  }

  listingProduct() {
    
    if(!this.formResponse.invalid && this.product_image !== ""){
      const formData: FormData = new FormData();
      formData.append('product_image',  this.product_image === ""? new Blob([]): this.product_image);
      formData.append('id', this.product_id);
      formData.append('product_name', this.formResponse.value.product_name);
      formData.append('description', this.formResponse.value.description);
      formData.append('price', this.formResponse.value.price);
      formData.append('quantity', this.formResponse.value.quantity);
      formData.append('transportation_type', this.formResponse.value.transportation_type);
      
      this.listProductApi = this.productService.listProduct(formData)
      .subscribe({
        next: (response) => {
          window.location.reload();
        },
        error: (err) => {
          alert(err.error)
        }
      })
    }
    else{
      alert("Check inputs!");
    }
  }

  updateProduct() {
    
    if(!this.formResponse.invalid){
      const formData: FormData = new FormData();
      formData.append('product_image',  this.product_image === ""? new Blob([]): this.product_image);
      formData.append('id', this.product_id);
      formData.append('product_name', this.formResponse.value.product_name);
      formData.append('description', this.formResponse.value.description);
      formData.append('price', this.formResponse.value.price);
      formData.append('quantity', this.formResponse.value.quantity);
      formData.append('transportation_type', this.formResponse.value.transportation_type);
      
      this.updateProductApi = this.productService.updateProductListing(formData)
      .subscribe({
        next: (response) => {
          window.location.reload();
        },
        error: (err) => {
          alert(err.error)
        }
      })
    }
    else{
      alert("Check inputs!");
    }
  }

  deleteProduct() {
    this.deleteProductApi = this.productService.deleteProductListing(this.product_id)
      .subscribe({
        next: (response) => {
          window.location.reload();
        },
        error: (err) => {
          alert(err.error)
        }
      })
  }

  ngOnDestroy() {
    this.listProductApi.unsubscribe();
    this.updateProductApi.unsubscribe();
    this.deleteProductApi.unsubscribe();
  }
}
