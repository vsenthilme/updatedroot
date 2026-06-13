import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError, of, forkJoin } from 'rxjs';
import { AuthService } from '../core/core';
import { CommonServiceService } from './common-service.service';
import { MessageService } from 'primeng/api';

export interface dropdownelement {
  key: any;
  value: any;
  referenceField?: any;
}

export interface dropdownelement1 {
  value: any;
  label: any;
  referenceField?: any;
  value2?: any;
}
export interface dropdownelement2 {
  key: any;
  value: any;
  referenceField?: any;
  languageId?: any;
  companyId?: any;

}
export interface dropdownelement3 {
  value: any;
  label: any;
  languageId?: any;
  companyId?: any;
}

@Injectable({
  providedIn: 'root'
})
export class CommonAPIService {

  constructor(private http: HttpClient, private cs: CommonServiceService, private auth: AuthService, private spin: NgxSpinnerService, public messageService: MessageService) { }

  setup = '/overc-idmaster-service/';
  dropdownlist: any = {
    setup: {
      language:{url:this.setup + 'language',key:{key:'languageId',value:'languageDescription'}},
      company:{url:this.setup + 'company',key:{key:'companyId',value:'companyName'}},
      country:{url:this.setup + 'country',key:{key:'countryId',value:'countryName', languageId :'languageId', companyId: 'companyId'}},
      currency:{url:this.setup + 'currency',key:{key:'currencyId',value:'currencyDescription'}},
      province:{url:this.setup + 'province',key:{key:'provinceId',value:'provinceName', languageId :'languageId', companyId: 'companyId'}},
      menu:{url:this.setup + 'menu', key:{key:'menuId',value:'menuName', languageId :'languageId', companyId: 'companyId'}},
      subMenu:{url:this.setup + 'menu', key:{key:'subMenuId',value:'subMenuName', languageId :'languageId', companyId: 'companyId'}},
      specialApproval:{url:this.setup + 'specialApproval', key:{key:'specialApprovalId',value:'specialApprovalText', languageId :'languageId', companyId: 'companyId'}},
      authorizationObject:{url:this.setup + 'menu', key:{key:'authorizationObjectId',value:'authorizationObjectValue', languageId :'languageId', companyId: 'companyId'}},
      district:{url:this.setup + 'district',key:{key:'districtId',value:'districtName', languageId :'languageId', companyId: 'companyId'}},
      subProduct: { url: this.setup + 'subProduct', key: { key: 'subProductId', value: 'subProductName', languageId: 'languageId', companyId: 'companyId' } },
      product: { url: this.setup + 'product', key: { key: 'productId', value: 'productName', languageId: 'languageId', companyId: 'companyId' } },
      customer: { url: this.setup + 'customer', key: { key: 'customerId', value: 'customerName', languageId: 'languageId', companyId: 'companyId' } },
      city: { url: this.setup + 'city', key: { key: 'cityId', value: 'cityName', languageId: 'languageId', companyId: 'companyId' } },
      serviceType: { url: this.setup + 'serviceType', key: { key: 'serviceTypeId', value: 'serviceTypeText', languageId: 'languageId', companyId: 'companyId' } },
      loadType : { url: this.setup + 'loadType', key: { key: 'loadTypeId', value: 'loadTypeText', languageId: 'languageId', companyId: 'companyId' } },
      consignmentType: { url: this.setup + 'consignmentType', key: { key: 'consignmentTypeId', value: 'consignmentTypeText', languageId: 'languageId', companyId: 'companyId' } },
      opStatus: { url: this.setup + 'opStatus', key: { key: 'statusCode', value: 'opStatusDescription', languageId: 'languageId', companyId: 'companyId' } },
      rateParameter: { url: this.setup + 'rateParameter', key: { key: 'rateParameterId', value: 'rateParameterDescription', languageId: 'languageId', companyId: 'companyId' } },
      hub: { url: this.setup + 'hub', key: { key: 'hubCode', value: 'hubName', languageId: 'languageId', companyId: 'companyId' } },
      consignor: { url: this.setup + 'consignor', key: { key: 'consignorId', value: 'consignorName', languageId: 'languageId', companyId: 'companyId' } },
      iata: { url: this.setup + 'iata', key: { key: 'iataKd', value: 'iataKd', languageId: 'languageId', companyId: 'companyId' } },
      hsCode: { url: this.setup + 'hsCode', key: { key: 'hsCode', value: 'hsCode', languageId: 'languageId', companyId: 'companyId' } },
      status: { url: this.setup + 'status', key: { key: 'statusId', value: 'statusDescription', languageId: 'languageId' } },
      event: { url: this.setup + 'event', key: { key: 'eventCode', value: 'eventDescription', languageId: 'languageId', companyId: 'companyId'  } },
      airportOrigin:{url:this.setup + 'airportCode',key:{key:'airportCode',value:'airportText', languageId :'languageId', companyId: 'companyId'}},
      statusevent:{url:this.setup + 'statusevent',key:{key:'typeId',value:'typeId', languageId :'languageId', companyId: 'companyId'}},
      route:{url:this.setup + 'route',key:{key:'routeId',value:'routeName', languageId :'languageId', companyId: 'companyId'}},
      vehicle:{url:this.setup + 'vehicle',key:{key:'vehicleRegNumber',value:'vehicleName', languageId :'languageId', companyId: 'companyId'}},
      zoneMaster:{url:this.setup + 'zonemaster',key:{key:'zoneId',value:'zoneText', languageId :'languageId', companyId: 'companyId'}},
      zoneTypeMaster:{url:this.setup + 'zoneTypeMaster',key:{key:'zoneTypeId',value:'zoneTypeId', languageId :'languageId', companyId: 'companyId'}},

    },
  }
 

