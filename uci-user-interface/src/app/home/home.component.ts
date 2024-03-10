import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  supplier: boolean = sessionStorage.getItem("SUPPLIER") === "true";
  consumer: boolean = sessionStorage.getItem("CONSUMER") === "true";
  transporter: boolean = sessionStorage.getItem("TRANSPORTER") === "true";
}
