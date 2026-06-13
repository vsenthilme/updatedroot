import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root',
})
export class SubProductService {
  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(subProductId: string, subProductValue: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/subProduct/' + subProductId + "?subProductValue=" + subProductValue + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/subProduct', obj);
  }

  CreateBulk(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/subProduct/create/list', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/subProduct/' + obj.subProductId + "?subProductValue=" + obj.subProductValue + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  UpdateBulk(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/subProduct/update/list', obj);
  }

  Delete(subProductId: string, subProductValue: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/subProduct/' + subProductId + "?subProductValue=" + subProductValue + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  DeleteBulk(obj: any) {
    return this.http.post<any>(
      '/overc-idmaster-service/subProduct/delete/list', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/subProduct/' + 'find', obj);
  }
}
