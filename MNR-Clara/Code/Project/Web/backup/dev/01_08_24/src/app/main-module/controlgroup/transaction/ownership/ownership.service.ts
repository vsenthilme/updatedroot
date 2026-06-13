import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OwnershipService {
  constructor(private http: HttpClient) { }
  apiName = '/mnr-cg-transaction-service/';

  Getall() {
    return this.http.get<any>(this.apiName + `ownershiprequest`);
  }
  Get(code: string,languageId:any,companyId:any) {
    return this.http.get<any>(this.apiName + `ownershiprequest/` + code+'?languageId='+languageId+'&companyId='+companyId);
  }
  Create(obj: any) {
    return this.http.post<any>(this.apiName + `ownershiprequest`, obj);
  }
  Update(obj: any, code: any,languageId:any,companyId:any) {
    return this.http.patch<any>(this.apiName + `ownershiprequest/`  + code +'?languageId='+languageId+'&companyId='+companyId, obj);
  }
  Delete(code: any,languageId:any,companyId:any) {
    return this.http.delete<any>(this.apiName + `ownershiprequest/` + code+'?languageId='+languageId+'&companyId='+companyId);
  }
  search(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/ownershiprequest/findownershiprequest', obj);
  }
  searchStoreMatch(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findMatchResult', obj);
  }
  searchStoreMatchNew(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findBrotherSister', obj);
  }
    templateMatchResult(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findResponse', obj);
  }
  templateFormat(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findMatchResultResponse', obj);
  }
  searchStorePartner(obj:any){
    return this.http.post<any>('/mnr-cg-transaction-service/storepartnerlisting/findStorePartnerListing', obj);
  }



      //uploadInvoiceQuotation
      public uploadfile(file: File, location) {
        let formParams = new FormData();
        formParams.append('file', file)
        return this.http.post('/doc-storage/upload?' + `location=${location}`, formParams)
      }


      // public uploadfile(file: any, location) {
      //   let formParams = new FormData();
      //   for (var i = 0; i < file.length; i++) {
      //     formParams.append('files', file[i]);
      //     console.log(formParams);
      //   }
      //   return this.http.post('/doc-storage/multiupload', formParams)
      // }

        download(obj): Promise<File> {
          return this.http
            .post<any>(`/sharepoint/download`,obj, {
              responseType: 'blob' as 'json',
            })
            .toPromise();
        }


      sendMailClient(obj) {
        return this.http.post<any>('/mnr-cg-setup-service/sendFormThroEmail', obj);
      }

      sendEmailWithAttachment(obj, fileName) {
        return this.http.post<any>('/mnr-cg-setup-service/sendEmailWithAttachment?' + `file=${fileName}`, obj);
      }





      
      Getall1() {
        return this.http.get<any>(this.apiName + `requestId`);
      }
      GetNew(storeId: string) {
        return this.http.get<any>(this.apiName + `requestId/store/` + storeId);
      }
      Get1(requestId: string) {
        return this.http.get<any>(this.apiName + `requestId/` + requestId);
      }
      Create1(obj: any) {
        return this.http.post<any>(this.apiName + `requestId`, obj);
      }
      Update1(obj: any, code: any) {
        return this.http.patch<any>(this.apiName + `requestId/store`  + code, obj);
      }
      Update2(obj: any, code: any) {
        return this.http.patch<any>(this.apiName + `requestId/`  + code, obj);
      }
      Delete1(code: any,id:any,) {
        return this.http.delete<any>(this.apiName + `requestId/store/` + code +'?id='+id);
      }
      Delete2(code: any,id:any,) {
        return this.http.delete<any>(this.apiName + `requestId/ ` + code +'?id='+id);
      }
      search1(obj:any){
        return this.http.post<any>('/mnr-cg-transaction-service/requestId/findRequestId', obj);
      }
}
