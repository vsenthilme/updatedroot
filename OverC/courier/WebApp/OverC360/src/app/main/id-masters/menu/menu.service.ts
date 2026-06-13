import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(languageId: string, companyId: string, menuId: string, subMenuId: string, authorizationObjectId: string) {
    return this.http.get<any>('/overc-idmaster-service/menu/' + menuId + '?languageId=' +
        this.auth.languageId + authorizationObjectId + companyId + subMenuId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/menu', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/menu/' + obj.menuId + '?authorizationObjectId=' + obj.authorizationObjectId + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId + '&subMenuId=' + obj.subMenuId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/menu/' + obj.menuId + '?authorizationObjectId=' + obj.authorizationObjectId + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId + '&subMenuId=' + obj.subMenuId );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/menu/find', obj);
  }
}
