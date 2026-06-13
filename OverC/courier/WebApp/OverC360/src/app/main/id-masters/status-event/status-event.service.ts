import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class StatusEventService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(typeId: string) {
    return this.http.get<any>('/overc-idmaster-service/statusevent/' + typeId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/statusevent', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/statusevent/'+ obj.typeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(typeId: string) {
    return this.http.delete<any>('/overc-idmaster-service/statusevent/' + typeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/statusevent/find', obj);
  }

}


