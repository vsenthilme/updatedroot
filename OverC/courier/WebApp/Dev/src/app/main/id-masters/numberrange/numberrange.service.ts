import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class NumberrangeService {

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  Get(obj: any) {
    return this.http.get<any>('/overc-idmaster-service/numberRange' + obj.numberRangeCode + '?languageId=' + obj.languageId + '&numberRangeObject=' + obj.numberRangeObject);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/numberRange', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/numberRange/' + obj.numberRangeCode + '?languageId=' + this.auth.languageId + '&numberRangeObject=' + obj.numberRangeObject, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/numberRange/' + obj.numberRangeCode + '?languageId=' + this.auth.languageId + '&numberRangeObject=' + obj.numberRangeObject);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/numberRange/find', obj);
  }

}
