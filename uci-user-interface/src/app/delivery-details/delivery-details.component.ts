import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { ProductService } from '../services/product.service';
import { LifecycleResponse } from '../interfaces/lifecycle-response';
import { HomeModule } from '../home/home.module';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DeliveryStatusComponent } from '../delivery-status/delivery-status.component';
import { MatButtonModule } from '@angular/material/button';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-delivery-details',
  standalone: true,
  imports: [CommonModule, MatPaginatorModule, HomeModule, MatDialogModule, MatButtonModule],
  templateUrl: './delivery-details.component.html',
  styleUrls: ['./delivery-details.component.scss']
})
export class DeliveryDetailsComponent {
  deliveryInfoApi: Subscription = new Subscription();
  lifecycles: LifecycleResponse[] | [] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;
  status: string = "";
  lifecycle_id: string = "";

  constructor(private productService: ProductService,  public dialog: MatDialog, private al: AlertService) {}

  ngOnInit() {
    this.deliveryInfo(this.pageIndex);
  }

  deliveryInfo(page: number) {
    this.deliveryInfoApi = this.productService.viewLifecycle(page)
    .subscribe({
      next: (response) => {
        if(response !== null) {
          this.lifecycles = response.content;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
        }
        else {
          this.lifecycles = response;
        }
      },
      error: (err) => {
        this.al.alertPrompt("Error", err.error, "error");;
      }
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.deliveryInfo(this.pageIndex);
  }

  openStatusUpdateDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(DeliveryStatusComponent, {
      width: '40vh',
      height: '40vh',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {status: this.status, lifecycle: this.lifecycles.filter((val) => val._id === this.lifecycle_id)[0]}
    });
  }

  updateStatus(status: string, lifecycle_id: string) {
    this.status = status;
    this.lifecycle_id = lifecycle_id;
    this.openStatusUpdateDialog('30ms', '30ms');
  }

  ngOnDestroy() {
    this.deliveryInfoApi.unsubscribe();
  }
}
