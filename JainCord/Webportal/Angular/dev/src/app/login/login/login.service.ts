import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  login(obj: any) {
    return this.http.post<any>('/wms-idmaster-service/login/v2', obj);
  }
}