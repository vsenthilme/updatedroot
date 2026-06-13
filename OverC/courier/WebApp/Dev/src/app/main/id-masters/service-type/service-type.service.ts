import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ServiceTypeService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(serviceTypeId: string) {
    return this.http.get<any>('/overc-idmaster-service/serviceType/' + serviceTypeId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/serviceType', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/serviceType/'+ obj.serviceTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(serviceTypeId: string) {
    return this.http.delete<any>('/overc-idmaster-service/serviceType/' + serviceTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/serviceType/find', obj);
  }

}
