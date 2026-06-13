import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  constructor(private http: HttpClient, private auth: AuthService) {}

  Get(companyId: string) {
    return this.http.get<any>('/overc-idmaster-service/company/' + companyId + '?languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/company', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/company/' + obj.companyId + '?languageId='+ this.auth.languageId, obj);
  }

  Delete(companyId: string) {
    return this.http.delete<any>('/overc-idmaster-service/company/' + companyId + '?languageId=' + this.auth.languageId );
  }

  search(obj: any) {
    return this.http.post<any>(
      '/overc-idmaster-service/company/' + 'find',
      obj
    );
  }
}
