import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CountryMappingService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(partnerId: string) {
    return this.http.get<any>('/overc-idmaster-service/countryMapping/' + partnerId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/countryMapping', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/countryMapping/'+ obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&countryId='+ obj.countryId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/countryMapping/' + obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&countryId='+ obj.countryId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/countryMapping/find', obj);
  }
}
