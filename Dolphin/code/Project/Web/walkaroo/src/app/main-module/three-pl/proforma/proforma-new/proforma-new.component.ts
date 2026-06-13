import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from 'angular2-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table/public_api';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/Auth/auth.service';
import { BillingService } from 'src/app/main-module/Masters -1/threepl/billing/billing.service';
import { variant } from 'src/app/main-module/cycle-count/variant-analysis/variant-analysis.component';
import { MasterService } from 'src/app/shared/master.service';
import { InvoiceService } from '../../invoice/invoice.service';
import { MatDialog } from '@angular/material/dialog';
import { PricelistpopupComponent } from 'src/app/main-module/Masters -1/threepl/pricelist/pricelistpopup/pricelistpopup.component';
import { ProformaPopupComponent } from '../proforma-popup/proforma-popup.component';

export interface  variant1 {
  servicedetails:  string;
  orderno:string;
  des:  string;
  customer:string;
  qty:  string;
  itemcode:string;
  date:string;
  unit:  string;
  CBMQ:string;
  rate:  string;
  value:string;
  type:string;
  billamount:string;
} 
const ELEMENT_DATA1:  any[] = 
[
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001070",
   "sku": "0057510968",
   "description": "FRESH CONTAINER 1Ltr 3pc RECT055010",
   "transactionQty": 252,
   "transactionDate": "08-05-2024",
   "currency": "USD",
   "chargeValue": 12.6,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 80,
   "totalValue": 1008
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001070",
   "sku": "0057517341",
   "description": "CONTAINER AROMA VEGETABLES 4Ltr",
   "transactionQty": 540,
   "transactionDate": "11-05-2024",
   "currency": "USD",
   "chargeValue": 37.8,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 60,
   "totalValue": 2268
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001070",
   "sku": "005751262",
   "description": "ICE LOLLY POP Pk(1)020560\/020520\/020580",
   "transactionQty": 744,
   "transactionDate": "11-05-2024",
   "currency": "USD",
   "chargeValue": 14.88,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 80,
   "totalValue": 1190.4
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001064",
   "sku": "0057511926",
   "description": "BANANA SAVER021269\/000180",
   "transactionQty": 800,
   "transactionDate": "12-05-2024",
   "currency": "USD",
   "chargeValue": 8,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 60,
   "totalValue": 480
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001064",
   "sku": "0057517698",
   "description": "SAPONELLO SOAP DISPENSER WHT\/GREY",
   "transactionQty": 900,
   "transactionDate": "14-05-2024",
   "currency": "USD",
   "chargeValue": 27,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 80,
   "totalValue": 2160
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001064",
   "sku": "0057517055",
   "description": "LEMON KEEPER",
   "transactionQty": 900,
   "transactionDate": "18-05-2024",
   "currency": "KWD",
   "chargeValue": 18,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 80,
   "totalValue": 1440
  },
  {
   "customer": 1001,
   "module": "Inbound",
   "orderNo": "ASN000001064",
   "sku": "0057517609",
   "description": "SALAD SPINNER WASH&DRY SALAD",
   "transactionQty": 180,
   "transactionDate": "20-05-2024",
   "currency": "KWD",
   "chargeValue": 27,
   "chargeUnit": "CBM",
   "serviceType": "2-Handling charges",
   "rateUnit": 60,
   "totalValue": 1620
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057510968",
   "description": "FRESH CONTAINER 1Ltr 3pc RECT055010",
   "transactionQty": 252,
   "currency": 0.05,
   "chargeValue": 12.6,
   "chargeUnit": "KWD",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 315
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057517341",
   "description": "CONTAINER AROMA VEGETABLES 4Ltr",
   "transactionQty": 540,
   "currency": "USD",
   "chargeValue": 37.8,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 945
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "005751262",
   "description": "ICE LOLLY POP Pk(1)020560\/020520\/020580",
   "transactionQty": 744,
   "currency": "USD",
   "chargeValue": 14.88,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 372
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057511926",
   "description": "BANANA SAVER021269\/000180",
   "transactionQty": 804,
   "currency": "KWD",
   "chargeValue": 8.04,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 201
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057517698",
   "description": "SAPONELLO SOAP DISPENSER WHT\/GREY",
   "transactionQty": 900,
   "currency": "KWD",
   "chargeValue": 27,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 675
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057517055",
   "description": "LEMON KEEPER",
   "transactionQty": 900,
   "currency": "USD",
   "chargeValue": 18,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 450
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "0057517609",
   "description": "SALAD SPINNER WASH&DRY SALAD",
   "transactionQty": 180,
   "currency": "USD",
   "chargeValue": 27,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 675
  },
  {
   "customer": 1001,
   "module": "Storage",
   "sku": "005759569",
   "description": "CHESTNUT CUTTER020490\/020491\/020493",
   "transactionQty": 200,
   "currency": "USD",
   "chargeValue": 36,
   "chargeUnit": "CBM",
   "serviceType": "4-storage charges",
   "rateUnit": 25,
   "totalValue": 900
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080857",
   "sku": "020304420",
   "description": "GARBAGE BAG 45x55CM ASSTD COLOR8009",
   "transactionQty": 200,
   "currency": "USD",
   "transactionDate": "23-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 400
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080960",
   "sku": "0203013102",
   "description": "NON-WOVEN BAG SMALL",
   "transactionQty": 200,
   "currency": "USD",
   "transactionDate": "13-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 5,
   "totalValue": 1000
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080653",
   "sku": "011671276",
   "description": "TISSUE HOLDER1141",
   "transactionQty": 216,
   "currency": "USD",
   "transactionDate": "19-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 432
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-081446",
   "sku": "0203013287",
   "description": "5pc KIDS HANGER SET PINK",
   "transactionQty": 240,
   "currency": "USD",
   "transactionDate": "24-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 3,
   "totalValue": 720
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080299",
   "sku": "21300073015",
   "description": "PHILIPS BIT #2 2FRV P2\/98660",
   "transactionQty": 240,
   "currency": "USD",
   "transactionDate": "04-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 5,
   "totalValue": 1200
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-081273",
   "sku": "0203011529",
   "description": "30WET SWEEPER CLOTHES REFILLS",
   "transactionQty": 270,
   "currency": "USD",
   "transactionDate": "22-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 5,
   "totalValue": 1350
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080492",
   "sku": "0203012838",
   "description": "METAL STORAGE BOX  ROUND RED",
   "transactionQty": 300,
   "currency": "USD",
   "transactionDate": "11-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 600
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-081446",
   "sku": "020307402",
   "description": "5pc KIDS HANGER SET-BLUE",
   "transactionQty": 320,
   "currency": "USD",
   "transactionDate": "24-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 640
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-081057",
   "sku": "020304539",
   "description": "RAIN COAT 0.02MM CLRCX-1",
   "transactionQty": 300,
   "currency": "USD",
   "transactionDate": "21-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 600
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080882",
   "sku": "0203013314",
   "description": "FLY FAN",
   "transactionQty": 432,
   "currency": "USD",
   "transactionDate": "13-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 3,
   "totalValue": 1296
  },
  {
   "customer": 1001,
   "module": "Picking",
   "orderNo": "TO-080427",
   "sku": "2000394",
   "description": "1\/8PLASTIC COATED CABLE (1 Roll=75mtr)7000497",
   "transactionQty": 525,
   "currency": "USD",
   "transactionDate": "02-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "9-Picking Charges",
   "rateUnit": 2,
   "totalValue": 1050
  },
  {
   "customer": 1001,
   "module": "Packing",
   "orderNo": "TO-081273",
   "sku": "0203011529",
   "description": "30WET SWEEPER CLOTHES REFILLS",
   "transactionQty": 270,
   "currency": "USD",
   "transactionDate": "22-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "10-Packing Charges",
   "rateUnit": 4,
   "totalValue": 1080
  },
  {
   "customer": 1001,
   "module": "Packing",
   "orderNo": "TO-080492",
   "sku": "0203012838",
   "description": "METAL STORAGE BOX  ROUND RED",
   "transactionQty": 300,
   "currency": "USD",
   "transactionDate": "11-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "10-Packing Charges",
   "rateUnit": 3,
   "totalValue": 900
  },
  {
   "customer": 1001,
   "module": "Packing",
   "orderNo": "TO-081446",
   "sku": "020307402",
   "description": "5pc KIDS HANGER SET-BLUE",
   "transactionQty": 320,
   "transactionDate": "24-05-2024",
   "chargeUnit": "QTY",
   "currency": "USD",
   "serviceType": "10-Packing Charges",
   "rateUnit": 2,
   "totalValue": 640
  },
  {
   "customer": 1001,
   "module": "Packing",
   "orderNo": "TO-081057",
   "sku": "020304539",
   "description": "RAIN COAT 0.02MM CLRCX-1",
   "transactionQty": 300,
   "currency": "USD",
   "transactionDate": "21-05-2024",
   "chargeUnit": "QTY",
   "serviceType": "10-Packing Charges",
   "rateUnit": 4,
   "totalValue": 1200
  },
 
 ]


