import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PrealertService {

  constructor(private http: HttpClient) { }
  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/prealert/findPrealert', obj);
  }


  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/prealert/delete/list', obj);
  }

  update(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/prealert/update/list', obj);
  }
}
