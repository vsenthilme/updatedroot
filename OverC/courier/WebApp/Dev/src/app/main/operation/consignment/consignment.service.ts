import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class ConsignmentService {

  constructor(private http : HttpClient, private auth: AuthService) { }

  Get(languageId: string) {
    return this.http.get<any>('/overc-midmile-service/consignment/' + languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignment', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/consignment', obj);
  }
  UpdateBondedManifest(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/bondedManifest/update/list', obj);
  }

  UpdatePreAlertManifest(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/prealert/update/list', obj);
  }

  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignment/delete/list', obj);
  }

  DeletePreAlertManifest(obj: any) {
    return this.http.delete<any>('/overc-midmile-service/consignment'  +'?languageId='+ this.auth.languageId  +'&companyId='+ this.auth.companyId
      + '&masterAirwayBill='+ obj.masterAirwayBill + '&houseAirwayBill='+ obj.houseAirwayBill +'&partnerId='+obj.partnerId);
  }

  DeletePiece(obj: any) {
    return this.http.delete<any>('/overc-midmile-service/consignment'  +'?languageId='+ this.auth.languageId  +'&companyId='+ this.auth.companyId
      + '&masterAirwayBill='+ obj.masterAirwayBill + '&houseAirwayBill='+ obj.houseAirwayBill +'&partnerId='+obj.partnerId + '&pieceId='+obj.pieceId);
  }
  DeleteItem(obj: any) {
    return this.http.delete<any>('/overc-midmile-service/consignment'  +'?languageId='+ this.auth.languageId  +'&companyId='+ this.auth.companyId
      + '&masterAirwayBill='+ obj.masterAirwayBill + '&houseAirwayBill='+ obj.houseAirwayBill +'&partnerId='+obj.partnerId + '&pieceItemId='+obj.pieceItemId);
  }
  DeleteImage(obj: any) {
    return this.http.delete<any>('/overc-midmile-service/consignment'  +'?languageId='+ this.auth.languageId  +'&companyId='+ this.auth.companyId
      + '&masterAirwayBill='+ obj.masterAirwayBill + '&houseAirwayBill='+ obj.houseAirwayBill +'&partnerId='+obj.partnerId + '&imageRefId='+obj.imageRefId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignment/find', obj);
  }
  searchCassandra(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignment/find/v4', obj);
  }

  searchV4(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignment/find/v4', obj);
  }
  searchStatus(obj: any) {
    return this.http.post<any>('/overc-midmile-service/consignmentStatus/find', obj);
  }

  searchItem(obj: any) {
    return this.http.post<any>('/overc-midmile-service/itemDetails/find', obj);
  }


  searchPiece(obj: any) {
    return this.http.post<any>('/overc-midmile-service/piecedetails/find}', obj);
  }

  searchPrealert(obj: any) {
    return this.http.post<any>('/overc-midmile-service/prealert/findPrealert', obj);
  }

  uploadFiles(files: FileList, location: any) {
    const formData: FormData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i], files[i].name);
    }
    return this.http.post<any>('/doc-storage/multiUpload'+'?location='+ location , formData);
  }

  uploadFilesPdfConvert(files: FileList, location: any) {
    const formData: FormData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i], files[i].name);
    }
    return this.http.post<any>('/doc/multiUpload'+'?location='+ location , formData);
  }
  
  public uploadsinglefile(file: File, location:any) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post('/document/Upload' + '?filePath=' + location, formData);
  }

  public downloadCompressedFile(file: File): Observable<Blob> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post('/pdfcompressed/upload', formData, {
      responseType: 'blob'
    });
  }
  public uploadPDFConvert(file: File, location:any) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post('/document/Upload/V2' + '?filePath=' + location, formData);
  }

  uploadBayan(file: File, filePath:any) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>('/pdf/extract' + '?filePath=' + filePath, formData);
  }

  uploadConsignment(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`/consignment/upload/v2?companyId=${this.auth.companyId}&loginUserID=${this.auth.userID}`, formData);
  }

  download(obj:any): Promise<File> {
    console.log(obj)
  return this.http
  .get<any>(`/doc-storage/download?fileName=${obj.imageRefId}&location=${obj.referenceImageUrl}`, {
    responseType: 'blob' as 'json',
  })
  .toPromise();
 }

 findConsignmentPreAlertIndicator(obj: any) {
  return this.http.post<any>(`/overc-midmile-service/consignment/find/v2`, obj);
 }

//  pdfMerge(obj: any) {
//   return this.http.post<any>(`/pdf/merge`, obj);
//  }

 pdfMerge(obj:any): Promise<File> {
  return this.http
  .post<any>(`/pdf/merge`, obj, {
    responseType: 'blob' as 'json',
  })
  .toPromise();
 }

 // Upload Pre-Alert Manifest
 uploadPreAlertFiles(file: File, obj: any) {
  const formData = new FormData();
    formData.append('file', file);
  return this.http.post<any>(`/preAlert/upload?companyId=${obj.companyId}&estimatedTimeOfArrival=${obj.estimatedTimeOfArrival}&estimatedTimeOfDeparture=${obj.estimatedDepartureTime}&flightName=${obj.flightName}&flightNo=${obj.flightNo}&partnerId=${obj.partnerId}&partnerMasterAirwayBill=${obj.partnerMasterAirwayBill}&partnerType=${obj.partnerType}&loginUserID=${this.auth.userID}`, formData);
}
}



