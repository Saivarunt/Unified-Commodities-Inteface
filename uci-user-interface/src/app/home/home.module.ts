import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing/home-routing.module';
import { HomeComponent } from './home.component';
import { TransporterAccessDirective } from '../directives/transporter-access.directive';
import { ConsumerAccessDirective } from '../directives/consumer-access.directive';
import { SupplierAccessDirective } from '../directives/supplier-access.directive';
import { SupplierHomeComponent } from '../supplier-home/supplier-home.component';
import { ConsumerHomeComponent } from '../consumer-home/consumer-home.component';
import { TransporterHomeComponent } from '../transporter-home/transporter-home.component';
import { RouterModule } from '@angular/router';
import {MatTabsModule} from '@angular/material/tabs';
import { ProfileComponent } from '../profile/profile.component';
import { AdminAccessDirective } from '../directives/admin-access.directive';
import {MatTableModule} from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { PermissionDirective } from '../directives/permission.directive';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    HomeComponent, 
    SupplierAccessDirective, 
    ConsumerAccessDirective, 
    TransporterAccessDirective,
    AdminAccessDirective,
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SupplierHomeComponent,
    ConsumerHomeComponent,
    TransporterHomeComponent,
    RouterModule,
    MatTabsModule,
    MatTableModule,
    MatButtonModule
  ],
  exports: [
    SupplierAccessDirective, 
    ConsumerAccessDirective, 
    TransporterAccessDirective,
    AdminAccessDirective,
  ]
})
export class HomeModule { }
