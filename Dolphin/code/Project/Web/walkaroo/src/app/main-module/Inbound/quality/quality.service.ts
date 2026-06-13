import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QualityService {

  constructor(private http: HttpClient) { }

  apiName = '/wms-mfg-service/';

  headerPath = 'inboundQualityHeader';
  linePath = 'inboundQualityLine';

  headerUrl = this.apiName + this.headerPath;
  lineUrl = this.apiName + this.linePath;


  searchHeader(obj: any) {
    return this.http.post<any>(this.headerUrl + '/findInboundQualityHeader', obj)
  }

  searchGRLines(obj: any) {
    return this.http.post<any>('/wms-spark-service/grline', obj)
  }
  searchQualityLines(obj: any) {
    return this.http.post<any>('/wms-mfg-service/inboundQualityLine/findInboundQualityLine', obj)
  }
  createLines(obj: any) {
    return this.http.post<any>(this.lineUrl + '/create', obj); // + '/v2'
  }

}
