import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(cityId: string) {
    return this.http.get<any>('/overc-idmaster-service/city/' + cityId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/city', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/city/'+ obj.cityId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&countryId='+ obj.countryId + '&provinceId='+ obj.provinceId
      +'&districtId='+obj.districtId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/city/' + obj.cityId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId
      + '&countryId='+ obj.countryId + '&provinceId='+ obj.provinceId +'&districtId='+obj.districtId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/city/find', obj);
  }
}

