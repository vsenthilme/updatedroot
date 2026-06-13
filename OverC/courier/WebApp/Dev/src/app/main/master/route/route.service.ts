import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(routeId: string) {
    return this.http.get<any>('/overc-idmaster-service/route/' + routeId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/route', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/route/'+ obj.routeId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&legId='+ obj.legId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/route/' + obj.routeId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&legId='+ obj.legId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/route/find', obj);
  }
}
