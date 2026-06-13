import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ModuleService {

    constructor(private http: HttpClient, private auth: AuthService) {}
  
    Get(moduleId: string) {
      return this.http.get<any>('/overc-idmaster-service/module/modulelist/' + moduleId + '?languageId=' + this.auth.languageId + '&companyId=' + this.auth.companyId);
    }
  
    Create(obj: any) {
      return this.http.post<any>('/overc-idmaster-service/module/bulk', obj);
    }
  
    Update(obj: any) {
      return this.http.patch<any>('/overc-idmaster-service/module/' + obj[0].moduleId + '?languageId='+ this.auth.languageId + '&companyId=' + this.auth.companyId, obj);
    }
  
    Delete(moduleId: string) {
      return this.http.delete<any>('/overc-idmaster-service/module/' + moduleId + '?languageId=' + this.auth.languageId  + '&companyId=' + this.auth.companyId);
    }
  
    search(obj: any) {
      return this.http.post<any>('/overc-idmaster-service/module/find',obj);
    }
  }
  
