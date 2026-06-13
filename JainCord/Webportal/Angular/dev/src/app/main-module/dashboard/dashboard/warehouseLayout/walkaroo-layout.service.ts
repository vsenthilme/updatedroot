import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WalkarooLayoutService {
  constructor(private http: HttpClient) { }

  find(obj:any) {
    return this.http.post<any>('/wms-transaction-service/dashBoard/v3/storageBinDashboard', obj);
  }
  update(obj:any) {
    return this.http.patch<any>('/wms-idmaster-service/notificationMessage/update', obj);
  }  
  updateNewCreate() {
    return this.http.get<any>('/wms-idmaster-service/notificationMessage/update/newCreated');
  }
}
