import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(languageId: string) {
    return this.http.get<any>('/overc-idmaster-service/language/' + languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/language', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/language/'+ obj.languageId, obj);
  }

  Delete(languageId: string) {
    return this.http.delete<any>('/overc-idmaster-service/language/' + languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/language/find', obj);
  }
}


