import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(eventCode: string) {
    return this.http.get<any>('/overc-idmaster-service/event/' + eventCode);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/event', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/event/' + obj.eventCode + '?languageId=' + this.auth.languageId + '&companyId=' + this.auth.companyId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/event/' + obj.eventCode + '?languageId=' + this.auth.languageId + '&companyId=' + this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/event/find', obj);
  }

}
