import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class StatusService {


  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(statusId: string) {
    return this.http.get<any>('/overc-idmaster-service/status/' + statusId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/status', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/status/'+ obj.statusId +'?languageId='+ this.auth.languageId, obj);
  }

  Delete(statusId: string) {
    return this.http.delete<any>('/overc-idmaster-service/status/' + statusId +'?languageId='+ this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/status/find', obj);
  }
}


