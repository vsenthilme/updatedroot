import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Table } from "primeng/table";
import { Subscription } from "rxjs";
import { DeleteComponent } from "src/app/common-field/delete/delete.component";
import { CommonService } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { InvoiceService } from "./invoice.service";
import pdfMake from "pdfmake/build/pdfmake";
import { DatePipe } from "@angular/common";

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss']
})
export class InvoiceComponent implements OnInit {
  screenid=3206;
  advanceFilterShow: boolean;
  @ViewChild('Setupinvoice') Setupinvoice: Table | undefined;
  invoices: any;
  selectedinvoice: any;
  displayedColumns: string[] = ['select', 'companyCodeId', 'description', 'plantId', 'createdBy', 'createdOn',];
  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor(
    public dialog: MatDialog,
    public toastr: ToastrService,
    private route: ActivatedRoute, private router: Router,
    private spin: NgxSpinnerService,
    public datePipe: DatePipe,
    public cs: CommonService,
    private service: InvoiceService,
    private fb: FormBuilder,
    private auth: AuthService,) { }
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
  applyFilterGlobal($event: any, stringVal: any) {
    this.Setupinvoice!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  saveElement: any;
  js: any = {};
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search();
  }

onChange() {
    const choosen = this.selectedinvoice[this.selectedinvoice.length - 1];
    this.selectedinvoice.length = 0;
    this.selectedinvoice.push(choosen);
  }
  
  search() {
    this.spin.show();
    
    let obj: any = {};
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCodeId = [this.auth.companyId];
    
    obj.plantId = [this.auth.plantId];

    this.sub.add(this.service.searchHeader(obj).subscribe((res: any[]) => {
      if (res) {
        this.invoices = res;
      } this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }


  routeToCrud(process){
    let paramdata = this.cs.encrypt({ code: process != 'New' ? this.invoices[0] : null, pageflow: process});
    this.router.navigate(['/main/threePL/invoice-new/' + paramdata]);
  }

  deleteDialog() {
    if (this.selectedinvoice.length === 0) {
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
      data: this.selectedinvoice[0].plantId,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedinvoice[0]);
      }
    });
  }


  deleterecord(header: any) {
    this.spin.show();
    this.sub.add(this.service.DeleteHeader(header).subscribe((res) => {
      this.toastr.success(header.invoiceHeader + " Deleted successfully.", "Notification", {
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

  downloadexcel() {
    var res: any = [];
    this.invoices.forEach(x => {
      res.push({
        "Bill No": x.billno,
        "Proforma No": x.proformano,
        " Customer ID ": x.customerid,
        "Bill Date ": this.cs.dateapiutc0(x.billdate),
        "	Bill Period ": x.billperiod,
        "Bill Amount": x.billamount,
        "Created By": x.createdby,
        "Status": x.status,
      });

    })
    this.cs.exportAsExcel(res, "Invoice");
  }
  
  send_date = new Date();
  formattedDate: any;
  storeNumberList: any
  generatePdf(element: any) {
    this.spin.show();
    let line=element
    let obj: any = {};
  obj.companyCodeId = [this.auth.companyId];
  obj.plantId = [this.auth.plantId];
  obj.languageId = [this.auth.languageId];
      obj.warehouseId = [this.auth.warehouseId];
      obj.invoiceHeaderId=[element.invoiceNumber];
      obj.partnerCode=[element.partnerCode]
    this.sub.add(this.service.searchinvoiceline(obj).subscribe((res: any) => {
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
  ;
      //Receipt List
      if (res != null) {
      
        var dd: any;
        let headerTable: any[] = [];
  
        headerTable.push([
          //  { image: iwExpressLogo.headerLogo, fit: [200, 200], alignment: 'left',  bold: false, fontSize: 12, border: [false, false, false, false] },
          { text: 'Demo-INVOICE', bold: true, alignment: 'right', margin: [0, 10, 20, 0], fontSize: 30, color: '#949494', border: [false, false, false, false] },
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
                margin: [10, 20, 10, 20]
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
              { text: 'This is computer generated Invoice no signature required', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 9, margin: [30, -40, 0, 10] },
              {
                text: 'Page ' + currentPage + ' of ' + pageCount,
                style: 'header',
                alignment: 'center',
                bold: false,
                fontSize: 6,
                margin: [10, 0, 10, 0]
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
          { text: 'AL-MAZJ HOUSE TRADING COMPANY  (blends.com.sa) ', bold: true, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
  
  
        ]);
        headerArray3.push([
          { text: 'P.O.Box 64006, Riyadh 11536, KSA ', bold: false, border: [false, false, false, false], alignment: 'left', fontSize: 10 },
  
  
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
            margin: [0, 30, 10, 0]
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
            margin: [0, 0, 10, 0]
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
  }
  // res.totalAfterDiscount

}




