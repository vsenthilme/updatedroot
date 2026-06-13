import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class PickupService {


  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(pickupId: string) {
    return this.http.get<any>('/overc-lastmile-service/pickup/get' + pickupId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-lastmile-service/pickup', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-lastmile-service/pickup/update/list', obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-lastmile-service/pickup/delete/list');
  }

  search(obj: any) {
    return this.http.post<any>('/overc-lastmile-service/pickup/find', obj);
  }

}
