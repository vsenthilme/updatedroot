import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({ 
  providedIn: 'root'
})
export class BondedManifestService {

  constructor(private http: HttpClient, private auth: AuthService) { }

 
  Get(obj: any) {
    return this.http.get<any>('/overc-midmile-service/bondedManifest/' + obj);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-midmile-service/bondedManifest/create', obj);
  }
  CreatefromPrealert(obj: any) {
    return this.http.post<any>('/overc-midmile-service/bondedManifest/create/preAlert', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/bondedManifest/update/list', obj);
  }

  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/bondedManifest/delete/list', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/bondedManifest/findBondedManifest', obj);
  }
}
