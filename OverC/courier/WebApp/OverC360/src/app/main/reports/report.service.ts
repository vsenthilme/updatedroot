import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  apiName = '/overc-midmile-service/reports';

  search(obj: any) {
    return this.http.post<any>(this.apiName + '/consoleTrackingReport',obj);
  }
 customssearch() {
  return this.http.get<any>('/overc-midmile-service/customsCalculation/report');
 }
 executeCostSheet(obj: any) {
  return this.http.post<any>('/overc-midmile-service/prealert/findPrealert', obj);
}
executeExpenseSheet(obj: any) {
  return this.http.post<any>('/overc-midmile-service/prealert/findPrealert', obj);
}
}
