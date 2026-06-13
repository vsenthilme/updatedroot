import { SelectionModel } from "@angular/cdk/collections";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { Router } from "@angular/router";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { DeleteComponent } from "src/app/common-field/delete/delete.component";
import { CommonService, dropdownelement1 } from "src/app/common-service/common-service.service";
import { walkaroo} from "../../../../../assets/font/walkaroo.js";
import { AuthService } from "src/app/core/core";
import { PickingService } from "../picking.service";
import { UpdatePickerComponent } from "./update-picker/update-picker.component";
import { Table } from "primeng/table";
import { DatePipe } from '@angular/common';
import pdfMake from "pdfmake/build/pdfmake";

import JsBarcode from 'jsbarcode';
import { PickingDialogComponent } from "../picking-dialog/picking-dialog.component";
export interface ordermanagement {
  no: string;
  lineno: string;
  partner: string;
  product: string;
  description: string;
  refdocno: string;
  variant: string;
  order: string;
  type: string;
  preoutboundno: string;
  uom: string;
  req: string;
  allocated: string;
  status: string;
  actions: string;
  sname: string;
  scode: string;
  reallocated: string;
}
interface PickingData {
  languageId: string;
  companyCodeId: string;
  plantId: string;
  warehouseId: string;
  preOutboundNo: string;
  refDocNumber: string;
  partnerCode: string;
  pickupNumber: string;
  lineNumber: number;
  itemCode: string;
  proposedStorageBin: string;
  proposedPackBarCode: string;
  outboundOrderTypeId: number;
  pickToQty: number;
  pickUom: string;
  stockTypeId: number;
  specialStockIndicatorId: number;
  manufacturerPartNo: string | null;
  statusId: number;
  assignedPickerId: string;
  referenceField1: string;
  referenceField2: string | null;
  referenceField3: string | null;
  referenceField4: string | null;
  referenceField5: string | null;
  referenceField6: string | null;
  referenceField7: string;
  referenceField8: string | null;
  referenceField9: string | null;
  referenceField10: string | null;
  deletionIndicator: number;
  remarks: string | null;
  pickupCreatedBy: string;
  pickupCreatedOn: string;
  pickConfimedBy: string | null;
  pickUpdatedBy: string | null;
  pickUpdatedOn: string;
  pickupReversedBy: string | null;
  pickupReversedOn: string;
  inventoryQuantity: number | null;
  manufacturerCode: string;
  manufacturerName: string;
  origin: string | null;
  brand: string | null;
  barcodeId: string;
  levelId: string | null;
  companyDescription: string;
  plantDescription: string;
  warehouseDescription: string;
  statusDescription: string;
  middlewareId: string | null;
  middlewareTable: string | null;
  referenceDocumentType: string;
  salesOrderNumber: string;
  tokenNumber: string | null;
  targetBranchCode: string | null;
  fromBranchCode: string | null;
  isCompleted: boolean | null;
  isCancelled: boolean | null;
  batchSerialNumber: string | null;
  customerId: string;
  customerName: string;
  materialNo: string;
  priceSegment: string;
  articleNo: string;
  gender: string;
  color: string;
  size: string;
  noPairs: string;
  mupdatedOn: string | null;
}
const ELEMENT_DATA: ordermanagement[] = [
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },
  { no: "readonly", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', sname: 'readonly', scode: 'readonly', reallocated: 'readonly', },

];
@Component({
  selector: 'app-picking-main',
  templateUrl: './picking-main.component.html',
  styleUrls: ['./picking-main.component.scss']
})
export class PickingMainComponent implements OnInit {
  screenid=3063;
  picking: any[] = [];
  selectedpicker : any[] = [];
  @ViewChild('pickingTag') pickingTag: Table | any;
 
  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  storecodeList: any;
  toggleFloat() {
    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;
    ;
  }

  

  title1 = "Outbound";
  title2 = "Order Management";
  constructor(private service: PickingService,
    public datePipe: DatePipe,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    //this.getstorename()
    this.search(true);

  }

  warehouseId = this.auth.warehouseId
 
