import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor(private http: HttpClient, private auth: AuthService){}

  Create(obj: any) {
    return this.http.post<any>('/overc-midmile-service/invoiceHeader', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-midmile-service/invoiceHeader/update', obj);
  }

  Delete(obj: any) {
    return this.http.post<any>('/overc-midmile-service/invoiceHeader/delete', obj);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-midmile-service/invoiceHeader/find', obj);
  }
  
  executeInvoiceReport(obj: any) {
    return this.http.post<any>('/overc-midmile-service/customsClearanceInvoiceReport/find', obj);
  }

}
