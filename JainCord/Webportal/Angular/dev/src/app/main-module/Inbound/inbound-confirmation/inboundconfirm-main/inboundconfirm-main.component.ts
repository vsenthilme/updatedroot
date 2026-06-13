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
import { CommonService, dropdownelement1 } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { iwExpressLogo } from "../../../../../assets/font/iwExpress.js";
import { workOrderLogo1 } from "../../../../../assets/font/workorder-logo1.js";

import { InboundConfirmationService } from "../inbound-confirmation.service";
import { ReplaceASNComponent } from "../replace-asn/replace-asn.component";
// import the pdfmake library
import pdfMake from "pdfmake/build/pdfmake";
// importing the fonts and icons needed
import pdfFonts from "../../../../../assets/font/vfs_fonts.js"
import { defaultStyle } from "../../../../config/customStyles";
import { fonts } from "../../../../config/pdfFonts";
import { indusLogo} from "../../../../../assets/font/indus.js";
import { Table } from "primeng/table";
import { DatePipe } from "@angular/common";

// PDFMAKE fonts
pdfMake.vfs = pdfFonts.pdfMake.vfs;
pdfMake.fonts = fonts;


@Component({
  selector: 'app-inboundconfirm-main',
  templateUrl: './inboundconfirm-main.component.html',
  styleUrls: ['./inboundconfirm-main.component.scss']
})
export class InboundconfirmMainComponent implements OnInit {
screenid= 3145 ;
  ready: any[] = [];
  selectedready : any[] = [];
  @ViewChild('readyTag') readyTag: Table | any;
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

cols: any[] = [
  { header: 'Branch', field: 'plantDescription', format: 'normal'},
  { header: 'Warehouse', field: 'warehouseDescription', format: 'normal'},
  { header: 'Status', field: 'statusDescription', format: 'normal'},
  { header: 'Order No', field: 'refDocNumber', format: 'normal'},
  { header: 'Order Type', field: 'referenceDocumentType', format: 'normal'},
  { header: 'Container No', field: 'containerNo', format: 'normal'},
  { header: 'Doc', field: 'doc', format: 'doc'},
  { header: 'Order Date', field: 'createdOn', format: 'date'},
  { header: 'Receipt Confirmation Date', field: 'confirmedOn', format: 'date'},
  { header: 'Lead Time', field: 'refdocno', format: 'leadTime'},
  { header: 'Confirmed By', field: 'confirmedBy', format: 'normal'}
];
  generatePdfAk(ready) {
    this.spin.show();
    //this.spin.hide();

    //Receipt List

    let currentDate = this.datePipe.transform(new Date, 'dd-MMM-yyyy HH:mm')
    var dd: any;
    let headerTable: any[] = [];
    headerTable.push([
      { text: '', bold: true, fontSize: 9, border: [false, false, false, false] },
      { text: 'Acknowlegement Receipt', margin: [0, 20, 0, 0], alignment: 'center', bold: true, fontSize: 18, border: [false, false, false, false] },
      // { image: indusLogo.headerLogo1, fit: [100, 100], alignment:'right', bold: true, fontSize: 9, border: [false, false, false, false] },
      { image: workOrderLogo1.headerLogo1, fit: [160, 160], alignment: 'right', bold: false, fontSize: 12, border: [false, false, false, false] },

    ]);
    headerTable.push([
      { text: '', bold: true, fontSize: 9, border: [false, false, false, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 9, border: [false, false, false, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: currentDate, margin: [0, -10, 20, 0], bold: true, alignment: 'right', fontSize: 9, border: [false, false, false, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd = {
      pageSize: "A4",
      pageOrientation: "portrait",
      pageMargins: [40, 80, 40, 40],
      header(currentPage: number, pageCount: number, pageSize: any): any {
        return [
          {
            table: {
              // layout: 'noBorders', // optional
              // heights: [,60,], // height for each row
              headerRows: 1,
              widths: [120,270, '*'],
              body: headerTable
            },
            margin: [20, 0, 20, 10]
          }
        ]
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [{
          text: 'Page ' + currentPage + ' of ' + pageCount,
          style: 'header',
          alignment: 'center',
          bold: true,
          fontSize: 6
        }]
      },
      content: ['\n'],
    };

    // border: [left, top, right, bottom]
    // border: [false, false, false, false]

    let bodyArray60: any[] = [];
    bodyArray60.push([
      { text: 'Customer Details', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [80, '*'],
          body: bodyArray60,
        },
        margin: [0, 0, 0, 20]
      }, '\n'
    )
  
    let bodyArray79: any[] = [];
    bodyArray79.push([
   
      { text: '1001, ABC Agro Forms Pvt Ltd', bold: false, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [false, false, false, false] },
     
    ]);
    bodyArray79.push([
     
      { text: 'All Products received with Good Condition', bold: false, fontSize: 9, border: [false, false, false, false] },
    
    ]);

    dd.content.push(
      {
        table: {
          // layout: 'noBorders', // optional
          // heights: [,60,], // height for each row
          headerRows: 1,
          widths: ['*'],
          body: bodyArray79,
        },
        margin: [5, -30, 0, 20]
      },
  
    )

    let bodyArray61: any[] = [];
    bodyArray61.push([
      { text: 'Bond Details', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [80, '*'],
          body: bodyArray61,
        },
        margin: [0, -10, 0, 20]
      }, '\n'
    )

    let bodyArray68: any[] = [];
    bodyArray68.push([
      { text: 'Bond No: '+'4287', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Validity : '+'31-12-2024', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'], margin: [0, 0, 0,0] },
    ]);
    bodyArray68.push([
      { text: 'Date: '+'01-06-2024', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
       dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [400, '*'],
          body: bodyArray68,
        },
        margin: [0, -25, 0, 20]
      }, '\n'
    )
    let bodyArray69: any[] = [];
    bodyArray69.push([
      { text: 'Item Details', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [80, '*'],
          body: bodyArray69,
        },
        margin: [0, -10, 0, 20]
      }, '\n'
    )
    let bodyArray: any[] = [];
    bodyArray.push([
      { text: 'S No', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'   },
      { text: 'Description', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
      { text: 'Qty', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
      { text: 'UOM', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
      { text: 'Chamber', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
      { text: 'Block', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
      { text: 'Reciept Date', bold: true, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'],color:'white' ,fillColor: '#203764'  },
    ]);
    bodyArray.push([
      { text: '1', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'W320 Cashews', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '500', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Bag', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1A', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1A-312', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '12/11/2024', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray.push([
      { text: '2', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Apricates -Grade 28', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '300', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Bag', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '2A', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '2A-200', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '11/11/2024', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      
    ]);
    bodyArray.push([
      { text: '3', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Turkish Dry Fig ', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '250', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Box', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1A', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1A-355', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '12/11/2024', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray.push([
      { text: '4', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: ' ', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray.push([
      { text: '5', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: ' ', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray.push([
      { text: '6', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: ' ', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [true, true, true, true],alignment: 'left', borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
   
  
  
    dd.content.push(
      {
        table: {
          // layout: 'noBorders', // optional
          // heights: [,60,], // height for each row
          headerRows: 1,
          widths: [50,100,'*','*','*','*','*'],
          body: bodyArray,
        },
        margin: [5, -20, 0, 20]
      },
  
    )
    let bodyArray70: any[] = [];
    bodyArray70.push([
      { text: 'Receiver Details', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [80, '*'],
          body: bodyArray70,
        },
        margin: [0, -5, 0, 20]
      }, '\n'
    )
    let bodyArray71: any[] = [];
    bodyArray71.push([
   
      { text: 'Sharma', bold: false, fontSize: 9, margin: [0, 0, 0,0], alignment: 'left', border: [false, false, false, false] },
     
    ]);
    bodyArray71.push([
     
      { text: 'All Products received with Good Condition', bold: false, fontSize: 9, border: [false, false, false, false] },
    
    ]);

    dd.content.push(
      {
        table: {
          // layout: 'noBorders', // optional
          // heights: [,60,], // height for each row
          headerRows: 1,
          widths: ['*'],
          body: bodyArray71,
        },
        margin: [5, -20, 0, 20]
      },
  
    )
    let bodyArray72: any[] = [];
    bodyArray72.push([
      { text: 'Signature :', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [60, '*'],
          body: bodyArray72,
        },
        margin: [0, -5, 0, 20]
      }, '\n'
    )
    pdfMake.createPdf(dd).open();


    this.spin.hide();

  }



  



  downloadexcel() {
    const exportData = this.ready.map(item => {
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
    this.cs.exportAsExcel(exportData, 'Inbound Summary');
  }



  constructor(private service: InboundConfirmationService,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,public datePipe: DatePipe,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search(true);
  }




 
  warehouseId = this.auth.warehouseId

  searhform = this.fb.group({
    containerNo: [],
    endConfirmedOn: [],
    endCreatedOn: [],
    inboundOrderTypeId: [],
    refDocNumber: [],
    startConfirmedOn: [],
    startCreatedOn: [],
    statusId: [[24],], //24
    warehouseId: [[this.auth.warehouseId]],
    companyCodeId: [[this.auth.companyId]],
    languageId: [[this.auth.languageId]],
    plantId: [[this.auth.plantId]],

  });

  dropdownSettings = {
    singleSelection: false,
    text: "Select",
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2
  };

  inboundOrderTypeIdListselected: any[] = [];
  inboundOrderTypeIdList: any[] = [];

  containerNoListselected: any[] = [];
  containerNoList: any[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];

  search(ispageload = false) {
    if (!ispageload) {

      //dateconvertion

      this.searhform.controls.endCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endCreatedOn.value));
      this.searhform.controls.startCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startCreatedOn.value));
      this.searhform.controls.endConfirmedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endConfirmedOn.value));
      this.searhform.controls.startConfirmedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startConfirmedOn.value));
      //patching
      // const packBarcodes = [...new Set(this.inboundOrderTypeIdListselected.map(item => item.id))].filter(x => x != null);
      // this.searhform.controls.packBarcodes.patchValue(packBarcodes);

      // const inboundOrderTypeId = [...new Set(this.inboundOrderTypeIdListselected.map(item => item.id))].filter(x => x != null);
      // this.searhform.controls.inboundOrderTypeId.patchValue(inboundOrderTypeId);

      // const refDocNumber = [...new Set(this.refDocNumberListselected.map(item => item.id))].filter(x => x != null);
      // this.searhform.controls.refDocNumber.patchValue(refDocNumber);

      // const containerNo = [...new Set(this.containerNoListselected.map(item => item.id))].filter(x => x != null);
      // this.searhform.controls.containerNo.patchValue(containerNo);

      // const statusId = [...new Set(this.statusIdListselected.map(item => item.id))].filter(x => x != null);
      // this.searhform.controls.statusId.patchValue(statusId);
    }
this.spin.show();
    this.service.searchstream(this.searhform.value).subscribe(res => {
      // let result = res.filter((x: any) => x.warehouseId == this.warehouseId);
      this.spin.hide();
      if (ispageload) {
        let tempinboundOrderTypeIdList: any[] = []
        res.forEach(x => tempinboundOrderTypeIdList.push({ value: x.inboundOrderTypeId, label: x.referenceDocumentType }));
//const inboundOrderTypeId = [...new Set(res.map(item => item.inboundOrderTypeId))].filter(x => x != null)
       // inboundOrderTypeId.forEach(x => tempinboundOrderTypeIdList.push({ value: x, label: x }));
        this.inboundOrderTypeIdList = tempinboundOrderTypeIdList;
        this.inboundOrderTypeIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.inboundOrderTypeIdList);

        let tempcontainerNoList: any[] = []
        const containerNo = [...new Set(res.map(item => item.containerNo))].filter(x => x != null)
        containerNo.forEach(x => tempcontainerNoList.push({ value: x, label: x }));
        this.containerNoList = tempcontainerNoList;

        let temprefDocNumberList: any[] = []
        const refDocNumber = [...new Set(res.map(item => item.refDocNumber))].filter(x => x != null)
        refDocNumber.forEach(x => temprefDocNumberList.push({ value: x, label: x }));
        this.refDocNumberList = temprefDocNumberList;

        let tempstatusIdList: any[] = []
       // const statusId = [...new Set(res.map(item => item.statusId))].filter(x => x != null)
        res.forEach(x => tempstatusIdList.push({ value: x.statusId, label: x.statusDescription }));
        this.statusIdList = tempstatusIdList;
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList);
      }
      this.ready = res;
      ;
    }, err => {

      this.cs.commonerrorNew(err);
      this.spin.hide();

    });

  }
  reload() {
    this.searhform.reset();
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId])
    this.searhform.controls.languageId.patchValue([this.auth.languageId])
    this.searhform.controls.plantId.patchValue([this.auth.plantId])
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId])
  }
  openDialog(data: any = 'new'): void {
    if (data != 'New')
      if (this.selectedready.length === 0) {
        this.toastr.error("Kindly select any row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    if (data != 'Display')
      if (this.selectedready[0].statusId == 24) {
        this.toastr.error("Order can't be edited as it's already confirmed.", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    let paramdata = "";

    if (this.selectedready.length > 0) {
      paramdata = this.cs.encrypt({ code: this.selectedready[0], pageflow: data ,pageflow1:'Inbound Summary'});
      this.router.navigate(['/main/inbound/inbound-create/' + paramdata]);
    }
    else {
      paramdata = this.cs.encrypt({ pageflow: data,pageflow1:'Inbound Summary' });
      this.router.navigate(['/main/inbound/cinbound-create/' + paramdata]);
    }
  }

  replace() {
    if (this.selectedready.length === 0) {
      this.toastr.error("Kindly select any row", "Norification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    if (this.selectedready[0].statusId != 24) {
      this.toastr.error("Order can't repalce.", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }


    const dialogRef = this.dialog.open(ReplaceASNComponent, {
      disableClose: true,
      width: '100%',
      maxWidth: '40%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spin.show();
        this.sub.add(this.service.replaceASN(this.selectedready[0].warehouseId, this.selectedready[0].preInboundNo, result, this.selectedready[0].refDocNumber).subscribe((res) => {
          this.toastr.success(result + " replaced successfully.", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });

          this.spin.hide(); //this.getAllListData();
          this.search();
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }));
      }
    });
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  OnItemDeSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onDeSelectAll(items: any) {
    console.log(items);
  }

  generatePdf(element: any) {
    this.spin.show();
    let currentDate = this.datePipe.transform(new Date, 'dd-MMM-yyyy HH:mm', 'GMT');
    let confirmedOn = this.datePipe.transform(element.confirmedOn, 'dd-MMM-yyyy HH:mm', 'GMT')
    this.sub.add(this.service.getInboundFormPdfv2(element.refDocNumber,this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId,element.preInboundNo).subscribe((res: any) => {
      this.spin.hide();
      var dd: any;
      dd = {
        pageSize: "A4",
        pageOrientation: "portrait",
        pageMargins: [40, 140, 40, 40],
        header(currentPage: number, pageCount: number, pageSize: any): any {
          return [
            {
              columns: [
                {
                  stack: [
                   
                    {
                      columns: [
                        {
                          stack: [
                            { text: '\n\n' },
                            { text: 'Supplier Name:', margin:[0, 20, 0, 0], bold: true, fontSize: 8, lineHeight: 2 },
                             { text: 'Container NO #', fontSize: 8, bold: true, lineHeight: 2 },
                          ],
                          width: 55, 
                        },
                        {
                          stack: [
                            { text: '\n\n' },
                            { text: ':  ' + res.receiptHeader.supplierName != null ? res.receiptHeader.supplierName : '', margin:[0, 20, 0, 0],  bold: true, fontSize: 8, lineHeight: 2 },
                            { text: ': ' + (res.receiptHeader.containerNo != null ? res.receiptHeader.containerNo : ''), fontSize: 8, bold: true, lineHeight: 2 },
                          ],
                          width: 100
                        },
                      ]
                    }
                  ]

                },
                {
                  stack: [
                    { text: '     Receipt Confirmation Report',  margin:[0, 5, 0, 0], alignment: 'center', bold: true, fontSize: 14 }

                  ],
                  width: 220
                },
            
                {
                  stack: [

                   { image: indusLogo.headerLogo, fit: [50, 50],  margin:[0, 0, 0, 0], alignment:'right'  },
                   { text: 'Printed Date: '+' '+currentDate, alignment: 'left', margin:[0, 25, 0, 0], bold: true, fontSize: 8,lineHeight: 2},
                   { text: 'Receipt Confirmed On: '+' '+confirmedOn, alignment: 'left', bold: true, fontSize: 8,lineHeight: 2},
                   { text: 'Order No #'  + element.refDocNumber, bold: true, alignment: 'left', fontSize: 8, lineHeight: 2 },
                   { text: 'Order Type #' + res.receiptHeader.orderType, alignment: 'left', bold: true, fontSize: 8, lineHeight: 2 },
              
                  ]
                }
              ],
              margin: [30, 5]
            }
          ]
        },
        footer(currentPage: number, pageCount: number, pageSize: any): any {
          return [{
            text: 'Page ' + currentPage + ' of ' + pageCount,
            style: 'header',
            alignment: 'center',
            bold: true,
            fontSize: 6
          }]
        },
        content: ['\n'],
        defaultStyle
      };

      //Receipt List
      if (res.receiptList.length > 0) {
        // border: [left, top, right, bottom]
        // border: [false, false, false, false]
        let bodyArray: any[] = [];
        bodyArray.push([
          { text: 'Part No', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Description', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Mfr Name', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Ordered Qty', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Accepted Qty', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Damaged Qty', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Missing Qty', bold: true, fontSize: 8, border: [false, false, false, true] },
          { text: 'Status', bold: true, fontSize: 8, border: [false, false, false, true] },
        ]);
        let totalExpected = 0;
        let totalAccepted = 0;
        let totalDamaged = 0;
        let totalMissing = 0;
        res.receiptList.forEach((receipt, i) => {
          totalExpected = totalExpected + (receipt.expectedQty != null ? receipt.expectedQty : 0);
          totalAccepted = totalAccepted + (receipt.acceptedQty != null ? receipt.acceptedQty : 0);
          totalDamaged = totalDamaged + (receipt.damagedQty != null ? receipt.damagedQty : 0);
          totalMissing = totalMissing + (receipt.missingORExcess != null ? receipt.missingORExcess : 0);
          bodyArray.push([
            { text: receipt.sku != null ? receipt.sku : '', fontSize: 8, border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.description != null ? receipt.description : '', fontSize: 8, border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.mfrSku != null ? receipt.mfrSku : '', fontSize: 8, border: [false, false, false, true], lineHeight: 2  },
            { text: receipt.expectedQty != null ? receipt.expectedQty : '0', fontSize: 8, alignment: 'left', border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.acceptedQty != null ? receipt.acceptedQty : '0', fontSize: 8, alignment: 'left', border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.damagedQty != null ? receipt.damagedQty : '0', fontSize: 8, alignment: 'left', border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.missingORExcess != null ? (receipt.expectedQty > receipt.acceptedQty ? '(' : '') + receipt.missingORExcess + (receipt.expectedQty > receipt.acceptedQty ? ')' : '') : '0', fontSize: 8, alignment: 'left', border: [false, false, false, true], lineHeight: 2 },
            { text: receipt.status != null ? receipt.status : '', fontSize: 8, border: [false, false, false, true], lineHeight: 2 }
          ])
          if ((i + 1) == res.receiptList.length) {
            bodyArray.push([
              { text: '', fontSize: 9, bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: '', fontSize: 9, bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: 'Total :', fontSize: 9, bold: true, border: [false, false, false, false] },
              { text: totalExpected, fontSize: 9, alignment: 'left', bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: totalAccepted, fontSize: 9, alignment: 'left', bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: totalDamaged, fontSize: 9, alignment: 'left', bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: (totalExpected > totalMissing ? '(' : '') + totalMissing + (totalExpected > totalMissing ? ')' : ''), fontSize: 9, alignment: 'left', bold: true, border: [false, false, false, false], lineHeight: 2 },
              { text: '', fontSize: 9, bold: true, border: [false, false, false, false], lineHeight: 2 }
            ])
          }
        });
        dd.content.push(
          '\n', '\n',
          {
            table: {
              // layout: 'noBorders', // optional
              // heights: [,60,], // height for each row
              headerRows: 1,
              widths: [50, 110, 50, 40, 40, 40, 40, 60],
              body: bodyArray
            }
          }
        )
      }

      pdfMake.createPdf(dd).open();
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  onChange() {
    const choosen= this.selectedready[this.selectedready.length - 1];   
    this.selectedready.length = 0;
    this.selectedready.push(choosen);
  }
}
