import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PlantNewComponent } from '../../other-setup/plant/plant-new/plant-new.component';
import { PlantService } from '../../other-setup/plant/plant.service';
import { ProformaNewComponent } from './proforma-new/proforma-new.component';
import pdfMake from "pdfmake/build/pdfmake";
import { iwExpressLogo } from "../../../../assets/font/iwExpress.js";
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceService } from '../invoice/invoice.service';
import { DatePipe } from '@angular/common';
import { PartnerService } from '../../Masters -1/masternew/partner/partner.service';
import { BusinessPartnerService } from '../../Masters -1/other-masters/business-partner/business-partner.service';


const ELEMENT_DATA1:  any[] = [
  {proformano:  '800108',customerid:  '8000516',billdate: '31-05-2023',billperiod: '01-05-2023 ',billperiodto: '31-05-2023',billamount: '$ 27,807',createdby: 'admin',
  status:'billed'},
   {proformano:  '800107',customerid:  '8000515',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 15200',createdby: 'admin',
   status:'billed'},{proformano:  '800106',customerid:  '8000514',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 6000',createdby: 'admin',
   status:'billed'},{proformano:  '800105',customerid:  '8000513',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 3800',createdby: 'admin',
   status:'billed'},{proformano:  '800104',customerid:  '8000512',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 4200',createdby: 'admin',
   status:'billed'},{proformano:  '800103',customerid:  '4504513',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 1900',createdby: 'admin',
   status:'billed'},{proformano:  '800102',customerid:  '4504512',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 2700',createdby: 'admin',
   status:'billed'},{proformano:  '800101',customerid:  '4504511',billdate: '30-04-2023',billperiod: '01-04-2023',billperiodto: '30-04-2023',billamount: '$ 3500',createdby: 'admin',
   status:'billed'}, {proformano:  '800100',customerid:  '4504510',billdate: '30-04-2023',billperiod: '01-04-2023 ',billperiodto: '30-04-2023',billamount: '$ 7000',createdby: 'admin',
   status:'billed'},
];

