import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ConsoleService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(consoleId: string) {
    return this.http.get<any>('/overc-midmile-service/console/' + consoleId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/create/list', obj);
  }
  
  CreateManual(obj: any) {
    return this.http.post<any>('/overc-midmile-service/manual/console/create', obj);
  }

  CreateFromConsignment(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/prealert', obj);
  }
  
  Update(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/console/update/list', obj);
  }

  updateSingle(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/console/update', obj);
  }

  createCCR(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/console/update/ccr/create', obj);
  }

  UpdateCCR(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/ccr/update/list', obj);
  }

  UpdateStatusforConsole(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/status-event/update', obj);
  }

  UpdateGatewayScan(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/console/update/mobileApp', obj);
  }
  
  Transfer(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/transfer', obj);
  }

  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/delete/list', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/console/findConsole', obj);
  }
  searchUnconsole(obj: any) {
    return this.http.post<any>('/overc-midmile-service/unconsolidation/find', obj);
  }

  uploadBayan(file: File, filePath:any) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>('/pdf/extract' + '?filePath=' + filePath + '&loginUserID=' + this.auth.userID, formData);
  }

  createLocation(obj: any) {
    return this.http.post<any>('/overc-midmile-service/reports/locationSheet', obj);
  }

  searchLocation(obj: any){
    return this.http.post<any>('/overc-midmile-service/reports/locationSheet', obj);
  }
}

