import { Component, Injectable, output } from '@angular/core';
import pdfMake from 'pdfmake/build/pdfmake';
// @ts-ignore
import pdfFonts from "../../../../../assets/font/vfs_fonts.js"
// @ts-ignore
import { iwExpressLogo, iwExpressLogo3 } from "../../../../../assets/font/iwExpress.js";
import JsBarcode from 'jsbarcode';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { HttpErrorResponse } from '@angular/common/http/index.js';
import { DomSanitizer } from '@angular/platform-browser';
import { MessageService } from 'primeng/api';
import { ConsignmentService } from '../../../operation/consignment/consignment.service.js';
pdfMake.vfs = pdfFonts.pdfMake.vfs;
//pdfMake.fonts = fonts;
pdfMake.fonts = {
  Roboto: {
    normal: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf',
    bold: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Medium.ttf'
  }
}

@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-customs-invoicepdf',
  templateUrl: './invoicepdf.component.html',
  styleUrl: './invoicepdf.component.scss'
})
export class InvoicepdfComponent {

  constructor(
    public datePipe: DatePipe,
    private sanitizer: DomSanitizer,
    private messageService: MessageService,
    private spin: NgxSpinnerService,
    private ConsignmentService: ConsignmentService,
  ) { }

  generateDDUInvoice(result: any, type: any) {
    var dd: any;
    dd = {
      pageSize: "A4",
      pageOrientation: "portrait",
      pageMargins: [20, 10, 20, 10],
      styles: {
        anotherStyle: {
          bordercolor: '#6102D3'
        }
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [{
          text: '',
          style: 'header',
          alignment: 'center',
          bold: true,
          fontSize: 6
        }]
      },
      content: ['\n'],
    };

    let createdOn = this.datePipe.transform(new Date, 'dd-MM-yyyy')
    let invoiceDate = this.datePipe.transform(result.invoiceDate, 'dd-MM-yyyy')

    let barcodeAWB: any[] = [];
    barcodeAWB.push([
      { image: iwExpressLogo.headerLogo, margin: [0, -15, 0, 0], fit: [150, 150], alignment: 'left', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -15, 0, 0], fit: [100, 100], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [95, 80, '*'],
          body: barcodeAWB,
        },
      }, '\n'
    )

