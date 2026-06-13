import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';


export interface BOMElement {
  bomNumber: number;
  companyCode: string;
  createdOn: Date;
  createdby: string;
  deletionIndicator: number;
  languageId: string;
  parentItemCode: string;
  childItemCode: string;
  childItemQuantity: string;
  parentItemQuantity: number;
  plantId: string;
  referenceField1: string;
  referenceField10: string;
  referenceField2: string;
  referenceField3: string;
  referenceField4: string;
  referenceField5: string;
  referenceField6: string;
  referenceField7: string;
  referenceField8: string;
  referenceField9: string;
  statusId: number;
  updatedOn: Date;
  updatedby: string;
  warehouseId: string;
}


@Injectable({
  providedIn: 'root'
})
export class BOMService {


  constructor(private http: HttpClient, private auth: AuthService) { }

  apiName = '/wms-masters-service/';
  methodName = 'bomheader';
  url = this.apiName + this.methodName;

  Getall() {
    return this.http.get<any>(this.apiName + `bomheader`);
  }
  Get(parentItemCode: any, warehouseId: any,plantId:any,companyCode:any,languageId:any) {
    return this.http.get<any>(this.apiName + `bomheader/` + parentItemCode + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCode='+companyCode+'&languageId='+languageId);
  }
  Create(obj: BOMElement) {
    return this.http.post<any>(this.apiName + `bomheader`, obj);
  }
  CreateMasterOperations(obj: any) {
    return this.http.post<any>('/wms-mfg-service/masterOperation/create', obj);
  }
  CreateMasterReceipe(obj: any) {
    return this.http.post<any>('/wms-mfg-service/masterReceipe/create', obj);
  }
  Update(obj: BOMElement, parentItemCode: any,  warehouseId: any,plantId:any,companyCode:any,languageId:any) {
    return this.http.patch<any>(this.apiName + `bomheader/` + '?parentItemCode=' + parentItemCode + '&warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCode='+companyCode+'&languageId='+languageId, obj);
  }
  UpdateMasterOperation(obj: any,  warehouseId: any,plantId:any,companyCodeId:any,languageId:any) {
    return this.http.patch<any>( `/wms-mfg-service/masterOperation/update` + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCodeId='+companyCodeId+'&languageId='+languageId, obj);
  }
  UpdateMasterReceipe(obj: any,  warehouseId: any,plantId:any,companyCodeId:any,languageId:any) {
    return this.http.patch<any>( `/wms-mfg-service/masterReceipe/update` + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCodeId='+companyCodeId+'&languageId='+languageId, obj);
  }
  Delete(parentItemCode: any, warehouseId: any,plantId:any,companyCode:any,languageId:any) {
    return this.http.delete<any>(this.apiName + `bomheader/` + parentItemCode + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCode='+companyCode+'&languageId='+languageId);
  }
  DeleteMasterOperations(operationNumber: any, warehouseId: any,plantId:any,companyCodeId:any,languageId:any) {
    return this.http.delete<any>( `/wms-mfg-service/masterOperation/` + operationNumber + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCodeId='+companyCodeId+'&languageId='+languageId);
  }
  DeleteMasterreceipe(receipeId: any, warehouseId: any,plantId:any,companyCodeId:any,languageId:any,operationNumber:any) {
    return this.http.delete<any>( `/wms-mfg-service/masterReceipe/` + receipeId + '?warehouseId=' + warehouseId+'&plantId='+plantId+'&companyCodeId='+companyCodeId+'&languageId='+languageId+'&operationNumber='+operationNumber);
  }

  Getitemcode(obj: any = { warehouseId: [this.auth.warehouseId] }) {
    return this.http.post<any>('/wms-masters-service/imbasicdata1/findImBasicData1', obj);
  }


  search(obj: any) {
    return this.http.post<any>(this.url + '/findBomHeader', obj);
  }
  searchMasterReceipe(obj: any) {
    return this.http.post<any>('/wms-mfg-service/masterReceipe/findMasterReceipe', obj);
  }
  searchMasterOperation(obj: any) {
    return this.http.post<any>('/wms-mfg-service/masterOperation/findMasterOperation', obj);
  }

  GetallProcess() {
    return this.http.get<any>('/wms-mfg-service/masterPhase');
}
  GetProcess(code: string) {
    return this.http.get<any>('/wms-mfg-service/masterPhase/' + code + '?warehouseId=' + this.auth.warehouseId +'&plantId='+ this.auth.plantId + 
      '&companyCodeId='+ this.auth.companyId +'&languageId='+ this.auth.languageId);
  }
  CreateProcess(obj: any) {
    return this.http.post<any>('/wms-mfg-service/masterPhase/create/', obj);
  }
  UpdateProcess(obj: any) {
    return this.http.patch<any>('/wms-mfg-service/masterPhase/update/' +  '?warehouseId=' + this.auth.warehouseId +'&plantId='+ this.auth.plantId + 
      '&companyCodeId='+ this.auth.companyId +'&languageId='+ this.auth.languageId, obj);
  }
  DeleteProcess(code: string) {
    return this.http.delete<any>('/wms-mfg-service/masterPhase/' + code + '?warehouseId=' +  this.auth.warehouseId +
      '&plantId='+  this.auth.plantId +  '&companyCodeId='+ this.auth.companyId +'&languageId='+ this.auth.languageId );
}
searchProcess(obj: any) {
  return this.http.post<any>('/wms-mfg-service/masterPhase/findMasterPhase', obj);
}
}



