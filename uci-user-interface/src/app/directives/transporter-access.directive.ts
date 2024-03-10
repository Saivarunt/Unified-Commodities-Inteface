import { Directive, ElementRef } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appTransporterAccess]'
})
export class TransporterAccessDirective {

  
  constructor(private el: ElementRef, private authService: AuthService) { }

  ngOnInit () {
    this.el.nativeElement.style.display = !this.authService.hasRequiredRole(["TRANSPORTER"])? "none": "block";
  }

}
