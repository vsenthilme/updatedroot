import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  find(obj:any) {
    return this.http.post<any>('/wms-idmaster-service/notificationMessage/find', obj);
  }
  update(obj:any) {
    return this.http.patch<any>('/wms-idmaster-service/notificationMessage/update', obj);
  }
  delete(obj:any) {
    return this.http.post<any>('/wms-idmaster-service/notificationMessage/delete', obj);
  }
  deleteall(obj:any) {
    return this.http.post<any>('/wms-idmaster-service/notificationMessage/deleteall', obj);
  }

  getNotificationsDBData() {
    return this.http.get<any>("/wms-idmaster-service/notificationMessage" + '?userId=' + this.auth.userID);
  }
  markNotificationsAsRead() {
    return this.http.get<any>("/wms-idmaster-service/notificationMessage/mark-read-all");
  }
  readOnenotification(id: any) {
    return this.http.get<any>("/wms-idmaster-service/notificationMessage/mark-read/" + id);
  }
}
