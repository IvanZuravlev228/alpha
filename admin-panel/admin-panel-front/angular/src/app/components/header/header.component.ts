import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private router: Router,
              private cookieService: CookieService) {}
  goToRegisterPage() {
    this.router.navigate(["/sign-up"]);
  }

  goToLoginPage() {
    this.router.navigate(["/sign-in"]);
  }

  logout() {
    this.cookieService.delete("jwt-token");
  }
}
