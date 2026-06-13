import { SelectionModel } from "@angular/cdk/collections";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { CommonService, dropdownelement1,} from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { AssignPickerComponent } from "../../assignment/assignment-main/assign-picker/assign-picker.component";
import { ReversalOutboundPopupComponent } from "../reversal-outbound-popup/reversal-outbound-popup.component";
import { ReversalOutboundService } from "../reversal-outbound.service";
import { Table } from "primeng/table";

export interface reversal {
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

}

const ELEMENT_DATA: reversal[] = [
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },
  { no: "Value", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', actions: 'readonly', },

];
@Component({
  selector: 'app-reversaloutbound-main',
  templateUrl: './reversaloutbound-main.component.html',
  styleUrls: ['./reversaloutbound-main.component.scss']
})
export class ReversaloutboundMainComponent implements OnInit {
  screenid= 3069 ;
  reversal: any[] = [];
  selectedreversal : any[] = [];
  @ViewChild('reveralTag') reveralTag: Table | any;
  
  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
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

  
  constructor(private service: ReversalOutboundService,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    private auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();
  RA: any = {};

  cols: any[] =  [
    { field: 'statusDescription', header: 'Status', format: 'normal' },
    { field: 'plantDescription', header: 'Branch', format: 'normal' },
    { field: 'warehouseDescription', header: 'Warehouse', format: 'normal' },
    { field: 'outboundReversalNo', header: 'Reversal No', format: 'normal' },
    { field: 'refDocNumber', header: 'Order No', format: 'normal' },
    { field: 'manufacturerName', header: 'Mfr Name', format: 'normal' },
    { field: 'itemCode', header: 'Part No', format: 'normal' },
    { field: 'salesOrderNumber', header: 'Sale Order No', format: 'normal' },
    { field: 'partnerCode', header: 'Customer Code', format: 'normal' },
    { field: 'partnerName', header: 'Customer Name', format: 'normal' },
    { field: 'partnerCode', header: 'Target Branch', format: 'normal' },
    { field: 'barcodeId', header: 'Barcode Id', format: 'normal' },
    { field: 'reversalType', header: 'Reversal Type', format: 'normal' },
    { field: 'reversedQty', header: 'Reversed Qty', format: 'normal' },
    { field: 'reversedOn', header: 'Reversed On', format: 'date' },
    { field: 'reversedBy', header: 'Reversed By', format: 'normal' }
  ];

  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search(true);

  }
 

    searhform = this.fb.group({
    endreversedOn: [],
    itemCode: [],
    companyCodeId:[[this.auth.companyId]],
    plantId:[[this.auth.plantId]],
    languageId:[[this.auth.languageId]],
    outboundReversalNo: [],
    packBarcode: [],
    partnerCode: [],
    refDocNumber: [],
    reversalType: [],
    reversedBy: [],
    startreversedOn: [],
    statusId: [],
    warehouseId: [[this.auth.warehouseId]],
  });




  dropdownSettings: IDropdownSettings = {
    idField: 'id',
    textField: 'itemName',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

  itemCodeListselected: any[] = [];
  itemCodeList: any[] = [];

  outboundReversalNoListselected: any[] = [];
  outboundReversalNoList: any[] = [];

  partnerCodeListselected: any[] = [];
  partnerCodeList: any[] = [];

  refDocNumberselected: any[] = [];
  refDocNumberList: any[] = [];

  reversalTypeselected: any[] = [];
  reversalTypeList: any[] = [];

  packBarcodeListselected: any[] = [];
  packBarcodeList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];
  warehouseId = this.auth.warehouseId;
  search(ispageload = false) {
    if (!ispageload) {

      //dateconvertion
      this.searhform.controls.endreversedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endreversedOn.value));
      this.searhform.controls.startreversedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startreversedOn.value));

    }
    this.service.searchSpark(this.searhform.value).subscribe(res => {
      let result = res.filter((x: any) => x.warehouseId == this.warehouseId);
      this.spin.show();
      if (ispageload) {
        let tempitemCodeList: any[] = []
        const itemCode = [...new Set(res.map(item => item.itemCode))].filter(x => x != null)
        itemCode.forEach(x => tempitemCodeList.push({ value: x, label: x }));
        this.itemCodeList = tempitemCodeList;

        let tempoutboundReversalNoList: any[] = []
        const outboundReversalNo = [...new Set(res.map(item => item.outboundOrderTypeId))].filter(x => x != null)
        outboundReversalNo.forEach(x => tempoutboundReversalNoList.push({ value: x, label: x }));
        this.outboundReversalNoList = tempoutboundReversalNoList;

        let temppartnerCodeList: any[] = []
        const partnerCode = [...new Set(res.map(item => item.partnerCode))].filter(x => x != null)
        partnerCode.forEach(x => temppartnerCodeList.push({ value: x, label: x }));
        this.partnerCodeList = temppartnerCodeList;

        let temppackBarcodeList: any[] = []
        const packBarcode = [...new Set(res.map(item => item.packBarcode))].filter(x => x != null)
        packBarcode.forEach(x => temppackBarcodeList.push({ value: x, label: x }));
        this.packBarcodeList = temppackBarcodeList;

        let temprefDocNumberList: any[] = []
        const refDocNumber = [...new Set(res.map(item => item.refDocNumber))].filter(x => x != null)
        refDocNumber.forEach(x => temprefDocNumberList.push({ value: x, label: x }));
        this.refDocNumberList = temprefDocNumberList;

        let tempreversalTypeList: any[] = []
        const reversalType = [...new Set(res.map(item => item.reversalType))].filter(x => x != null)
        reversalType.forEach(x => tempreversalTypeList.push({ value: x, label: x }));
        this.reversalTypeList = tempreversalTypeList;

        let tempstatusIdList: any[] = []
       // const statusId = [...new Set(res.map(item => item.statusId))].filter(x => x != null)
        res.forEach(x => tempstatusIdList.push({ value: x.statusId, label: x.statusDescription }));
        this.statusIdList = tempstatusIdList;
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList);
      }
      this.reversal = result;
    
      this.spin.hide();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    });


  }
  reload() {
    this.searhform.reset();
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
  }
  assign(): void {

    const dialogRef = this.dialog.open(AssignPickerComponent, {
      disableClose: true,
      width: '100%',
      maxWidth: '55%',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  title1 = "Outbound";
  title2 = "Order Management";






  
  new(): void {

    const dialogRef = this.dialog.open(ReversalOutboundPopupComponent, {
      disableClose: true,
      width: '45%',
      maxWidth: '80%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.search();
    });
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

downloadexcel() {
  const exportData = this.reversal.map(item => {
    const exportItem: any = {};
    this.cols.forEach(col => {
      if (col.format == 'date') {
        exportItem[col.header] = this.cs.excelDate(item[col.field]);
      } else {
        exportItem[col.header] = item[col.field];
      }
    });
    return exportItem;
  });
  this.cs.exportAsExcel(exportData, 'Reversal');
}
onChange() {
  const choosen= this.selectedreversal[this.selectedreversal.length - 1];   
  this.selectedreversal.length = 0;
  this.selectedreversal.push(choosen);
}
}
