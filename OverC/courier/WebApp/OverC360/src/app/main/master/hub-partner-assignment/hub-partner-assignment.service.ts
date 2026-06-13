import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class HubPartnerAssignmentService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(partnerId: string) {
    return this.http.get<any>('/overc-idmaster-service/partnerHubMapping/' + partnerId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/partnerHubMapping', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/partnerHubMapping/'+ obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&hubCode='+ obj.hubCode +'&partnerType='+ obj.partnerType, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/partnerHubMapping/' + obj.partnerId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId +'&hubCode='+ obj.hubCode +'&partnerType='+ obj.partnerType );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/partnerHubMapping/find', obj);
  }
}
