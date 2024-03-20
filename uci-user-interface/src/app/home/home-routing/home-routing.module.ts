import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../home.component';
import { AuthGuard } from 'src/app/auth.guard';
import { ProductsComponent } from 'src/app/products/products.component';
import { UsersComponent } from 'src/app/users/users.component';
import { TransportRequestsComponent } from 'src/app/transport-requests/transport-requests.component';
import { TransporterProposalsComponent } from 'src/app/transporter-proposals/transporter-proposals.component';
import { PurchaseInfoComponent } from 'src/app/purchase-info/purchase-info.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { RoleGuard } from 'src/app/role.guard';
import { FormsModule } from '@angular/forms';

const appRoute: Routes = [
  {
    path: "", component: HomeComponent, canActivate:[AuthGuard],
    children: [
      {
        path: "transportation", loadComponent:() =>
          import('src/app/transportation/transportation.component')
          .then(out => out.TransportationComponent)
        , canActivate:[AuthGuard, RoleGuard], data: {'roles': ['CONSUMER', 'ADMIN']}
      },
      {
        path: "supplier-home", loadComponent: () =>
        import('src/app/supplier-home/supplier-home.component')
        .then(out => out.SupplierHomeComponent), canActivate:[AuthGuard, RoleGuard], data: {'roles': ['SUPPLIER', 'ADMIN']}
      },
      {
        path: "consumer-home", loadComponent: () =>
        import('src/app/consumer-home/consumer-home.component')
        .then(out => out.ConsumerHomeComponent), canActivate:[AuthGuard, RoleGuard], data: {'roles': ['CONSUMER', 'ADMIN']}
      },
      {
        path: "transporter-home", loadComponent: () =>
        import('src/app/transporter-home/transporter-home.component')
        .then(out => out.TransporterHomeComponent), canActivate:[AuthGuard, RoleGuard], data: {'roles': ['TRANSPORTER', 'ADMIN']}
      },
      {
        path: "delivery-details", loadComponent: () =>
        import('src/app/delivery-details/delivery-details.component')
        .then(out => out.DeliveryDetailsComponent), canActivate:[AuthGuard, RoleGuard], data: {'roles': ['ADMIN', 'SUPPLIER', 'CONSUMER', 'TRANSPORTER']}
      },
      {
        path: "delivery-proposals", loadComponent: () =>
        import('src/app/delivery-proposals/delivery-proposals.component')
        .then(out => out.DeliveryProposalsComponent), canActivate:[AuthGuard, RoleGuard], data: {'roles': ['TRANSPORTER', 'ADMIN']}
      },
    ],
  },
  
]

@NgModule({
  declarations: [    
    ProductsComponent,
    UsersComponent,
    TransportRequestsComponent,
    TransporterProposalsComponent,
    PurchaseInfoComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(appRoute),
    MatPaginatorModule,
    MatTableModule,
    MatButtonModule,
    FormsModule
  ],
  exports: [
    RouterModule,     
    ProductsComponent,
    UsersComponent,
    TransportRequestsComponent,
    TransporterProposalsComponent,
    PurchaseInfoComponent]
})
export class HomeRoutingModule { }
