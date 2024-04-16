import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {RegisterUser} from "../../model/user/RegisterUser";
import {RegisterService} from "../../services/register.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerUser: RegisterUser = new RegisterUser();

  constructor(private registerService: RegisterService,
              private router: Router) {}

  register() {
    this.registerService.registerUser(this.registerUser).subscribe({
      next: (user) => {
        this.registerUser = new RegisterUser();
        this.router.navigate(["/sign-in"]);
      },
      error: (error) => {
        console.log(error)
      }
    })
  }
}