@Component({
  selector: 'app-proforma-new',
  templateUrl: './proforma-new.component.html',
  styleUrls: ['./proforma-new.component.scss']
})
export class ProformaNewComponent implements OnInit {
  advanceFilterShow: boolean;
  @ViewChild('Setupproforma') Setupproforma: Table | undefined;
  transfer: any;
  selectedproforma : any;
  
  form = this.fb.group({
    billQuantity: [],
  billUnit: [],
  companyCodeId: [],
  deletionIndicator: [],
  description: [],
  invoiceDate: [],
  languageId: [],
  lineNumber: [],
  partnerCode: [],
  plantId: [],
  priceUnit: [],
  proformaBillAmountLine: [],
  proformaBillNo: [],
  referenceField1: [],
  referenceField10: [],
  referenceField2: [],
  referenceField3: [],
  referenceField4: [],
  referenceField5: [],
  referenceField6: [],
  referenceField7: [],
  referenceField8: [],
  referenceField9: [],
  statusId: [],
  billmode:[],
  warehouseId: [],
  startCreatedOnFE:[],
  endCreatedOnFE:[],
  billdatefrom:[],
  billdateto:[],
  billfrequnecy:[],
  
   billamount:[],
  });
  
  isShowDiv = false;
  table = true;
  fullscreen = false;
  search = true;
  step = 0;
  back = false;
  div1Function() {
    this.table = !this.table;
  }
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  toggleFloat() {

    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;

  }
  showFiller = false;
  //displayedColumns: string[] = [ 'yz', 'number','servicedetails','des','qty','unit','rate','billamount'];
  sub = new Subscription();
 