    let bodyArray67: any[] = [];
    bodyArray67.push([
      { text: 'Innerworks Company', bold: true, fontSize: 10, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'INVOICE #', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'DATE', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    bodyArray67.push([
      { text: 'Office No. 21 | Waha Mall | Dajeej Farwaniya |', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.invoiceNo, bold: false, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: invoiceDate, bold: false, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    bodyArray67.push([
      { text: 'Kuwait phone No.: +965 2206 2206', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'CUSTOMER ID', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'TERMS', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    bodyArray67.push([
      { text: 'Email: info@iwexpress.com', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.partnerName, bold: false, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Cash Payment', bold: false, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [190, '*', 80, 80],
          body: bodyArray67,
        },
      }, '\n'
    )

    let bodyArray97: any[] = [];
    bodyArray97.push([
      { text: 'BILL TO', bold: true, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray97.push([
      { text: result.destinationName, bold: true, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray97.push([
      { text: result.destinationAddress, bold: true, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);


    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [100, '', '', '', ''],
          body: bodyArray97,
        },
        margin: [0, -1, 0, 0],
      }, '\n'
    )
    let bodyArray98: any[] = [];
    bodyArray98.push([
      { text: 'Shipment AWB: ' + result.partnerHouseAirwayBill, alignment: 'right', bold: true, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*'],
          body: bodyArray98,
        },
        margin: [0, -1, 0, 0],
      },
    )
    let bodyArray99: any[] = [];
    bodyArray99.push([
      { text: 'Kuwait Customs Clearance Invoice', bold: false, fontSize: 10, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [200, '*', '*', '*', '*'],
          body: bodyArray99,
        },
        margin: [0, -1, 0, 0],
      }, '\n'
    )
    let bodyArray100: any[] = [];
    bodyArray100.push([
      { text: 'Description', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Unit', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'QTY', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Unit Price (KD)', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Total Price (KD)', bold: true, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*', 50, 50, 70, 70],
          body: bodyArray100,
        },
        margin: [0, -1, 0, 0],
      },
    )
    let bodyArray101: any[] = [];
    let total = (Number(result.clearanceFee) || 0) + (Number(result.customsDuty) || 0) + (Number(result.specialApprovalValue) || 0);
    bodyArray101.push([
      { text: 'Clearance & Handling Fee', margin: [0, 10, 0, 10], bold: false, fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'HAWB', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.clearanceFee.toFixed(3), bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.clearanceFee.toFixed(3), margin: [0, 10, 0, 10], bold: false, fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray101.push([
      { text: 'Customs Duty', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'HAWB', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.customsDuty.toFixed(3), bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.customsDuty.toFixed(3), bold: false, margin: [0, 10, 0, 10], fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray101.push([
      { text: 'Special Approval Value', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'HAWB', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'left', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '1', bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.specialApprovalValue.toFixed(3), bold: false, margin: [0, 10, 0, 10], fontSize: 10, alignment: 'center', border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.specialApprovalValue.toFixed(3), bold: false, margin: [0, 10, 0, 80], fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*', 50, 50, 70, 70],
          body: bodyArray101,
        },
        margin: [0, -1, 0, 0],
      },
    )
    let bodyArray102: any[] = [];
    bodyArray102.push([
      { text: '= TOTAL in KD', bold: false, fontSize: 10, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: total.toFixed(3), bold: false, fontSize: 10, border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*', 70],
          body: bodyArray102,
        },
        margin: [0, -1, 0, 0],
      }, '\n', '\n'
    )
    let bodyArray103: any[] = [];
    bodyArray103.push([
      { text: 'Bank Details', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'A/C Name: INNERWORKS DESIGN AND BUILD', bold: false, fontSize: 10, border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray103.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'A/C No: 95954323', bold: false, fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray103.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Bank Name: Gulf Bank Kuwait, Branch: Mubark Al Kabeer, Head Office IBAN No: KW87 GULB', bold: false, fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray103.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '0000 0000 0000 0095 9543 23', bold: false, fontSize: 10, border: [true, false, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray103.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Address: P.O.Box 3200, Safa: 13032, Kuwait', bold: false, fontSize: 10, border: [true, false, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [70, '*'],
          body: bodyArray103,
        },
        margin: [0, -1, 0, 0],
      }, '\n'
    )
    let bodyArray104: any[] = [];
    bodyArray104.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      {
        text: 'Payment must be paid in full, free of any bank charges or other charges.', alignment: 'center',
        bold: false, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080']
      },
    ]);
    bodyArray104.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Thank you for your business!', bold: false, alignment: 'center', fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [50, 450],
          body: bodyArray104,
        },
        margin: [0, -1, 0, 0],
      }, '\n'
    )
    let bodyArray105: any[] = [];
    bodyArray105.push([
      { text: '', bold: false, fontSize: 10, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'This is computer generated Invoice no signature requried', bold: false, alignment: 'center', fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [50, 450],
          body: bodyArray105,
        },
        margin: [0, -1, 0, 0],
      }, '\n'
    )
    pdfMake.createPdf(dd).open();
    // pdfMake.createPdf(dd).download('Invoice' + result[0].houseAirwayBill)
  }
  invoiceDownload(result: any) {
    console.log(result)
    var dd: any;
    dd = {
      pageSize: "A4",
      pageOrientation: "landscape",
      pageMargins: [20, 10, 20, 10],
      styles: {
        anotherStyle: {
          bordercolor: '#6102D3'
        }
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [{
          text: '',
          style: 'header',
          alignment: 'center',
          bold: true,
          fontSize: 6
        }]
      },
      content: ['\n'],
    };

    let createdOn = this.datePipe.transform(new Date, 'dd-MM-yyyy')

    let barcodeAWB: any[] = [];
    barcodeAWB.push([
      { image: iwExpressLogo.headerLogo, margin: [0, -15, 0, 0], fit: [150, 150], alignment: 'left', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -15, 0, 0], fit: [100, 100], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [95, 80, '*'],
          body: barcodeAWB,
        },
      }, '\n'
    )

    let bodyArray67: any[] = [];
    bodyArray67.push([
      { text: 'Innerworks for Receiving and Distributing letters and Parcels Company W.L.L', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray67.push([
      { text: 'PO BOX, 15300, Dajeej, Kuwait', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray67.push([
      { text: 'Phone No.: +965 2206 2205', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray67.push([
      { text: 'Email: info@iwexpress.com', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [300],
          body: bodyArray67,
        },
      }, '\n'
    )

    let bodyArray68: any[] = [];
    bodyArray68.push([
      { text: 'Invoice #', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.invoiceNo, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray68.push([
      { text: 'Invoice Date', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: this.datePipe.transform(result.invoiceDate, 'dd-MM-yyyy'), bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray68.push([
      { text: 'Client Name', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result.partnerName, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [50, '*'],
          body: bodyArray68,
        },
      }, '\n'
    )
    let bodyArray69: any[] = [];
    bodyArray69.push([
      { text: 'Invoice Description', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    bodyArray69.push([
      { text: result.description, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*'],
          body: bodyArray69,
        },
      }, '\n'
    )

    let bodyArray70: any[] = [];
    bodyArray70.push([
      { text: 'MAWB Ref#', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Total No. of Shipments', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Clearance Charges', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Other Approvals', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Food Approvals', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Approvals', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Handling Fees', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Custom Duty 5%', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Value (KWD)', bold: true, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    let totalNoOfShipments = 0;
    let totalClearanceCharge = 0;
    let totalOtherApproval = 0;
    let totalFoodApproval = 0;
    let totalCustomDuty = 0;
    let totalApproval = 0;
    let totalHandlingForkCharges = 0;
    let totalValue = 0;
    result.invoiceLines.forEach((x: any) => {

      totalNoOfShipments += parseFloat(x.noOfShipments) || 0;
      totalClearanceCharge += parseFloat(x.clearanceCharge) || 0;
      totalOtherApproval += parseFloat(x.otherApproval) || 0;
      totalFoodApproval += parseFloat(x.foodApproval) || 0;
      totalCustomDuty += parseFloat(x.customDuty) || 0;
      totalApproval += parseFloat(x.approvals) || 0;
      totalHandlingForkCharges += parseFloat(x.handlingFees) || 0;
      totalValue += parseFloat(x.totalValue) || 0;

      bodyArray70.push([
        { text: x.partnerMasterAirwayBill, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.noOfShipments, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.clearanceCharge, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.otherApproval, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.foodApproval, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.approvals, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.handlingFees, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.customDuty, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.totalValue, bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      ]);
    })

    bodyArray70.push([
      { text: 'Total', bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalNoOfShipments.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalClearanceCharge.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalOtherApproval.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalFoodApproval.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalApproval.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalHandlingForkCharges.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalCustomDuty.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalValue.toFixed(3), bold: false, fontSize: 9, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: ['*', 80, 80, 80, 80, 80, 80, 80, 80],
          body: bodyArray70,
        },
      },
    )

    pdfMake.createPdf(dd).open();
   // pdfMake.createPdf(dd).download('Invoice_' + result.invoiceNo)
  }

  customsCostingSheet(result: any) {
    var dd: any;
    dd = {
      pageSize: "A4",
      pageOrientation: "landscape",
      pageMargins: [20, 10, 20, 10],
      styles: {
        anotherStyle: {
          bordercolor: '#6102D3'
        }
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [{
          text: '',
          style: 'header',
          alignment: 'center',
          bold: true,
          fontSize: 6
        }]
      },
      content: ['\n'],
    };

    let invoiceDate = this.datePipe.transform(result[0].date, 'dd-MM-yyyy')


    let barcodeAWB: any[] = [];
    barcodeAWB.push([
      { image: iwExpressLogo.headerLogo, margin: [0, -15, 0, 0], fit: [80, 80], alignment: 'left', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -15, 0, 0], fit: [100, 100], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
      { text: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
    ]);

    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [95, 80, '*'],
          body: barcodeAWB,
        },
      }, '\n'
    )
    let bodyArray66: any[] = [];
    bodyArray66.push([
      { text: 'Petty Cash Reimbursement Form', bold: true, fontSize: 9, alignment: 'center', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [650],
          body: bodyArray66,
        },
      },
    )
    let bodyArray67: any[] = [];
    bodyArray67.push([
      { text: 'Petty cash Number', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].cashNumber, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Date', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: invoiceDate, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Department', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].department, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Petty cash holder', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].cashHolder, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    bodyArray67.push([
      { text: 'Customer Name', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].partnerName, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Cost center', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].costCenter, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Number of Shipments', bold: true, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].noOfShipments, bold: false, fontSize: 9, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Remark', bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].remark, bold: true, fontSize: 7, alignment: 'left', border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [80, 80, 50, 100, 100, 100, 100, 100, 100, '*'],
          body: bodyArray67,
        },
      }, '\n'
    )
    let bodyArray70: any[] = [];
    
    bodyArray70.push([
      { text: 'S.No', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Invoice Number', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Invoice Date', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Supplier Name', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Desc', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'NAS-Delivery Order', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Global', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Labour and Truck', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Approval', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Handling and Fork Charges', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Other Charges', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Special Approvals', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Other Approvals', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Food Approvals', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Customs Duty 5%', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Stamp(Kuwait Customs)', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Total', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);

    let totalNas = 0;
    let totalGlobal = 0;
    let totalLabourandTruck = 0;
    let totalApproval = 0;
    let totalHandlingForkCharges = 0;
    let totalOtherCharges = 0;
    let totalSpecialApprovals = 0;
    let totalOtherApprovals = 0;
    let totalFoodApprovals = 0;
    let totalCustoms = 0;
    let totalStamp = 0;
    let totalValue = 0;



    result.forEach((x: any, index: any) => {

      totalNas += parseFloat(x.nasDeliveryOrder) || 0;
      totalGlobal += parseFloat(x.global) || 0;
      totalLabourandTruck += parseFloat(x.labours) || 0;
      totalApproval += parseFloat(x.approval) || 0;
      totalHandlingForkCharges += parseFloat(x.handlingForkCharges) || 0;
      totalOtherCharges += parseFloat(x.otherCharges) || 0;
      totalSpecialApprovals += parseFloat(x.specialApprovals) || 0;
      totalOtherApprovals += parseFloat(x.otherApprovals) || 0;
      totalFoodApprovals += parseFloat(x.foodApprovals) || 0;
      totalCustoms += parseFloat(x.customDuty) || 0;
      totalStamp += parseFloat(x.stamp) || 0;
      totalValue += parseFloat(x.total) || 0;
     


      bodyArray70.push([
        { text: index + 1, bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.invoiceNumber, bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: this.datePipe.transform(x.invoiceDate, 'dd-MM-yyyy'), bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.supplierName, bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.costDescription, bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.nasDeliveryOrder != null ? (x.nasDeliveryOrder).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.global != null ? (x.global).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.labours != null ? (x.labours).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.approval != null ? (x.approval).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.handlingForkCharges != null ? (x.handlingForkCharges).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.otherCharges != null ? (x.otherCharges).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.specialApprovals != null ? (x.specialApprovals).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.otherApprovals != null ? (x.otherApprovals).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.foodApprovals != null ? (x.foodApprovals).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.customDuty != null ? (x.customDuty).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.stamp != null ? (x.stamp).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
        { text: x.total != null ? (x.total).toFixed(3) : '', bold: false, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      ]);
    }),

    totalNas = parseFloat(totalNas.toFixed(3));
    totalGlobal = parseFloat(totalGlobal.toFixed(3));
    totalLabourandTruck = parseFloat(totalLabourandTruck.toFixed(3));
    totalApproval = parseFloat(totalApproval.toFixed(3));
    totalHandlingForkCharges = parseFloat(totalHandlingForkCharges.toFixed(3));
    totalOtherCharges = parseFloat(totalOtherCharges.toFixed(3));
    totalSpecialApprovals = parseFloat(totalSpecialApprovals.toFixed(3));
    totalOtherApprovals = parseFloat(totalOtherApprovals.toFixed(3));
    totalFoodApprovals = parseFloat(totalFoodApprovals.toFixed(3));
    totalCustoms = parseFloat(totalCustoms.toFixed(3));
    totalStamp = parseFloat(totalStamp.toFixed(3));
    totalValue = parseFloat(totalValue.toFixed(3));

    bodyArray70.push([
      { text: '', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: '', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Total Amount', bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalNas.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalGlobal.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalLabourandTruck.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalApproval.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalHandlingForkCharges.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalOtherCharges.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalSpecialApprovals.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalOtherApprovals.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalFoodApprovals.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalCustoms.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalStamp.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: totalValue.toFixed(3), bold: true, fontSize: 6.1, alignment: 'center', border: [true, true, true, true], borderColor: ['#808080', '#808080', '#808080', '#808080'] },

    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [20, 34, 34, 34, 85, 45, 34, 34, 34, 34, 34, 40, 34, 34, 34, 34, 34, 43, '*'],
          body: bodyArray70,
        },
      },
    )
    let bodyArray73: any[] = [];
    bodyArray73.push([
      { text: 'Prepared By:', alignment:'left', margin: [0, 10, 0, 0],  bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].createdBy, alignment:'left', margin: [0, 10, 0, 0],  bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: 'Approved By:', alignment:'right', margin: [0, 10, 0, 0],  bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
      { text: result[0].approvedBy, alignment:'right', margin: [0, 10, 0, 0],  bold: true, fontSize: 9, border: [false, false, false, false], borderColor: ['#808080', '#808080', '#808080', '#808080'] },
    ]);
    dd.content.push(
      {
        table: {
          headerRows: 1,
          widths: [70, '*', 70, 70],
          body: bodyArray73,
        },
      }, '\n'
    )

    //pdfMake.createPdf(dd).open();
    const pdfDocGenerator = pdfMake.createPdf(dd);
    pdfDocGenerator.getBlob((blob) => {
      var file = new File([blob], 'Costing_Sheet' + ".pdf");
      var filepath = 'customsCosting/' + result[0].costCenter + '/';
      if (file) {
        this.ConsignmentService.uploadsinglefile(file, filepath).subscribe((resp: any) => {
        });
      }
    });
  }

}
