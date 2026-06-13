import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  constructor(private http: HttpClient) { }

  processFind(obj:any, apiName:any){
    return this.http.post(`/wms-mfg-service/${apiName}/`, obj);
  }
}