  constructor(
    private route: ActivatedRoute, private router: Router, public toastr: ToastrService, private spin: NgxSpinnerService,private fb: FormBuilder,private cs: CommonService, private masterService: BillingService, public auth: AuthService,private service:InvoiceService, public dialog: MatDialog,
    ) { }
  animal: string | undefined;
  saveElement:any;
  js: any = {};
  businesspartnerList:any[]=[];
  ngOnInit(): void {
   


    this.masterService.search({ warehouseId:this.auth.warehouseId,companyCodeId:this.auth.companyId,plantId:this.auth.plantId,languageId:[this.auth.languageId] }).subscribe(res => {
      this.businesspartnerList = [];
     res.forEach(element => {
       this.businesspartnerList.push({value: element.partnerCode, label: element.referenceField9,billModeId:element.billModeId,billFrequencyId:element.billFrequencyId,billFrequencyIdAndDescription:element.billFrequencyIdAndDescription,billModeIdAndDescription:element.billModeIdAndDescription});
    })
   });
  
  //   if(this.js.code){
  //   setTimeout(() => {
  //     this.js.code.filter(item => item.refDocNumber).forEach(item => {
  //       this.saveElement=item;
  //       ELEMENT_DATA1.unshift({
  //         customer: '1001',
  //         servicedetails: 'GR',
  //         orderno: item.refDocNumber,
  //         itemcode: item.itemCode,
  //         des: item.description,
  //         qty: item.orderQty,
  //         unit: 'QTY',
  //         date: '26-05-2024',
  //         CBMQ:'',
  //         value: '',
  //         type: '2-Handling Charges',
  //         rate: '$2',
  //         billamount: '$202',
  //       });
  //     });
  //     this.proforma = ELEMENT_DATA1;
     
  //     this.spin.hide();
  //   }, 500);
  //  }
  }

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }

  }
 
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator; // Pagination
  // Pagination
  fill(){
    this.service.GetProfomaHeader(this.js.code.proformaBillNo,this.auth.companyId,this.auth.languageId,this.js.code.partnerCode,this.auth.warehouseId,this.auth.plantId).subscribe(res => {
    this.service.GetProfomaLine(this.js.code.proformaBillNo,this.auth.companyId,this.auth.languageId,this.js.code.partnerCode,this.auth.warehouseId,this.auth.plantId).subscribe(result => {
    this.form.patchValue(res, { emitEvent: false });
   this.transfer=(result);
    this.selectedproforma=this.transfer;
    
    })
   })
  }

