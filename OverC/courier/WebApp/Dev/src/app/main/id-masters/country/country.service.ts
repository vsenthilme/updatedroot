import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(countryId: string) {
    return this.http.get<any>('/overc-idmaster-service/country/' + countryId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/country', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/country/'+ obj.countryId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(countryId: string) {
    return this.http.delete<any>('/overc-idmaster-service/country/' + countryId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/country/find', obj);
  }
}
