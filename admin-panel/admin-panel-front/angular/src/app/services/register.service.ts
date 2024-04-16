import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user/User";
import {RegisterUser} from "../model/user/RegisterUser";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  registerUser(user: RegisterUser) {
    const body = JSON.stringify(user);
    return this.http.post<User>("http://37.221.125.51:8090/users/register", body, {
      headers: {
        "Content-Type": "application/json"
      },
      withCredentials: true
    });
  }
}
