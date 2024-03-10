import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { LifecycleResponse } from '../interfaces/lifecycle-response';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { HomeModule } from '../home/home.module';
import { ProductService } from '../services/product.service';
import { Router, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-rating',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule, MatButtonModule, MatDialogModule, HomeModule, RouterModule],
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent {
  rateProductApi: Subscription = new Subscription();
  rateTransporterApi: Subscription = new Subscription();
  faStar = faStar;
  rating: number = 0;

  constructor(public dialogRef: MatDialogRef<RatingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {lifecycle: LifecycleResponse}, private productService: ProductService, private router: Router) {}

  setRating(value: number) {
    this.rating = value; 
  }

  sendProductRating() {
    this.rateProductApi = this.productService.rateProduct(this.rating, this.data.lifecycle._id)
    .subscribe({
      next: (response) => {

        if(this.data.lifecycle.consumer !== this.data.lifecycle.transporter) {         
          this.sendTransporterRating();
        }
        else{
          this.dialogRef.close();
        }

      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  sendTransporterRating() {
    this.rateTransporterApi = this.productService.rateTransporter(this.rating, this.data.lifecycle._id)
    .subscribe({
      next: (response) => {
        this.dialogRef.close();
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }
  
  ngOnDestroy() {
    this.rateTransporterApi.unsubscribe();
    this.rateProductApi.unsubscribe();
    window.location.reload();    
  }
}