addLine(){
  const dialogRef = this.dialog.open(ProformaPopupComponent, {
    disableClose: true,
    width: '55%',
    maxWidth: '80%',
   data: this.form.controls.partnerCode.value,
  });

  dialogRef.afterClosed().subscribe(result => {
    this.spin.show();
    console.log(result)
    if(result){
    if (this.showresult == true){
      
      ELEMENT_DATA1.unshift(result); // Push the result to the top of ELEMENT_DATA1
      this.transfer = ELEMENT_DATA1.slice();
      this.selectedproforma = this.transfer;  
      console.log(this.transfer);
      console.log(this.selectedproforma.length);
    }
  }
  this.spin.hide();
  });
}
  togglesearch() {
    this.search = false;
    this.table = true;
    this.fullscreen = false;
    this.back = true;
  }
  backsearch() {
    this.table = true;
    this.search = true;
    this.fullscreen = true;
    this.back = false;
  }
  onpartnertytpeChange(value) {
    console.log(value); 
   this.form.controls.billfrequnecy.patchValue(value.billFrequencyIdAndDescription);
   this.form.controls.billmode.patchValue(value.billModeIdAndDescription);
   //this.form.controls.billFrequencyId.patchValue(value.billFrequencyId);
}
total1:number =0;
showresult:any;
filtersearch(){
  if (this.form.controls.partnerCode.value ==null) {
    this.toastr.error("Kindly select any Customer", "Notification", {
      timeOut: 2000,
      progressBar: false,
    });
    return;
  }
  else{
 
     let obj: any = {};
     obj.companyCodeId = this.auth.companyId;
     obj.plantId = this.auth.plantId;
     obj.languageId =this.auth.languageId;
         obj.warehouseId = this.auth.warehouseId;
         obj.startCreatedOn=(this.cs.dateNewFormat1(this.form.controls.startCreatedOnFE.value));
   obj.endCreatedOn=(this.cs.dateNewFormat1(this.form.controls.endCreatedOnFE.value));
   obj.partnerCode=this.form.controls.partnerCode.value;
    this.sub.add(this.service.searchreport(obj).subscribe((res: any[]) => {
      this.transfer=res;
 this.selectedproforma=this.transfer;
 this.showresult=true;
 let total=0;
this.transfer.forEach(x =>{
   total = total + (x.totalValue != null ? x.totalValue : 0)
 })
 this.total1=total;

    }))
}
// else{
//   this.transfer=ELEMENT_DATA1;
//   this.selectedproforma=this.transfer;
//   let total = 0;
//   ELEMENT_DATA1.forEach(x => {
//     total = total + (x.totalValue != null ? x.totalValue : 0)
//   })
//  this.total1=total;
//   this.showresult=true;

//   }
}

  reset() {
    this.form.reset();
  }
  setStep(index: number) {
    this.step = index;
  }
 
  onChange() {
    console.log(this.selectedproforma.length)
    const choosen= this.selectedproforma[this.selectedproforma.length - 1];   
    this.selectedproforma.length = 0;
    this.selectedproforma.push(choosen);
  } 
 
  proformaLines:any[]=[];
  proformaHeaderId:any;