  searhform = this.fb.group({
    actualHeNo: [],
    companyCodeId:[[this.auth.companyId]],
    plantId:[[this.auth.plantId]],
    languageId:[[this.auth.languageId]],
    itemCode: [],
    lineNumber: [],
    partnerCode: [],
    pickedPackCode: [],
    pickedStorageBin: [],
    pickupNumber: [],
    preOutboundNo: [],
    refDocNumber: [],
    warehouseId: [[this.auth.warehouseId]],
    statusId: [[48]],
  });





  dropdownSettings = {
    singleSelection: false, 
    text:"Select",
    selectAllText:'Select All',
    unSelectAllText:'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2
  };
  itemCodeListselected: any[] = [];
  itemCodeList: any[] = [];

  lineNumberListselected: any[] = [];
  lineNumberList: any[] = [];

  partnerCodeListselected: any[] = [];
  partnerCodeList: any[] = [];

  pickedStorageBinselected: any[] = [];
  pickedStorageBinList: any[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: any[] = [];

  pickupNumberListselected: any[] = [];
  pickupNumberList: any[] = [];

  preOutboundNoListselected: any[] = [];
  preOutboundNoList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];

  // getstorename(){
  //   this.spin.show();
  //   this.sub.add(this.service.GetStoreCode().subscribe(res => {
  //     this.storecodeList = res;
      
  //     this.spin.hide();
  //   },
  //   err => {
  //     this.cs.commonerrorNew(err);;
  //     this.spin.hide();
  //   }));
  // }


  search(ispageload = false) {
    if (!ispageload) {

     

    }
    //this.searhform.controls.statusId.value.push(48);
    this.service.searchv2(this.searhform.value).subscribe(res => {
     
        

  
      if (ispageload) {
        this.spin.show();
        let tempitemCodeList: any[] = []
        const itemCode = [...new Set(res.map(item => item.itemCode))].filter(x => x != null)
        itemCode.forEach(x => tempitemCodeList.push({ value: x, label: x }));
        this.itemCodeList = tempitemCodeList;

        let templineNumberList: any[] = []
        const lineNumber = [...new Set(res.map(item => item.lineNumber))].filter(x => x != null)
        lineNumber.forEach(x => templineNumberList.push({ value: x, label: x }));
        this.lineNumberList = templineNumberList;

        let temppartnerCodeList: any[] = []
        const partnerCode = [...new Set(res.map(item => item.partnerCode))].filter(x => x != null)
        partnerCode.forEach(x => temppartnerCodeList.push({ value: x, label: x }));
        this.partnerCodeList = temppartnerCodeList;

        let temppreOutboundNoList: any[] = []
        const preOutboundNo = [...new Set(res.map(item => item.preOutboundNo))].filter(x => x != null)
        preOutboundNo.forEach(x => temppreOutboundNoList.push({ value: x, label: x }));
        this.preOutboundNoList = temppreOutboundNoList;

        let temprefDocNumberList: any[] = []
        const refDocNumber = [...new Set(res.map(item => item.refDocNumber))].filter(x => x != null)
        refDocNumber.forEach(x => temprefDocNumberList.push({ value: x, label: x }));
        this.refDocNumberList = temprefDocNumberList;

        let temppickedStorageBinList: any[] = []
        const pickedStorageBin = [...new Set(res.map(item => item.pickedStorageBin))].filter(x => x != null)
        pickedStorageBin.forEach(x => temppickedStorageBinList.push({ value: x, label: x }));
        this.pickedStorageBinList = temppickedStorageBinList;


        let temppickupNumberList: any[] = []
        const pickupNumber = [...new Set(res.map(item => item.pickupNumber))].filter(x => x != null)
        pickupNumber.forEach(x => temppickupNumberList.push({ value: x, label: x }));
        this.pickupNumberList = temppickupNumberList;

      }
      res = this.cs.removeDuplicatesFromArrayList(res, 'pickupNumber');
      this.picking = res;
   
      this.spin.hide();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    });


  }
  onChange() {
    const choosen= this.selectedpicker[this.selectedpicker.length - 1];   
    this.selectedpicker.length = 0;
    this.selectedpicker.push(choosen);
  }
 
