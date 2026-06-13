import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';



@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(currencyId: string) {
    return this.http.get<any>('/overc-idmaster-service/currency/' + currencyId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/currency', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/currency/'+ obj.currencyId, obj);
  }

  Delete(currencyId: string) {
    return this.http.delete<any>('/overc-idmaster-service/currency/' + currencyId );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/currency/find', obj);
  }
}



