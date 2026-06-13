import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class UserRoleService {

  constructor(
    private http: HttpClient,
    private auth: AuthService,
  ) { }

  Get (roleId: any) {
    return this.http.get<any>('/overc-idmaster-service/roleAccess/' + roleId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }


  Create (obj: any) {
    return this.http.post<any>('/overc-idmaster-service/roleAccess', obj);
  }

  Update (obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/roleAccess/' + obj.roleId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj + '&menuId=' + obj.menuId + '&subMenuId=' + obj.subMenuId);
  }

  Delete (obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/roleAccess/' + obj.roleId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId + '&menuId=' + obj.menuId + '&subMenuId=' + obj.subMenuId);
  }

  search (obj: any) {
    return this.http.post<any>('/overc-idmaster-service/roleAccess/find', obj);
  }

  searchModule (obj: any) {

  }

}
