import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class BillModeService {

  
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(billModeId: string) {
    return this.http.get<any>('/overc-idmaster-service/billmode/' + billModeId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/billmode', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/billmode/'+ obj.billModeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(billModeId: string) {
    return this.http.delete<any>('/overc-idmaster-service/billmode/' + billModeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/billmode/find', obj);
  }

}