Submit(){
  console.log(this.selectedproforma)
  this.proformaLines=[];
     let lineno=0;
     let billqty=0;
     let totalValue1=0;
        this.selectedproforma.forEach((x: any) => {
            lineno=lineno+1
            billqty=billqty+(x.transactionQty);
            totalValue1=totalValue1+(x.totalValue);
            this.form.controls.billamount.patchValue(totalValue1)
          this.proformaLines.push({
            billQuantity: x.totalValue,
           billUnit:x.chargeUnit,
           companyCodeId:this.auth.companyId,
           warehouseId:this.auth.warehouseId,
           languageId:this.auth.languageId,
           plantId:this.auth.plantId,
          lineNumber:lineno,
          priceUnit:x.rateUnit,
          referenceField1:x.module,
          referenceField2:x.orderNo,
          referenceField3:x.sku,
          referenceField4:x.serviceType,
          referenceField5:x.description,
          referenceField6:x.chargeValue,
          referenceField7:x.cbmQty,
          referenceField8:x.transactionDate,
          referenceField10:x.currency,
         // invoiceDate:this.cs.dateNewFormat(x.transactionDate),
          partnerCode:this.form.controls.partnerCode.value,
          });
        });
       
      let  proformaheader: any = {};
      proformaheader.billDateFrom=(this.form.controls.startCreatedOnFE.value),
      proformaheader.billDateTo=(this.form.controls.endCreatedOnFE.value),
        proformaheader.companyCodeId=(this.auth.companyId),
        proformaheader.warehouseId=(this.auth.warehouseId),
        proformaheader.languageId=(this.auth.languageId),
        proformaheader.plantId=(this.auth.plantId),
        proformaheader.billQuantity=(totalValue1),
        proformaheader.proformaBillAmount=(this.form.controls.billamount.value),
        proformaheader.statusId=(1),
       // invoiceDate:this.cs.dateNewFormat(x.transactionDate),
       proformaheader.partnerCode=(this.form.controls.partnerCode.value),
        console.log(this.proformaLines)
        this.sub.add(this.service.CreateProformaheader(proformaheader).subscribe(res => {
          this.proformaLines.forEach(line => {
            line.proformaHeaderId = res.proformaBillNo;
        });
         this.sub.add(this.service.CreateProformaLine(this.proformaLines).subscribe(result => {
          console.log(result)
           this.toastr.success(res[0].proformaBillNo + " Saved Successfully!","Notification",{
             timeOut: 2000,
             progressBar: false,
           });
         }));
         this.router.navigate(['/main/threePL/profoma/']);
           this.spin.hide();
      
         }, err => {
           this.cs.commonerrorNew(err);
           this.spin.hide();
      
         }));
       }
 //let paramdata = "";
 //paramdata = this.cs.encrypt({ pageflow: "Invoice", code: this.saveElement});
 // this.router.navigate(['/main/threePL/profoma/' + paramdata]);
// this.toastr.success(this.saveElement.refDocNumber + "Proforma Invoice Saved Successfully.", "", {
        
//   timeOut: 2000,
//   progressBar: false,
// })

}




