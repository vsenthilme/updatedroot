import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CityMappingService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(partnerId: string) {
    return this.http.get<any>('/overc-idmaster-service/cityMapping/' + partnerId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/cityMapping', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/cityMapping/'+ obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&cityId='+ obj.cityId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/cityMapping/' + obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&cityId='+ obj.cityId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/cityMapping/find', obj);
  }
}
