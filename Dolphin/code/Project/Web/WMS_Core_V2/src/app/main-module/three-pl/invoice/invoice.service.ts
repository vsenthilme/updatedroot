import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  constructor(private http: HttpClient, private auth: AuthService) { }

  searchHeader(obj: any) {
    return this.http.post<any>('/wms-transaction-service/invoiceHeader/findInvoiceHeader', obj);
  }
  searchProformaHeader(obj: any) {
    return this.http.post<any>('/wms-transaction-service/proformainvoiceheader/find', obj);
  }


  DeleteHeader(line: any) {
    return this.http.delete<any>(`/wms-transaction-service/invoiceHeader/` + line.invoiceNumber + '?warehouseId=' + this.auth.warehouseId + '&languageId=' + this.auth.languageId + '&plantId=' + this.auth.plantId + '&companyCodeId=' + this.auth.companyId
      + '&partnerCode=' + line.partnerCode);
  }
  DeleteProformaHeader(line: any) {
    return this.http.delete<any>(`/wms-transaction-service/proformainvoiceheader/` + line.proformaBillNo + '?warehouseId=' + this.auth.warehouseId + '&languageId=' + this.auth.languageId + '&plantId=' + this.auth.plantId + '&companyCodeId=' + this.auth.companyId
      + '&partnerCode=' + line.partnerCode);
  }

  CreateHeader(obj) {
    return this.http.post<any>('/wms-transaction-service/invoiceHeader', obj);
  }

  CreateLine(obj) {
    return this.http.post<any>('/wms-transaction-service/invoiceLine', obj);
  }
  CreateProformaheader(obj) {
    return this.http.post<any>('/wms-transaction-service/proformainvoiceheader', obj);
  }
  CreateProformaLine(obj) {
    return this.http.post<any>('/wms-transaction-service/proformainvoiceline', obj);
  }

  GetHeader(line: any) {
    return this.http.get<any>('/wms-transaction-service/invoiceHeader/' + line.invoiceNumber + '?companyCodeId=' + line.companyCodeId + '&languageId=' + line.languageId + '&plantId=' + line.plantId + '&warehouseId=' + line.warehouseId + '&partnerCode=' + line.partnerCode);
  }

  GetLine (line: any) {
    return this.http.get<any>('/wms-transaction-service/invoiceLine/' + line.invoiceNumber + '?companyCodeId=' + line.companyCodeId + '&languageId=' + line.languageId + '&plantId=' + line.plantId + '&warehouseId=' + line.warehouseId + '&partnerCode=' + line.partnerCode);
  }


  GetProfomaHeader(code: any, companyCodeId: any, languageId: any, partnerCode: any, warehouseId: any,plantId:any) {
    return this.http.get<any>('/wms-transaction-service/proformainvoiceheader/' + code + '?companyCodeId=' + companyCodeId + '&languageId=' + languageId + '&partnerCode=' + partnerCode + '&warehouseId=' + warehouseId + '&plantId=' + plantId);
  }

  GetProfomaLine (code: any, companyCodeId: any, languageId: any, partnerCode: any, warehouseId: any,plantId:any) {
    return this.http.get<any>('/wms-transaction-service/proformainvoiceline/' + code + '?companyCodeId=' + companyCodeId + '&languageId=' + languageId + '&partnerCode=' + partnerCode + '&warehouseId=' + warehouseId + '&plantId=' + plantId);
  }
  searchProformaline(obj: any) {
    return this.http.post<any>('/wms-transaction-service/proformainvoiceline/find', obj);
  }
  searchinvoiceline(obj: any) {
    return this.http.post<any>('/wms-transaction-service/invoiceLine/findInvoiceLine', obj);
  }
  searchreport(obj: any) {
    return this.http.post<any>('/wms-transaction-service/reports/billingTransactionReport', obj);
  }
}