@Component({
  selector: 'app-proforma',
  templateUrl: './proforma.component.html',
  styleUrls: ['./proforma.component.scss']
})
export class ProformaComponent implements OnInit {
  screenid=3205;
  advanceFilterShow: boolean;
  @ViewChild('Setupproforma') Setupproforma: Table | undefined;
  proforma: any;
  selectedproforma : any;
  displayedColumns: string[] = ['select','companyCodeId', 'description', 'plantId','createdBy', 'createdOn', ];
  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor(public dialog: MatDialog,
   // private cas: CommonApiService,
   private route: ActivatedRoute, private router: Router,
   
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public datePipe: DatePipe,
    public cs: CommonService,
   // private excel: ExcelService,
    private fb: FormBuilder,
    private BuisnesspartnerService:BusinessPartnerService,
    private auth: AuthService,
    private service:InvoiceService , ) { }
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
  animal: string | undefined;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  applyFilterGlobal($event: any, stringVal: any) {
    this.Setupproforma!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  saveElement:any;
  js:any={};
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
   this.search();
//    this.proforma = ELEMENT_DATA1;
//    let code = this.route.snapshot.params.code;
//    this.js = this.cs.decrypt(code);
//   ;
//    this.spin.show();
//    if(this.js.code){
//    setTimeout(() => {
//       this.saveElement=this.js;
//       ELEMENT_DATA1.unshift({
//         proformano:'800517',
//         customerid: '10001',
//         billdate:'26-04-2024',
//         billperiod:'01-04-2024',
//         billperiodto:'26-04-2024',
//         billamount:'$202',
//         createdby: this.auth.userID,
//         status:'billed',
//       });
    
//     this.proforma = ELEMENT_DATA1;
   
//     this.spin.hide();
//   }, 500);
// }
  }
  ELEMENT_DATA: any[] = [];
  dataSource = new MatTableDataSource<any>(this.ELEMENT_DATA);
  selection = new SelectionModel<any>(true, []);




  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator; // Pagination
 // Pagination
  warehouseId = this.auth.warehouseId;
  search() {
    this.spin.show();
    
    let obj: any = {};
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCodeId = [this.auth.companyId];
    //obj.languageId = this.auth.languageId;
    obj.plantId = [this.auth.plantId];

    this.sub.add(this.service.searchProformaHeader(obj).subscribe((res: any[]) => {
      if (res) {
        this.proforma = res;
      } this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  deleteDialog() {
    if (this.selectedproforma.length === 0) {
      this.toastr.error("Kindly select any row", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: this.selectedproforma[0].plantId,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedproforma[0]);
      }
    });
  }
  routeToCrud(process){
    let paramdata = this.cs.encrypt({ code: process != 'New' ? this.proforma[0] : null, pageflow: process});
    this.router.navigate(['/main/threePL/profoma-new/' + paramdata]);
  }

  deleterecord(header: any) {
    this.spin.show();
    this.sub.add(this.service.DeleteProformaHeader(header).subscribe((res) => {
      this.toastr.success(header.proformaBillNo + " Deleted successfully.", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      this.search();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }



  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.handlingUnit + 1}`;
  }






  clearselection(row: any) {

    this.selection.clear();
    this.selection.toggle(row);
  }

openDialog(data: any = 'New'): void {
console.log(this.Setupproforma)
  if (data != 'New')
  if (this.selectedproforma.length === 0) {
    this.toastr.warning("Kindly select any Row", "Notification", {
      timeOut: 2000,
      progressBar: false,
    });
    return;
  }
  const dialogRef = this.dialog.open(ProformaNewComponent, {
    disableClose: true,
    width: '55%',
    maxWidth: '80%',
    data: { pageflow: data, code: data != 'New' ? this.selectedproforma[0].plantId : null}
  });

  dialogRef.afterClosed().subscribe(result => {
    this.search();
  });
}
// getAll() {
//   this.spin.show();
//   this.sub.add(this.service.Getall().subscribe((res: any[]) => {
//     
//     if(res){
//       this.proforma=res;
//   //   this.dataSource = new MatTableDataSource<any>(res);
//   //   this.selection = new SelectionModel<any>(true, []);
//   //   this.dataSource.sort = this.sort;
//   //  this.dataSource.paginator = this.paginator;
//     }this.spin.hide();
//   }, err => {
//     this.cs.commonerrorNew(err);
//     this.spin.hide();
//   }));
// }

// deleteDialog() {
//   if (this.selectedproforma.length === 0) {
//     this.toastr.error("Kindly select any row", "Notification",{
//       timeOut: 2000,
//       progressBar: false,
//     });
//     return;
//   }
//   const dialogRef = this.dialog.open(DeleteComponent, {
//     disableClose: true,
//     width: '40%',
//     maxWidth: '80%',
//     position: { top: '9%', },
//     data: this.selectedproforma[0].plantId,
//   });

//   dialogRef.afterClosed().subscribe(result => {

//     if (result) {
//       this.deleterecord(this.selectedproforma[0].plantId);

//     }
//   });
// }


// deleterecord(id: any) {
//   this.spin.show();
//   this.sub.add(this.service.Delete(id,this.auth.company,this.auth.languageId).subscribe((res) => {
//     this.toastr.success(id + " Deleted successfully.","Notification",{
//       timeOut: 2000,
//       progressBar: false,
//     });
//     this.spin.hide();
//     this.getAll();
//   }, err => {
//     this.cs.commonerrorNew(err);
//     this.spin.hide();
//   }));
// }
downloadexcel() {
  var res: any = [];
  this.proforma.forEach(x => {
    res.push({
      "Proforma No": x.proformano,
      " Customer ID ": x.partnerCode,
      "Bill Date From ":this.cs.dateapiutc0(x.billDateFrom),
      "	Bill Date To ": this.cs.dateapiutc0(x.billdateto),
      "Bill Amount":x.billQuantity,
      "Created By":x.createdBy,
      "Status":x.statusId,
      
    });

  })
  this.cs.exportAsExcel(res, "Proforma Invoice");
}
Submit(element){
  console.log(element);
  let paramdata = "";
  if(element){
  paramdata = this.cs.encrypt({ pageflow: "Invoice", code: element});
  this.router.navigate(['/main/threePL/invoice-new/' + paramdata]);
  this.toastr.success(element.proformaBillNo + "Proforma Invoice Approved Successfully.", "", {
          
    timeOut: 2000,
    progressBar: false,
  })
}
else{
  
  this.router.navigate(['/main/threePL/invoice-new/']);
  this.toastr.success(  "Proforma Invoice Approved Successfully.", "", {
          
    timeOut: 2000,
    progressBar: false,
  })
}
  
  }
onChange() {
  console.log(this.selectedproforma.length)
  const choosen= this.selectedproforma[this.selectedproforma.length - 1];   
  this.selectedproforma.length = 0;
  this.selectedproforma.push(choosen);
} 
generatePdf(element: any) {
  this.spin.show();
  let obj1:any={};
  obj1.companyCodeId = [this.auth.companyId];
  obj1.plantId = [this.auth.plantId];
  obj1.languageId = [this.auth.languageId];
      obj1.warehouseId = [this.auth.warehouseId];
      obj1.partnerCode=[element.partnerCode]
      this.sub.add(this.BuisnesspartnerService.search(obj1).subscribe((result: any) => {  
        console.log(result) 
        const partnerCode=result[0].partnerCode;
        const partnerName=result[0].partnerName;
  let obj: any = {};
  obj.companyCodeId = [this.auth.companyId];
  obj.plantId = [this.auth.plantId];
  obj.languageId = [this.auth.languageId];
      obj.warehouseId = [this.auth.warehouseId];
      obj.proformaHeaderId=[element.proformaBillNo];
      obj.partnerCode=[element.partnerCode]
  this.sub.add(this.service.searchProformaline(obj).subscribe((res: any) => {
    let invoiceDate = this.datePipe.transform(res[0].invoiceDate, 'dd-MMM-yyyy HH:mm', 'GMT+3')
    let total=0;
  for(let i=0;i<res.length;i++){
   total+=res[i].billQuantity; 
  }
    let taxes=((total *5)/100);
    let totalAftertax=taxes+(total);
    let billDateFrom= this.datePipe.transform(element.billDateFrom, 'dd-MMM-yyyy', 'GMT+3')
    let billDateTo=this.datePipe.transform(element.billDateTo, 'dd-MMM-yyyy', 'GMT+3')
    let billMonth=this.datePipe.transform(element.billDateFrom, 'MMMM', 'GMT+3')

    //Receipt List
    if (res != null) {
    
      var dd: any;
      let headerTable: any[] = [];

      headerTable.push([
        //  { image: iwExpressLogo.headerLogo, fit: [200, 200], alignment: 'left',  bold: false, fontSize: 12, border: [false, false, false, false] },
        { text: 'Demo-Proforma INVOICE', bold: true, alignment: 'right', margin: [0, 10, 20, 0], fontSize: 30, color: '#949494', border: [false, false, false, false] },
        //     { image: workOrderLogo1.headerLogo1, fit: [180, 180], alignment: 'center',  bold: false, fontSize: 12, border: [false, false, false, false] },
      ]);

      dd = {
        pageSize: "A4",
        pageOrientation: "portrait",
        pageMargins: [30, 90, 30, 20],
        header(currentPage: number, pageCount: number, pageSize: any): any {
          return [
            {
              table: {
                // layout: 'noBorders', // optional
                // heights: [,60,], // height for each row
                headerRows: 1,
                widths: ['*', '*'],
                body: headerTable
              },
              margin: [10, 20, 10, 40]
            }
          ]
        },
        styles: {
          anotherStyle: {
            bordercolor: '#6102D3'
          }
        },

        footer(currentPage: number, pageCount: number, pageSize: any): any {
          return [
            { text: 'This is computer generated Invoice no signature required', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 9, margin: [30, -40, 0, 0] },
            {
              text: 'Page ' + currentPage + ' of ' + pageCount,
              style: 'header',
              alignment: 'center',
              bold: false,
              fontSize: 6,
              margin: [10, 30, 10, 0]
            }

            // { image: res.requirement == "Packing & Moving" ? workOrderLogo1.ulogistics1 : workOrderLogo1.ustorage, width: 570,   bold: false, margin: [10, -80, 0, 0], fontSize: 12, border: [false, false, false, false] },
          ]
        },
        content: ['\n'],
        //   defaultStyle,

      };

      let headerArray: any[] = [];
      headerArray.push([
        { text: 'Innerworks Company  ', bold: true, fontSize: 10, border: [false, false, false, false] },
        { text: '', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: 'Invoice # ', bold: true, fontSize: 10, border: [true, true, true, true], alignment: 'center' },
        { text: 'Date', bold: true, fontSize: 10, border: [true, true, true, true], alignment: 'center' },

      ]); headerArray.push([
        { text: 'Office No:21 | Waha Mall |Dajeej Farwaniya |Kuwait  ', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: '', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: (res[0].proformaBillNo), bold: false, fontSize: 10, border: [true, true, true, true], alignment: 'center' },
        { text: invoiceDate, bold: false, fontSize: 10, border: [true, true, true, true], alignment: 'center' },

      ]);
      headerArray.push([
        { text: 'Phone No.: +965 2206 2205  ', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: '', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: 'CustomerID', bold: true, fontSize: 10, border: [true, true, true, true], alignment: 'center' },
        { text: 'Terms', bold: true, fontSize: 10, border: [true, true, true, true], alignment: 'center' },

      ]);
      headerArray.push([
        { text: 'Email: info@iwexpress.com  ', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: '', bold: false, fontSize: 10, border: [false, false, false, false] },
        { text: (res[0].partnerCode), bold: false, fontSize: 10, border: [true, true, true, true], alignment: 'center' },
        { text: 'Credit 7 Days', bold: false, fontSize: 10, border: [true, true, true, true], alignment: 'center' },

      ]);



      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [250, 70, '*', '*'],
            body: headerArray
          },
          margin: [0, -10, 10, 0]
        }
      )
      let headerArray2: any[] = [];
      headerArray2.push([
        { text: 'Bill To ', bold: true, border: [false, false, false, false], alignment: 'left', fontSize: 11, color: '#555555', fillColor: '#dedede' },


      ]);
      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: ['*'],
            body: headerArray2
          },
          margin: [0, 35, 10, 0]
        }
      )
      let headerArray3: any[] = [];
      headerArray3.push([
        { text: partnerCode+'-'+partnerName, bold: true, border: [false, false, false, false], alignment: 'left', fontSize: 10 },


      ]);
      headerArray3.push([
        { text: (result[0].address1)+''+(result[0].address2), bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },


      ]);
      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: ['*'],
            body: headerArray3
          },
          margin: [0, 10, 10, 0]
        }
      )
      let headerArray4: any[] = [];
      headerArray4.push([
        { text: ' Fulfilment + Delivery Charges for the Month of '+ billMonth +'2024' + '  ' + 'Period('+billDateFrom+'-'+billDateTo+')', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },


      ]);

      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: ['*'],
            body: headerArray4
          },
          margin: [0, 5, 10, 0]
        }
      )
      let headerArray5: any[] = [];
      headerArray5.push([
        { text: 'Description', bold: true, border: [true, true, false, true], alignment: 'left', fontSize: 10 },
        { text: 'Unit', bold: true, border: [true, true, false, true], alignment: 'center', fontSize: 10 },
        { text: 'Qty', bold: true, border: [true, true, false, true], alignment: 'center', fontSize: 10 },
        { text: 'Unit Price(KWD)', bold: true, border: [true, true, false, true], alignment: 'center', fontSize: 10 },
        { text: 'Amount(KWD)', bold: true, border: [true, true, true, true], alignment: 'center', fontSize: 10 },

      ]); dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [300, 30, 30, 55, '*'],
            body: headerArray5
          },
          margin: [0, 10, 10, 0]
        }
      )
      let headerArray7: any[] = [];
      for (let i = 0; i < res.length; i++) {
      headerArray7.push([
        { text: (res[i].referenceField4), bold: true, border: [true, false, false, true], alignment: 'left', fontSize: 10 },
        { text: (res[i].billUnit), bold: true, border: [true, false, false, true], alignment: 'center', fontSize: 10 },
        { text: (res[i].referenceField6), bold: true, border: [true, false, false, true], alignment: 'center', fontSize: 10 },
        { text: (res[i].priceUnit), bold: true, border: [true, false, false, true], alignment: 'center', fontSize: 10 },
        { text: (res[i].billQuantity), bold: true, border: [true, false, true, true], alignment: 'center', fontSize: 10 },

      ]);
    }
      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [300, 30, 30, 55, '*'],
            body: headerArray7
          },
          margin: [0, -1, 10, 0]
        }
      )
      let headerArray109: any[] = [];
      headerArray109.push([
        { text: 'GST 5% Added', bold: false, border: [true, false, false, true], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [true, false, false, true], alignment: 'center', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, true], alignment: 'center', fontSize: 10, margin: [7, 0, 0, 0] },
        { text: '', bold: true, border: [false, false, false, true], alignment: 'center', fontSize: 10, margin: [-35, 0, 0, 0] },
        { text: taxes, bold: true, border: [false, false, true, true], alignment: 'center', fontSize: 10 },

      ]);

      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [300, 30, 30, 55, '*'],
            body: headerArray109
          },
          margin: [0, 0, 10, 0]
        }
      )
      let headerArray10: any[] = [];
      headerArray10.push([
        { text: '', bold: false, border: [true, false, false, true], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [true, false, false, true], alignment: 'center', fontSize: 10 },
        { text: 'Total', bold: true, border: [false, false, false, true], alignment: 'center', fontSize: 10, margin: [7, 0, 0, 0] },
        { text: 'in KWD', bold: true, border: [false, false, false, true], alignment: 'center', fontSize: 10, margin: [-35, 0, 0, 0] },
        { text: totalAftertax, bold: true, border: [false, false, true, true], alignment: 'center', fontSize: 10 },

      ]);

      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [300, 30, 30, 55, '*'],
            body: headerArray10
          },
          margin: [0, 0, 10, 0]
        }
      )
      let headerArray11: any[] = [];
      headerArray11.push([
        { text: 'Bank Details:', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, false], alignment: 'center', fontSize: 10 },
        { text: 'A/C Name: INNERWORKS DESIGN AND BUILD  ', bold: false, border: [true, true, true, false], alignment: 'left', fontSize: 10 },


      ]);
      headerArray11.push([
        { text: '', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, false], alignment: 'center', fontSize: 10 },
        { text: 'A/C No.: 95954323  ', bold: false, border: [true, false, true, false], alignment: 'left', fontSize: 10 },


      ]);
      headerArray11.push([
        { text: '', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, false], alignment: 'center', fontSize: 10 },
        { text: 'Bank Name: Gulf Bank Kuwait, Branch : Mubark Al Kabeer, Head Office', bold: false, border: [true, false, true, false], alignment: 'left', fontSize: 10 },


      ]);
      headerArray11.push([
        { text: '', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, false], alignment: 'center', fontSize: 10 },
        { text: 'IBAN No.: KW87 GULB 0000 0000 0000 0095 9543 23', bold: false, border: [true, false, true, false], alignment: 'left', fontSize: 10 },


      ]);
      headerArray11.push([
        { text: '', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
        { text: '', bold: true, border: [false, false, false, false], alignment: 'center', fontSize: 10 },
        { text: 'Address:P.O.Box 3200,Safat13032, Kuwait', bold: false, border: [true, false, true, true], alignment: 'left', fontSize: 10 },


      ]);

      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: [100, 50, '*'],
            body: headerArray11
          },
          margin: [0, 30, 10, 0]
        }
      )
      let headerArray12: any[] = [];
      headerArray12.push([
        { text: 'Payment must be paid in full , free of any bank charges or other charges.', bold: false, border: [false, false, false, false], alignment: 'center', fontSize: 10 },


      ]);
      headerArray12.push([
        { text: 'Thank you for your business!', bold: false, border: [false, false, false, false], alignment: 'center', fontSize: 10 },


      ]);




      dd.content.push(
        {
          table: {
            headerRows: 1,
            widths: ['*'],
            body: headerArray12
          },
          margin: [0, 20, 10, 0]
        }
      )

      //    pdfMake.createPdf(dd).download('Invoice');
      pdfMake.createPdf(dd).open();

      this.spin.hide();
    }

     
     if(res == null) {
      this.toastr.info("No data available", "Pdf Download", {
        timeOut: 2000,
        progressBar: false,
      });
    }
    this.spin.hide();
  }, err => {
    this.cs.commonerrorNew(err);;
    this.spin.hide();
  }));
}))
}
}



