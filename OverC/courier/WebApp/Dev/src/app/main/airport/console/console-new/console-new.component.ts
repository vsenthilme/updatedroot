import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ConsoleService } from '../console.service';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { IataService } from '../../../master/iata/iata.service';
import { CurrencyService } from '../../../id-masters/currency/currency.service';

@Component({
  selector: 'app-console-new',
  templateUrl: './console-new.component.html',
  styleUrl: './console-new.component.scss'
})
export class ConsoleNewComponent {

  
  
  active: number | undefined = 0;
  status:any[] = []
  constructor(private cs: CommonServiceService, private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router, private path: PathNameService, private fb: FormBuilder,
    private service: ConsoleService, private messageService: MessageService,private cas: CommonAPIService, 
    private auth: AuthService,private consignmentService: ConsignmentService,private currencyService: CurrencyService,
    private iataService: IataService) {     
      this.status = [
        { value: '17', label: 'Inactive' },
        { value: '16', label: 'Active' }
      ];}

    
  pageToken: any;

  //form builder initialize
  form = this.fb.group({
      actualCurrency:[],
      actualValue: [],
      airportOriginCode: [],
      bondedId: [],
      consoleId: [],
      languageId: [this.auth.languageId],
      companyName: [],
      consigneeCivilId: [],
      consigneeName: [],
      consignmentCurrency: [],
      consignmentValue: [],
      countryOfOrigin: [],
      createdBy: [],
      pieceId: [],
      pieceItemId: [],
      createdOn: ['', ],
      currency: [],
      customsCurrency: [],
      customsKd: [],
      customsValue: [],
      declaredValue: [],
      deletionIndicator: [],
      description: [],
      expectedDuty: [],
      finalDestination: [],
      freightCharges: [],
      freightCurrency: [],
      goodsDescription: [],
      goodsType: [],
      grossWeight: [],
      houseAirwayBill: [, Validators.required],
      hsCode: [],
      iata: [],
      incoTerms: [],
      invoiceDate: [],
      invoiceNumber: [],
      invoiceSupplierName: [],
      invoiceType: [],
      isConsolidatedShipment: [],
      isPendingShipment: [],
      isSplitBillOfLading: [],
      landedQuantity: [],
      languageDescription: [],
      companyId: [this.auth.companyId],
      manifestedGrossWeight: [],
      manifestedQuantity: [],
      masterAirwayBill: [, Validators.required],
      netWeight: [],
      noOfPackageMawb: [],
      noOfPieceHawb: [],
      notifyParty: [],
      partnerHouseAirwayBill: [],
      partnerId: [],
      partnerMasterAirwayBill: [],
      partnerName: [],
      partnerType: [],
      productId: [],
      productName: [],
      quantity: [],
      referenceField1: [],
      referenceField10: [],
      referenceField11: [],
      referenceField12: [],
      referenceField13: [],
      referenceField14: [],
      referenceField15: [],
      referenceField16: [],
      referenceField17: [],
      referenceField18: [],
      referenceField19: [],
      referenceField2: [],
      referenceField20: [],
      referenceField3: [],
      referenceField4: [],
      referenceField5: [],
      referenceField6: [],
      referenceField7: [],
      referenceField8: [],
      referenceField9: [],
      remarks: [],
      serviceTypeId: [],
      serviceTypeName: [],
      shipperId: [],
      shipperName: [],
      specialApprovalValue: [],
      statusId: ["16",],
      subProductId: [],
      subProductName: [],
      tareWeight: [],
      totalQuantity: [],
      updatedBy: [],
      updatedOn: ['', ],
      volume: [],
  
  });

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = "required") {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }


  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Airport ', 'Console', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();
    this.mawbDropdown();


    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.masterAirwayBill.disable();
      this.form.controls.houseAirwayBill.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  countryIdList: any[] =[];
  mawbList: any[] = [];
  hawbList: any[] = [];
  iataList: any[] = [];
  originCodeList: any[] =[];
  currencyIdList: any[] = [];
  consignorIdList: any[] = [];
  



  dropdownlist(){
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.iata.url,


    ]).subscribe({next: (results: any) => {
      this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
      this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
      this.currencyIdList = this.cas.foreachlist(results[2], this.cas.dropdownlist.setup.currency.key);
      this.countryIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.country.key);
      this.consignorIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.consignor.key);
      this.iataList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.iata.key);

      this.spin.hide();
    },
    error: (err: any) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    },
  });

  }
  fill(line: any) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }

  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: ' Please fill required fields to continue' });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show()
      this.service.updateSingle([this.form.getRawValue()]).subscribe({
        next: (res: any) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res[0].consoleId + ' has been updated successfully' });
          this.router.navigate(['/main/airport/console']);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show()
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
        if(res){
          this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res[0].consoleId + ' has been created successfully' });
          this.router.navigate(['/main/airport/console']);
          this.spin.hide();
        }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }
  
  mawbDropdown(){

    let obj: any = {};
    obj.companyId = [this.auth.companyId];

    this.mawbList = [];
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
    this.mawbList = this.cas.foreachlist(result, {key: 'masterAirwayBill', value: 'masterAirwayBill'});
    this.hawbList = this.cas.foreachlist(result, {key: 'houseAirwayBill', value: 'houseAirwayBill'});
    this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

  mawbChanged(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.masterAirwayBill = [this.form.controls.masterAirwayBill.value]

    this.hawbList = [];
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
      this.hawbList = this.cas.foreachlist(result, {key: 'houseAirwayBill', value: 'houseAirwayBill'});
      this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

  hawbChanged(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.masterAirwayBill = [this.form.controls.masterAirwayBill.value]
    obj.houseAirwayBill = [this.form.controls.houseAirwayBill.value]
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
      this.form.patchValue(result[0]);
      this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

}




