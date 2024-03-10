import { Directive, ElementRef, Input } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appPermission]',
  standalone:true
})
export class PermissionDirective {

  @Input() value:string = "";
  constructor(private el: ElementRef, private authService: AuthService) { }

  ngOnInit() {   
    this.el.nativeElement.disabled = !this.authService.hasRequiredRole([this.value]);
  }
}
