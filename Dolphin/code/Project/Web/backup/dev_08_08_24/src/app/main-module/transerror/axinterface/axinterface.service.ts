import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class AxinterfaceService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  search(obj:any) {
    return this.http.post<any>('/wms-transaction-service/axinterface/find' ,obj);
  }
 
 
}


