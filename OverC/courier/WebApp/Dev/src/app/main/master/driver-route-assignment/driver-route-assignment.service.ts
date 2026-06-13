import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class DriverRouteAssignmentService {


  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(obj: any) {
    return this.http.get<any>(
      '/overc-idmaster-service/driverRouteAssignment/' + obj.courierId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId +'&routeId=' + obj.routeId + '&vehicleRegNumber=' + obj.vehicleRegNumber +'&assignedHubCode=' +obj.assignedHubCode);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/driverRouteAssignment', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/driverRouteAssignment/' + obj.courierId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId +'&routeId=' + obj.routeId + '&vehicleRegNumber=' + obj.vehicleRegNumber +'&assignedHubCode=' +obj.assignedHubCode, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>(
      '/overc-idmaster-service/driverRouteAssignment/' + obj.courierId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId +'&routeId=' + obj.routeId + '&vehicleRegNumber=' + obj.vehicleRegNumber +'&assignedHubCode=' +obj.assignedHubCode);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/driverRouteAssignment/' + 'find', obj);
  }

}
