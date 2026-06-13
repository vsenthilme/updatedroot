import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ProvinceMappingService {

 
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(partnerId: string) {
    return this.http.get<any>('/overc-idmaster-service/provinceMapping/' + partnerId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/provinceMapping', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/provinceMapping/'+ obj.partnerId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&provinceId='+ obj.provinceId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/provinceMapping/' + obj.partnerId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&provinceId='+ obj.provinceId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/provinceMapping/find', obj);
  }
}
