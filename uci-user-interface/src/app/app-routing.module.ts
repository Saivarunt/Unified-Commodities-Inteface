import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AuthGuard } from './auth.guard';
import { RoleGuard } from './role.guard';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';

const routes: Routes = [
  {path:"login", component: LoginComponent},
  {path:"", redirectTo:'login', pathMatch:'full'},
  {path:"register", component: RegistrationComponent},
  {path:"unauthorized", component: UnauthorizedComponent},
  {
    path:"profile", loadComponent: () => 
    import("./profile/profile.component")
    .then(out => out.ProfileComponent) , canActivate: [AuthGuard, RoleGuard], data: {'roles': ['ADMIN', 'SUPPLIER', 'CONSUMER', 'TRANSPORTER']}
  },
  {
    path:"home", loadChildren: () => 
      import('./home/home.module')
      .then(out => out.HomeModule), canActivate: [AuthGuard, RoleGuard], data: {'roles': ['ADMIN', 'SUPPLIER', 'CONSUMER', 'TRANSPORTER']}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