  deleteDialog() {
    if (this.selectedpicker.length === 0) {
      this.toastr.error("Kindly select any row", "Norification");
      return;
    }
    if (this.selectedpicker[0].statusId != 48) {
      this.toastr.error("Confirmed items can't be deleted", "Norification");
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%', },
      // data: this.selection.selected[0],
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result) {
        this.deleterecord(this.selectedpicker[0]);

      }
    });
  }
  deleterecord(obj: any) {
    this.spin.show();
    this.sub.add(this.service.deletePickupHeaderv2(obj).subscribe((ress) => {
      this.toastr.success(obj.pickupNumber + " Deleted successfully.", "Notification",{
        timeOut: 2000,
        progressBar: false,
      });
     this.search(true);
     this.spin.hide(); 
    }, err => {
      this.cs.commonerrorNew(err);;
      this.spin.hide();
    }));
  }
  
  reload() {
    this.searhform.reset();
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
  }
  openDialog(data: any = 'new'): void {
    if (data != 'New')
      if (this.selectedpicker.length === 0) {
        this.toastr.error("Kindly select any row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }

    let paramdata = "";

    if (this.selectedpicker.length > 0) {
      paramdata = this.cs.encrypt({ code: this.selectedpicker[0], pageflow: data });
      this.router.navigate(['/main/outbound/pickup-confirm/' + paramdata]);
    }
    else {
      paramdata = this.cs.encrypt({ pageflow: data });
      this.router.navigate(['/main/outbound/pickup-confirm/' + paramdata]);
    }
  }

  openConfirm(data: any) {

    if(data.statusId != 48){
      this.toastr.error("Picking Process of this Particular Order is Completed", "Norification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    let paramdata = this.cs.encrypt({ code: data, pageflow: 'Edit' });
    this.router.navigate(['/main/outbound/pickup-confirm/' + paramdata]);

  }

  generateBarcode(text: string) {
    const canvas = document.createElement('canvas');
    JsBarcode(canvas, text, { height: 80 });
    return canvas.toDataURL('image/png');
  }

  pickList(pickup) {
    let dd: any = {
      pageSize: 'A4',
      pageOrientation: 'portrait',
      alignment: 'center',
      pageMargins: [20, 10, 20, 10],
      styles: {
        anotherStyle: {
          bordercolor: '#6102D3',
        },
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [
          {
            text: 'Page ' + currentPage + ' of ' + pageCount,
            style: 'header',
            alignment: 'center',
            bold: true,
            fontSize: 6,
          },
        ];
      },
      content: ['\n'],
    };
    
    let createdOn = this.datePipe.transform(new Date(), 'dd-MM-yyyy');
 
  
    let obj: any = {};
    obj.pickupNumber = [pickup.pickupNumber];
    obj.companyCodeId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.plantId = [this.auth.plantId];
    this.sub.add(
      this.service.searchv2(obj).subscribe((res: PickingData[]) => {
        let groupedResults = res.reduce((acc: { [key: string]: PickingData }, curr: PickingData) => {
          let key = curr.refDocNumber + '_' + curr.itemCode + '_' + curr.proposedStorageBin;
          
          if (!acc[key]) {
            acc[key] = { 
              ...curr 
            }; 
          } else {
            // Add the quantity to the existing grouped entry
            acc[key].pickToQty += curr.pickToQty;
          }
          
          return acc;
        }, {});
      console.log(groupedResults)
        // Convert the grouped results back into an array
        let result = Object.values(groupedResults); 
        let  resultSorted = result.sort((a, b) => (a.proposedStorageBin > b.proposedStorageBin) ? 1 : -1)
        console.log(resultSorted)
      // let resultnew=result;
        if (resultSorted.length > 0) {
          let confirmedOn = this.datePipe.transform(
            resultSorted[0].pickupCreatedOn,
            'dd-MMM-yyyy HH:mm',
            'GMT'
          );
    
          // Creating the layout with equal width columns
          dd.content.push(
            {
              columns: [
                {
                  // Left column with logo
                  width: '35%',
                  image: walkaroo.headerLogo, 
                  fit: [120, 120],  // Adjust the size of the logo
                  alignment: 'left',
                  margin: [0, -15, 0, 0]
                },
                {
                  // Center column for spacing (empty)
                  width: '45%',
                  stack: [
                    {
                      text: 'Walkaroo International Pvt Ltd',
                      alignment: 'center',
                      bold: true,
                      fontSize: 14,
                      margin: [-80,-10, 0, 0]
                    },
                    {
                      text: 'Plot No 10-12 SIDCO Industrial Estate,',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-80, 0, 0, 5]
                    },
                    {
                      text: 'Malumichampatti Coimbatore 641050',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-80, -3, 0, 5]
                    },
                    {
                      text: 'Tamil Nadu, India',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-80, -3, 0, 5]
                    },
                    {
                      text: 'Pickup List',
                      alignment: 'center',
                      bold: true,
                      fontSize: 18,
                      margin: [-80, 2, 0, 5]
                    }
                  ]
                },
                {
                  // Right column with PutList No., Date, and Barcode
                  width: '20%',
                  stack: [
                    {
                      text: 'Picklist No:  '+'' + resultSorted[0].pickupNumber,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -10, 0, 0]
                    },
                    {
                      text: 'Customer:'+'' + resultSorted[0].customerId+'-'+resultSorted[0].customerName,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, 0, 0, 5]
                    },
                    {
                      text: 'Date: '+'' + confirmedOn,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -4, 0, 5]
                    },
                    {
                      // Barcode image
                      image: this.generateBarcode(resultSorted[0].pickupNumber),
                      fit: [100, 100],  // Adjust the size of the barcode
                      alignment: 'left',
                      margin: [0, 0, 0, 0]
                    }
                  ]
                }
              ]
            },
          )
      //       // Adding the horizontal line before the table
      // dd.content.push({
      //   canvas: [
      //     {
      //       type: 'line',
      //       x1: 0, y1: 0,
      //       x2: 550, y2: 0, 
      //       lineWidth: 1.5,
      //       lineColor: '#808080', 
      //     },
      //   ],
      //   margin: [-10, '*', 0, 10], // Adjust the margin if needed
      // });


  
    let bodyArray68: any[] = [];
    bodyArray68.push([
      { text: 'SNo ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Storage Bin ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Order No ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'SKUCode ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Material ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      // { text: 'HU Serial No ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Price Segement ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Article Number ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Gender ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Color ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Size ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'No of Pairs ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Qty ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [17, 37, 37, 90, 60, 37, 40, 27, 23,22,30,20],
          body: bodyArray68,
        },
        margin: [0, 0, 0, 14]
      }, 
    )
    for(let i=0;i<resultSorted.length;i++){
    let bodyArray69: any[] = [];
    bodyArray69.push([
      { text:(i+1) , bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].proposedStorageBin, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].refDocNumber, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].itemCode, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].materialNo, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] ,   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0],},
      // { text: resultSorted[i].barcodeId, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].priceSegment, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].articleNo, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0],},
      { text: resultSorted[i].gender, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] ,   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0],},
      { text: resultSorted[i].color, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].size, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] ,   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0],},
      { text: resultSorted[i].noPairs, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'],   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0], },
      { text: resultSorted[i].pickToQty, bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] ,   margin: [0, 0, 0, 0], padding: [0, 0, 0, 0],},

    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [17, 37, 37, 90, 60, 37, 40, 27, 23,22,30,20],
          body: bodyArray69,
        },
        margin: [0, -14, 0, 0]
      }, '\n'
    )
  }
}
else{
   // Creating the layout with equal width columns
   dd.content.push(
    {
      columns: [
        {
          // Left column with logo
          width: '35%',
          image: walkaroo.headerLogo, 
          fit: [120, 120],  // Adjust the size of the logo
          alignment: 'left',
          margin: [0, -15, 0, 0]
        },
        {
          // Center column for spacing (empty)
          width: '45%',
          stack: [
            {
              text: 'Walkaroo International Pvt Ltd',
              alignment: 'center',
              bold: true,
              fontSize: 14,
              margin: [-80, -10, 0, 0]
            },
            {
              text: 'Plot No 10-12 SIDCO Industrial Estate,',
              alignment: 'center',
              bold: true,
              fontSize: 9,
              margin: [-80, 0, 0, 5]
            },
            {
              text: 'Malumichampatti Coimbatore 641050',
              alignment: 'center',
              bold: true,
              fontSize: 9,
              margin: [-80, -3, 0, 5]
            },
            {
              text: 'Tamil Nadu India',
              alignment: 'center',
              bold: true,
              fontSize: 9,
              margin: [-80, -3, 0, 5]
            },
            {
              text: 'Pickup List',
              alignment: 'center',
              bold: true,
              fontSize: 14,
              margin: [-80, -3, 0, 5]
            }
          ]
        },
        {
          // Right column with PutList No., Date, and Barcode
          width: '20%',
          stack: [
            {
              text: 'Picklist No:  '+'' ,
              alignment: 'left',
              bold: true,
              fontSize: 9,
              margin: [0, -10, 0, 0]
            },
            {
              text: 'User:'+'' ,
              alignment: 'left',
              bold: true,
              fontSize: 9,
              margin: [0, 0, 0, 5]
            },
            {
              text: 'Date: '+'' ,
              alignment: 'left',
              bold: true,
              fontSize: 9,
              margin: [0, -4, 0, 5]
            },
            {
              // Barcode image
             text:'',
              fit: [100, 100],  // Adjust the size of the barcode
              alignment: 'left',
              margin: [0, 0, 0, 0]
            }
          ]
        }
      ]
    },
  )
//     // Adding the horizontal line before the table
// dd.content.push({
// canvas: [
//   {
//     type: 'line',
//     x1: 0, y1: 0,
//     x2: 550, y2: 0, 
//     lineWidth: 1.5,
//     lineColor: '#808080', 
//   },
// ],
// margin: [-10, -40, 0, 10], // Adjust the margin if needed
// });


  let bodyArray68: any[] = [];
  bodyArray68.push([
    { text: 'SNo ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Storage Bin ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Order No ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'SKUCode ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Material ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    //{ text: 'HU Serial No ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Price Segement ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Article Number ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Gender ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Color ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Size ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'No of Pairs ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: 'Qty ', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

  ]);
  dd.content.push(
    {
      table: {
        headerRows: 1,
        widths: [17, 37, 37, 90, 60, 37, 40, 27, 23,22,30,20],
        body: bodyArray68,
      },
    }, 
  )
  let bodyArray69: any[] = [];
  bodyArray69.push([
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    //{ text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    { text: '', bold: true, fontSize: 8, alignment: 'left', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
   

  ]);
  dd.content.push(
    {
      table: {
        headerRows: 1,
        widths: [17, 37, 37, 90, 60, 37, 40, 27, 23,22,30,20],
        body: bodyArray69,
      },
    }, '\n'
  )
}
pdfMake.createPdf(dd).download('Pickup Report : ' +  pickup.refDocNumber);
pdfMake.createPdf(dd).open(); pdfMake.createPdf(dd).open();
      }))
      }

 

  
 


  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.picking.forEach(x => {
      res.push({
        "Branch":x.plantDescription,
        "Warehouse":x.warehouseDescription,
        "Sales Order No":x.salesOrderNumber,
        "Customer Code":x.customerId,
        "Customer Name":x.customerName,
        "Status": x.statusDescription,
        'Order No': x.refDocNumber,
        "Order Type": x.referenceDocumentType,
        "Line No": x.lineNumber,
        "Mfr Name":x.manufacturerName,
        "Part No": x.itemCode,
        "Article":x.articleNo,
        "Gender":x.gender,
        "Price Segment":x.priceSegment,
        "Color":x.color,
        "Size":x.size,
        "Material No":x.materialNo,
        "No of Pairs":x.noPairs,
        "HU SNo":x.barcodeId,
        "Bin Location": x.proposedStorageBin,
        "Picker": x.assignedPickerId,
        "Order Qty": x.pickToQty,
        "Created Date":this.cs.dateapiwithTime(x.pickupCreatedOn),
        "Level":x.levelId,
        "Order Category": x.referenceField1,
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Picking");
  }
  onItemSelect(item: any) {
    console.log(item);
  }
  
  OnItemDeSelect(item:any){
    console.log(item);
}
onSelectAll(items: any){
    console.log(items);
}
onDeSelectAll(items: any){
    console.log(items);
}

form = this.fb.group({
  assignedPickerId: [,],
});

updatePicker(Line: any){
  if (this.auth.warehouseId != 100) {
    let dataErrorArray: any = [];
    this.selectedpicker.forEach(data => {
      if (data.levelId != this.selectedpicker[0].levelId) {
        dataErrorArray.push(data.lineNumber);
      }
    });

    if (dataErrorArray.length > 0) {
      this.toastr.error(
        "The selected records have different level Id",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.cs.notifyOther(true);
      return;
    }
  }
  const dialogRef = this.dialog.open(UpdatePickerComponent, {
    disableClose: true,
    width: '100%',
    maxWidth: '55%',
    data: { levelID: Line.levelId, }
  });
  dialogRef.afterClosed().subscribe(result => {
    if(result){
    let data: any[] = [];
      data.push({
        itemCode: Line.itemCode,
        lineNumber: Line.lineNumber,
        partnerCode: Line.partnerCode,
        preOutboundNo: Line.preOutboundNo,
        pickupNumber: Line.pickupNumber,
        refDocNumber: Line.refDocNumber,
        warehouseId: Line.warehouseId,
      })
    this.form.controls.assignedPickerId.patchValue(result)
    console.log(data)
    this.spin.show();
    this.sub.add(this.service.updatePickerv2(this.form.getRawValue(),data[0].pickupNumber, data[0].itemCode, data[0].lineNumber, data[0].partnerCode, data[0].preOutboundNo,  data[0].refDocNumber ,this.auth.warehouseId,this.auth.companyId,this.auth.plantId,this.auth.languageId).subscribe(res => {
      this.spin.hide();
      this.toastr.success(result + " updated successfully.", "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      this.search(true);

    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    }));
  }
  });
}


multipleReassign(){
  if (this.selectedpicker.length == 0) {
    this.toastr.error("Kindly select any row", "Notification", {
      timeOut: 2000,
      progressBar: false,
    });
    return;
  }
 
  const dialogRef = this.dialog.open(UpdatePickerComponent, {
    disableClose: true,
    width: '100%',
    maxWidth: '55%',
  });
  dialogRef.afterClosed().subscribe(result => {
if(result){
  let objArray: any[] = [];
  this.selectedpicker.forEach((x: any) => {
    let obj: any = {};
    obj.itemCode = x.itemCode;
    obj.lineNumber = x.lineNumber;
    obj.partnerCode = x.partnerCode;
    obj.preOutboundNo = x.preOutboundNo;
    obj.proposedPackCode = x.proposedPackBarCode;
    obj.proposedStorageBin = x.proposedStorageBin;
    obj.refDocNumber = x.refDocNumber;
    obj.warehouseId = x.warehouseId;
    objArray.push(obj);
  });

  if (objArray.length > 0) {
    this.spin.show();
    this.selectedpicker.forEach((x )=> {
      x.assignedPickerId = result;
    })
    this.spin.show();
    
    this.sub.add(this.service.multipleUpdatePickerv2(this.selectedpicker).subscribe(res => {
      this.spin.hide();
      this.toastr.success(" Reassigned successfully!", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.search(true);
    }, err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);;
    }));
  }

}
  
});
  
}



dialogPicking(pick) {
  const dialogRef = this.dialog.open(PickingDialogComponent, {
    disableClose: true,
    width: '68%',
    maxWidth: '80%',
    position: { top: '9%', },
    data: { code: pick, }
  });

  dialogRef.afterClosed().subscribe(result => {
    this.search();
  });
}

}
