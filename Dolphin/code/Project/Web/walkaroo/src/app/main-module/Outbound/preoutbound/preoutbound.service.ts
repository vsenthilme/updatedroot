import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from "rxjs/operators";
import { AuthService } from 'src/app/core/core';
@Injectable({
  providedIn: 'root'
})
export class PreoutboundService {

  constructor(private http: HttpClient,private auth: AuthService) { }

  apiName = '/wms-transaction-service/';
  methodName = 'preoutboundheader';
  url = this.apiName + this.methodName;

  search(obj: any) {
    return this.http.post<any>(this.url + '/findPreOutboundHeader', obj);
  }
  searchv2(obj: any) {
    return this.http.post<any>(this.url + '/v2/findPreOutboundHeader', obj);
  }
  searchSpark(obj: any) {
    return this.http.post<any>('/wms-spark-service/preoutboundheader', obj);
  }
  searchNew1(obj: any) {
    return this.http.post<any>(this.url + '/findPreOutboundHeader/v2', obj);
  }
  searchNew(obj: any) {
    return this.http.post<any>(this.url + '/findPreOutboundHeaderNew', obj);
  }
  searchspeed(obj: any) {
    return this.http.post<any>(this.url + '/findPreOutboundHeader', obj).toPromise();
  }
  searchLine(obj: any) {
    return this.http.post<any>(this.apiName + 'preoutboundline/findPreOutboundLine', obj);
  }
  searchLinev2(obj: any) {
    return this.http.post<any>(this.apiName + 'preoutboundline/v2/findPreOutboundLine', obj);
  }
  searchLineSpark(obj: any) {
    return this.http.post<any>( '/wms-spark-service/preoutboundline', obj);
  }
  GetStoreCode() {
    return this.http.get<any>('/wms-masters-service/businesspartner');
  }

  createShipmentOrder(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/so', obj);
  }
  createretrunpo(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/returnpo/v2', obj);
  }
  createOUTBOUNDSO(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/v2/outbound/so', obj);
  }
  createShipmentOrderV2(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/salesorder/v2', obj);
  }
  
  createinterwarehouse(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/interwarehousetransferout/v2', obj);
  }
  createsalesinvoice(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/salesinvoice', obj);
  }
  createsalesorder(obj: any) {
    return this.http.post<any>('/wms-transaction-service/warehouse/outbound/salesorder/v2', obj);
  }



  updateOutBoundLine(lineNumber: any,itemCode:any,partnerCode: any,preOutboundNo:any,refDocNumber:any,warehouseId: any, obj: any) {
    return this.http.patch<any>('/wms-transaction-service/outboundline/'+ lineNumber + '?itemCode=' + itemCode +  '&partnerCode=' + partnerCode +  '&preOutboundNo=' + preOutboundNo +  '&refDocNumber=' + refDocNumber  +  '&warehouseId=' + warehouseId  ,obj);
  }
  searchOutboundHeader(obj: any) {
    return this.http.post<any>('/wms-transaction-service/findOutboundOrderV2', obj);
  }

    ////// Upload API for Inbound
    public outboundUpload(file: File, apiName:any) {
      let formParams = new FormData();
      formParams.append('file', file)
      return this.http.post(`/wms-transaction-service/warehouse/outbound/${apiName}/upload/v3`+ '?languageId='+this.auth.languageId+'&companyCodeId='+this.auth.companyId+'&plantId='+this.auth.plantId+'&warehouseId='+this.auth.warehouseId+'&loginUserID='+this.auth.userID , formParams)
    }
    download(fileName:any,location:any): Promise<File> {
      return this.http
        .get<any>(`/document/download`+'?fileName='+fileName+'&location='+location, {
          responseType: 'blob' as 'json',
        })
        .toPromise();
    }
}
