import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginUser} from "../model/user/LoginUser";
import {Token} from "../model/Token";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  loginUser(user: LoginUser) {
    const body = JSON.stringify(user);
    return this.http.post<Token>("http://37.221.125.51:8090/users/login", body, {
      headers: {
        "Content-Type": "application/json"
      },
      withCredentials: true
    });
  }
}
