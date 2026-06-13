import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class DistrictMappingService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(partnerId: string) {
    return this.http.get<any>('/overc-idmaster-service/districtMapping/' + partnerId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/districtMapping', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/districtMapping/'+ obj.partnerId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&districtId='+ obj.districtId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/districtMapping/' + obj.partnerId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&districtId='+ obj.districtId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/districtMapping/find', obj);
  }
}
