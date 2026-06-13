import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class UomService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(uomId: string) {
    return this.http.get<any>('/overc-idmaster-service/uom/' + uomId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/uom', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/uom/'+ obj.uomId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(uomId: string) {
    return this.http.delete<any>('/overc-idmaster-service/uom/' + uomId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }
  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/uom/find', obj);
  }
}

