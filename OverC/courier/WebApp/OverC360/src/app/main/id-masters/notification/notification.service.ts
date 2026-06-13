import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(notificationId: string) {
    return this.http.get<any>('/overc-idmaster-service/notification/' + notificationId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/notification', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/notification/'+ obj.notificationId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(notificationId: string) {
    return this.http.delete<any>('/overc-idmaster-service/notification/' + notificationId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/notification/find', obj);
  }

}
