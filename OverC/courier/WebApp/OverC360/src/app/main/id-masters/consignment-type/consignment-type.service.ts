import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ConsignmentTypeService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(consignmentTypeId: string) {
    return this.http.get<any>('/overc-idmaster-service/consignmentType/' + consignmentTypeId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/consignmentType', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/consignmentType/'+ obj.consignmentTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(consignmentTypeId: string) {
    return this.http.delete<any>('/overc-idmaster-service/consignmentType/' + consignmentTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/consignmentType/find', obj);
  }
}
