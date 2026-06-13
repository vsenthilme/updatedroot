import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/core';

@Injectable({
  providedIn: 'root'
})
export class ConfirmService {

 
  constructor(private http: HttpClient, private auth: AuthService) { }



  getConfirm(obj: any) {
    return this.http.get<any>('/wms-mfg-service/productionOrder/blackDalGet' +'?languageId='+ this.auth.languageId 
      +'&plantId='+ this.auth.plantId  +'&companyCodeId='+ this.auth.companyId +'&warehouseId='+ this.auth.warehouseId +'&batchNumber='+ obj.batchNumber);
  }
}
