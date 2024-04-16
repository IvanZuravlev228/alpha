import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  security_word: string = "";

  constructor(private router: Router) {
  }

  toMainPage() {
    if (this.security_word === "3v20x7") {
      this.router.navigate(['/main']);
    }
  }
}
