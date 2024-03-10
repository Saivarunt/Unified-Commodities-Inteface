import { Directive, ElementRef } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appSupplierAccess]'
})
export class SupplierAccessDirective {

  constructor(private el: ElementRef, private authService: AuthService) { }

  ngOnInit () {
    this.el.nativeElement.style.display = !this.authService.hasRequiredRole(["SUPPLIER"])? "none": "block";
  }

}
