import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  find(obj:any) {
    return this.http.post<any>('/wms-idmaster-service/notificationMessage/find', obj);
  }
  update(obj:any) {
    return this.http.patch<any>('/wms-idmaster-service/notificationMessage/update', obj);
  }
}
