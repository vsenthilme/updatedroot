import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class ProductionService {
  
  constructor(private http: HttpClient, private auth: AuthService) { }
  
  apiName = '/wms-mfg-service/';
  methodName = 'operationHeader';
  url = this.apiName + this.methodName;

  
  
    Get(obj: any) {
      return this.http.get<any>('/wms-mfg-service/productionOrder/v2/' + obj.productionOrderNo +'?languageId='+ this.auth.languageId 
        +'&plantId='+ this.auth.plantId  +'&companyCodeId='+ this.auth.companyId +'&warehouseId='+ this.auth.warehouseId +'&batchNumber='+ obj.batchNumber);
    }

    getConfirm(obj: any) {
      return this.http.get<any>('/wms-mfg-service/productionOrder/orderConfirmationGet' +'?languageId='+ this.auth.languageId 
        +'&plantId='+ this.auth.plantId  +'&companyCodeId='+ this.auth.companyId +'&warehouseId='+ this.auth.warehouseId  +'&productionOrderNo='+ obj.productionOrderNo +'&batchNumber='+ obj.batchNumber);
    }

    confirmProdcution(obj: any) {
      return this.http.post<any>('/wms-mfg-service/productionOrder/orderConfirmationConfirm', obj);
    }
    createSFG(obj: any) {
      return this.http.post<any>('/wms-mfg-service/productionOrder/sfgCreate', obj);
    }
    Create(obj: any) {
      return this.http.post<any>('/wms-mfg-service/productionOrder/create', obj);
    }
  
    Update(obj: any) {
      return this.http.patch<any>('/wms-mfg-service/productionOrder/update' +'?languageId='+ this.auth.languageId   + '&warehouseId='+ this.auth.warehouseId
        + '&plantId='+ this.auth.plantId +'&companyCodeId='+ this.auth.companyId, obj);
    }

    search(obj: any) {
      return this.http.post<any>('/wms-mfg-service/productionOrder/findOperationHeader', obj);
    }
    searchLine(obj: any) {
      return this.http.post<any>('/wms-mfg-service/operationLine/findOperationLine', obj);
    }
    Delete(obj: any, warehouseId: any, plantId: any, companyCodeId: any, languageId: any){
      return this.http.delete<any>('/wms-mfg-service/operationHeader/' +obj.productionOrderNo + '?languageId='+ this.auth.languageId 
        +'&companyId='+ this.auth.companyId + '&plantId='+this.auth.plantId +  '&warehouseId='+this.auth.warehouseId)
    }

}
