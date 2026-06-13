import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class SortingMasterService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(sortingId: string) {
    return this.http.get<any>('/overc-idmaster-service/sortmaster/create-sort-master/' + sortingId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/sortmaster/create-sort-master/', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/sortmaster/create-sort-master/'+ obj.sortingId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/sortingMaster/' + obj.sortingId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/sortingMaster/find', obj);
  }

}
