import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private router: Router,
              private cookie: CookieService) {
  }

  ngOnInit(): void {
    if (this.cookie.get('jwt-token').length < 5) {
      this.router.navigate(['/error']);
    } else {
      this.router.navigate(['/main']);
    }
  }
}
