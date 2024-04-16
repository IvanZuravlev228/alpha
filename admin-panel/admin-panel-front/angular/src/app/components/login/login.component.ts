import { Component } from '@angular/core';
import {LoginService} from "../../services/login.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {LoginUser} from "../../model/user/LoginUser";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../register/register.component.css']
})
export class LoginComponent {
  loginUser: LoginUser = new LoginUser();

  constructor(private loginService: LoginService,
              private cookie: CookieService,
              private router: Router) {}

  login() {
    this.loginService.loginUser(this.loginUser).subscribe({
      next: (token) => {
        this.cookie.set("jwt-token", token.token);
        this.router.navigate(["/main"])
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
