import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoadTypeService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(loadTypeId: string) {
    return this.http.get<any>('/overc-idmaster-service/loadType/' + loadTypeId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/loadType', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/loadType/'+ obj.loadTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(loadTypeId: string) {
    return this.http.delete<any>('/overc-idmaster-service/loadType/' + loadTypeId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/loadType/find', obj);
  }
}


