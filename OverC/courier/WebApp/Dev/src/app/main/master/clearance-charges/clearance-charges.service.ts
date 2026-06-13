import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ClearanceChargesService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(customerId: string) {
    return this.http.get<any>('/overc-midmile-service/clearanceCharges/' + customerId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }
  CreateBulk(obj: any) {
    return this.http.post<any>('/overc-midmile-service/clearanceCharges', obj);
  }

  UpdateBulk(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/clearanceCharges/update/list', obj);
  }

  Delete(obj:any) {
    return this.http.post<any>('/overc-midmile-service/clearanceCharges/delete/list', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/clearanceCharges/find', obj);
  }

}

