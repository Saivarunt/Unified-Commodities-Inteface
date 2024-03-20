import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LifecycleResponse } from '../interfaces/lifecycle-response';
import { ProductService } from '../services/product.service';
import { RatingComponent } from '../rating/rating.component';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-delivery-status',
  templateUrl: './delivery-status.component.html',
  styleUrls: ['./delivery-status.component.scss']
})
export class DeliveryStatusComponent {
  updateSupplierStatusApi: Subscription = new Subscription();
  updateConsumerStatusApi: Subscription = new Subscription();
  interval = setTimeout(() => {}, 0);

 constructor(public dialogRef: MatDialogRef<DeliveryStatusComponent>,
  @Inject(MAT_DIALOG_DATA) public data: {status: string, lifecycle: LifecycleResponse}, private productService: ProductService, public dialog: MatDialog, private al: AlertService) {}


  openRatingDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(RatingComponent, {
      width: '30vh',
      height: '30vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {lifecycle: this.data.lifecycle}
    });
  }
  
  rate() {
    this.openRatingDialog('30ms','30ms');
  }


  updateStatus() {
    Swal.showLoading();

    if(this.data.status === 'Supplier') {

      this.updateSupplierStatusApi = this.productService.updateSupplierStatus(this.data.lifecycle._id) 
      .subscribe({
        next: (response) => {
          Swal.close();
          if(this.data.lifecycle.supplier !== this.data.lifecycle.transporter){
            this.rate();
          }
          else {
            clearTimeout(this.interval);
            this.interval = setTimeout(() => this.dialogRef.close(), 1800);   
          }

          this.al.alertPrompt("UPDATED SUPPLIER STATUS", "Supplier status was successfully updated", "success");
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      });
    }
    else if(this.data.status === 'Consumer') {
      this. updateConsumerStatusApi = this.productService.updateConsumerStatus(this.data.lifecycle._id) 
      .subscribe({
        next: (response) => {
          Swal.close();
          this.rate();
          this.al.alertPrompt("UPDATED CONSUMER STATUS", "Supplier status was successfully updated", "success");
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      });
    }

  }

  ngOnDestroy() {
    this.updateSupplierStatusApi.unsubscribe();
    this.updateConsumerStatusApi.unsubscribe();
    window.location.reload();
  }
}
