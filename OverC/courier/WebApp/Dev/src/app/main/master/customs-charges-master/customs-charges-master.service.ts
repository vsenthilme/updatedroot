import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CustomsChargesMasterService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(sortingId: string) {
    return this.http.get<any>('/overc-idmaster-service/sortmaster/create-sort-master/' + sortingId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/customCharge/create/list', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/customCharge/update/list', obj);
  }

  Delete(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/customCharge/delete/list' ,obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/customCharge/find', obj);
  }


}