  getalldropdownlist(url: string[]) {
    let observableBatch: any[] = [];
    url.forEach((url: string) => { observableBatch.push(this.http.get<any>(url).pipe(catchError(err => of(err)))) });
    return forkJoin(observableBatch);
  }

  foreachlist(list: any, val: { key: any, value: any, value2?: any}, _filter: any = {}, addblank: boolean = false,) {
    let dropdownlist: dropdownelement[] = [];
    let dropdownlist1: dropdownelement1[] = [];
    let filter = list;
    if (_filter)
      filter = this.cs.filterArray(list, _filter);
    if (addblank)
      dropdownlist.push({ key: '', value: '' });
    for (const l of filter) {
      let filter2 = this.cs.filterArray(dropdownlist, { key: l[val.key] })
      if (filter2.length == 0)
      dropdownlist1.push({ value: l[val.key], label: l[val.key] + ' - ' + l[val.value], value2: l[val.value2] });
    }
    dropdownlist1 = this.cs.removeDuplicatesFromArrayList(dropdownlist1, 'value');
    return dropdownlist1.sort((a, b) => (Number(a.value) > Number(b.value)) ? 1 : -1);
  }

  foreachlistWithoutKey(list: any, val: { key: any, value: any }, _filter: any = {}, addblank: boolean = false,) {
    let dropdownlist: dropdownelement[] = [];
    let dropdownlist1: dropdownelement1[] = [];
    let filter = list;
    if (_filter)
      filter = this.cs.filterArray(list, _filter);
    if (addblank)
      dropdownlist.push({ key: '', value: '' });
    for (const l of filter) {
      let filter2 = this.cs.filterArray(dropdownlist, { key: l[val.key] })
      if (filter2.length == 0)
      dropdownlist1.push({ value: l[val.key], label: l[val.value] });
    }
    dropdownlist1 = this.cs.removeDuplicatesFromArrayList(dropdownlist1, 'value');
    return dropdownlist1.sort((a, b) => (Number(a.value) > Number(b.value)) ? 1 : -1);
  }

  
  forLanguageFilter(list: any, val: { key: any, value: any, languageId: any, companyId: any }, _filter: any = {}, addblank: boolean = false,) {
    let dropdownlist: dropdownelement2[] = [];
    let dropdownlist1: dropdownelement3[] = [];
    let dropdownlist2: dropdownelement3[] = [];
    let filter = list;
    if (_filter)
      filter = this.cs.filterArray(list, _filter);
    if (addblank)
      dropdownlist.push({ key: '', value: '', languageId: '', companyId: '' });
    for (const l of filter) {
      let filter2 = this.cs.filterArray(dropdownlist, { key: l[val.key] })
      if (filter2.length == 0)
      dropdownlist1.push({ value: l[val.key], label: l[val.key] + ' - ' + l[val.value], languageId: l[val.languageId], companyId: l[val.companyId]});
    }
    dropdownlist1.forEach(x=> {
      if(this.auth.languageId == x.languageId && this.auth.companyId == x.companyId){
        dropdownlist2.push(x)
      }
    })
    dropdownlist2 = this.cs.removeDuplicatesFromArrayList(dropdownlist2, 'value');
    return dropdownlist2.sort((a, b) => (Number(a.value) > Number(b.value)) ? 1 : -1);
  }

  forLanguageFilterWithoutKey(list: any, val: { key: any, value: any, languageId: any, companyId: any }, _filter: any = {}, addblank: boolean = false,) {
    let dropdownlist: dropdownelement2[] = [];
    let dropdownlist1: dropdownelement3[] = [];
    let dropdownlist2: dropdownelement3[] = [];
    let filter = list;
    if (_filter)
      filter = this.cs.filterArray(list, _filter);
    if (addblank)
      dropdownlist.push({ key: '', value: '', languageId: '', companyId: '' });
    for (const l of filter) {
      let filter2 = this.cs.filterArray(dropdownlist, { key: l[val.key] })
      if (filter2.length == 0)
      dropdownlist1.push({ value: l[val.key], label: l[val.value], languageId: l[val.languageId], companyId: l[val.companyId]});
    }
    dropdownlist1.forEach(x=> {
      if(this.auth.languageId == x.languageId && this.auth.companyId == x.companyId){
        dropdownlist2.push(x)
      }
    })
    dropdownlist2 = this.cs.removeDuplicatesFromArrayList(dropdownlist2, 'value');
    return dropdownlist2.sort((a, b) => (Number(a.value) > Number(b.value)) ? 1 : -1);
  }
}
