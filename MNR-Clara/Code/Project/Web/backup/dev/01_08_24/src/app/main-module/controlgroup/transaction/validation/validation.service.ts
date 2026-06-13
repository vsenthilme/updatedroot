import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor(private http: HttpClient) { }

  searchStoreMatch(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findMatchResult', obj);
  }
  findGroup(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findGroup/count', obj);
  }
  findGroupNew(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findStore/entity', obj);
  }
  findstore(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findStorePartnerListing', obj);
  }
   findGroupbackup(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findGroup', obj);
  }
  Update(obj: any, code: any,languageId:any,companyId:any,versionNumber:any) {
    return this.http.patch<any>('/mnr-cg-transaction-service/' + `storepartnerlisting/`  + code +'?languageId='+languageId+'&companyId='+companyId+'&versionNumber='+versionNumber, obj);
  }
}
