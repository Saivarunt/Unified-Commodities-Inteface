import { Directive, ElementRef } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appConsumerAccess]'
})
export class ConsumerAccessDirective {
  constructor(private el: ElementRef, private authService: AuthService) { }

  ngOnInit() {
    this.el.nativeElement.style.display = !this.authService.hasRequiredRole(["CONSUMER"]) ? "none" : "block";
  }
}
