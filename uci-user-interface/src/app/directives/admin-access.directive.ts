import { Directive, ElementRef } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appAdminAccess]'
})
export class AdminAccessDirective {

  constructor(private el: ElementRef, private authService: AuthService) { }

  ngOnInit () {
    this.el.nativeElement.style.display = !this.authService.hasRequiredRole(["ADMIN"])? "none": "block";
  }
}
