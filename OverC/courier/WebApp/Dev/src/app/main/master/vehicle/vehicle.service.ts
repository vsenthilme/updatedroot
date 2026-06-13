import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(vehicleRegNumber: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/vehicle/' + vehicleRegNumber + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/vehicle', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/vehicle/' + obj.vehicleRegNumber + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(vehicleRegNumber: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/vehicle/' + vehicleRegNumber + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/vehicle/' + 'find', obj);
  }

}
