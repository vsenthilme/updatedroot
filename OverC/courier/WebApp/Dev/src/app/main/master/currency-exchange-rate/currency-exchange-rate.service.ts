import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CurrencyExchangeRateService {

  
  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(fromCurrencyId: string) {
    return this.http.get<any>('/overc-idmaster-service/currencyExchangeRate/' + fromCurrencyId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/currencyExchangeRate', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/currencyExchangeRate/'+ obj.fromCurrencyId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&toCurrencyId='+ obj.toCurrencyId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/currencyExchangeRate/' + obj.fromCurrencyId+'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&toCurrencyId='+ obj.toCurrencyId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/currencyExchangeRate/find', obj);
  }
}

