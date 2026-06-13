import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CostingSheetService {
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(languageId: string) {
    return this.http.get<any>('/overc-idmaster-service/language/' + languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting', obj);
  }
  UpdateCustomCosting(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/customsCosting/update/list', obj);
  }

  DeleteLine(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/delete/list', obj);
  }
  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/multiple/list', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCostingInvoice/find', obj);
  }
  searchCalculation(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/find', obj);
  }

  customsClearanceInvoice(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsClearanceInvoice/find', obj);
  }

  mergeInvoice(obj: any) {
    return this.http
      .post<any>(`/pdf/invoice/merge`, obj, {
        responseType: 'blob' as 'json',
      })
      .toPromise();
  }

  costingSheetPdf(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/findCustomsCostingPdf', obj);
  }

  CustomsCalculationBulkUpdate(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/cost/text', obj);
  }
  bulkUpdate(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/customsCosting/update/list', obj);
  }

  approveCostSheet(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsCosting/approve/batch', obj);
  }

  download(location: any): Promise<File> {
    return this.http
      .get<any>(`/doc-storage/download-all?location=${location}`, {
        responseType: 'blob' as 'json',
      })
      .toPromise();
  }
}



