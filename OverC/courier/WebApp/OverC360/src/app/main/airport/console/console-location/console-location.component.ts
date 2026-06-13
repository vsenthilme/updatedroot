import { Component, ViewChild } from '@angular/core';
import { ConsoleService } from '../console.service';
import { DatePipe, Location } from '@angular/common';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { CustomTableComponent } from '../../../../common-dialog/custom-table/custom-table.component';
import { DeleteComponent } from '../../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { DynamicFieldSelectionComponent } from '../console-edit/dynamic-field-selection/dynamic-field-selection.component';
import * as ExcelJS from 'exceljs';

@Component({
  selector: 'app-console-location',
  templateUrl: './console-location.component.html',
  styleUrl: './console-location.component.scss'
})
export class ConsoleLocationComponent {

  
  
  consoleTable: any[] = [];
  selectedConsole: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ConsoleService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private spin: NgxSpinnerService,
  ) { }

  fullDate: any;
  today: any;
  pageToken: any;
  ngOnInit() {
    
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Mid Mile', 'Console'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.fill(this.pageToken.line);
  }

  callTableHeader() {
    this.cols = [
      
      { field: 'consoleId', header: 'Console ID' ,format:'hyperLink' },
      { field: 'totalNoOfPieces', header: 'No Of Pieces'},
      { field: 'totalSumOfWeights', header: 'Gross Weight' },
      { field: 'natureOfGoods', header: 'Nature of Goods' },
      { field: 'masterAirwayBill', header: 'MAWB' },
    ];
    this.target = [
      

    ];
  }
  
  
  fill(line: any) {
  let obj: any = {};
  obj.languageId = this.auth.languageId;
  obj.companyId = this.auth.companyId;
  obj.partnerMasterAirwayBill = this.pageToken.line.partnerMasterAirwayBill;

  this.service.searchLocation([obj]).subscribe({
    next: (res: any) => {
      this.consoleTable = res;
      this.spin.hide();
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }
  })
}

  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res: any) => {
          this.consoleTable = res;
          this.spin.hide();
        }, error: (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 1000);
  }

  onChange() {
    const choosen = this.selectedConsole[this.selectedConsole.length - 1];
    this.selectedConsole.length = 0;
    this.selectedConsole.push(choosen);
  }
  openEdit(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedConsole = linedata;
    }
    if (this.selectedConsole.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any Row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsole[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/console-edit/' + paramdata]);
    }
  }

  createLocation() {

    const dialogRef = this.dialog.open(DynamicFieldSelectionComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'Location', code: this.selectedConsole },

    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(this.selectedConsole)
      if (result) {
        this.selectedConsole.forEach(x => x['location'] = result);
        this.spin.show();
        this.service.createLocation(this.selectedConsole).subscribe({
          next: (res) => {
            this.downloadLocation(res);
            this.spin.hide();
          }, error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        })
      }
    });
  }

  downloadLocation(res: any) {

    const locationColumns = [
      { header: 'S No', field: 'partnerMasterAirwayBill', format: 'number' },
      { header: 'Console ID', field: 'consoleId' },
      { header: 'NO OF PCS', field: 'totalNoOfPieces' },
      { header: 'NATURE OF GOODS', field: 'natureOfGoods' },
      { header: 'ORIGIN', field: 'airportOriginCode' },
      { header: 'CONSIGNEE NAME', field: 'consigneeName' },
      { header: 'GROSS WT.', field: 'totalSumOfWeights' },
      { header: 'MAWB', field: 'partnerMasterAirwayBill' },
      { header: 'LOCATION ', field: 'location' }
    ];

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet(`LOCATION`);
    const currentDate = new Date();
    const worksheetPromises = [];
    const newRow = {
      'Origin Code': '',
      'Shipper': 'Operator',
      'WT KG': 'IW EXPRESS',
      'PCS': '',
      'Description': '',
      'Consignee Name': '',
      'Currency': 'Date',
      'Value': this.datePipe.transform(currentDate, 'dd-MM-yyyy'),
      'Customs KD': '',
    };

    const headerRowFirst = worksheet.addRow(Object.values(newRow));

    headerRowFirst.eachCell((cell, index) => {
      cell.font = {
        bold: true,
        color: { argb: '0000' }, // White text color
      };
      cell.border = {
        top: { style: 'thin' },
        left: { style: 'thin' },
        bottom: { style: 'thin' },
        right: { style: 'thin' },
      };
    });


    const headerRow = worksheet.addRow(Object.values(locationColumns.map(col => col.header)));

    headerRow.eachCell((cell, index) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'ff0000' }, // Replace with your desired background color
      };
      cell.font = {
        bold: true,
        color: { argb: '0000' }, // White text color
      };
      cell.border = {
        top: { style: 'thin' },
        left: { style: 'thin' },
        bottom: { style: 'thin' },
        right: { style: 'thin' },
      };
    });

    res.forEach((item: any, index: any) => {
      const exportItem: any = {};
      locationColumns.forEach(col => {
        if (col.format == 'number') {
          exportItem[col.header] = index + 1;
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      const cellRow = worksheet.addRow(Object.values(exportItem));
      cellRow.eachCell({ includeEmpty: true }, (cell, index) => {
        cell.border = {
          top: { style: 'thin' },
          left: { style: 'thin' },
          bottom: { style: 'thin' },
          right: { style: 'thin' },
        };
      });
    });

    // Call ExcelService to export data to Excel
    worksheetPromises.push(worksheet);

    workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

      // Create a temporary anchor element
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.style.display = 'none';
      a.href = url;
      a.download = `LOCATION_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
      document.body.appendChild(a);

      // Simulate click to trigger download
      a.click();

      // Clean up
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    });
  }

  panelOpenState = false;
  back() {
    this.location.back();
  }
  downloadExcel() {
    const exportData = this.selectedConsole.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }

      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Bill Mode');
  }

  saveCCR() {
    if (this.selectedConsole.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }

    
    const consoleID = this.selectedConsole.map(item => item.consoleId);
    this.spin.show();
    this.service.search({ consoleId: consoleID, companyId: [this.auth.companyId] }).subscribe({
      next: (res: any) => {
        this.service.createCCR(res).subscribe({
          next: (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Updated',
              key: 'br',
              detail: res[0].partnerHouseAirwayBill + ' has been created successfully',
            });
            this.spin.hide();
            this.downloadCCR(res);
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })


  }

  downloadCCR(res: any) {
    const invoicesColumns = [
      { header: 'Airway Bill No', field: 'partnerHouseAirwayBill' },
      { header: 'Consignee Name', field: 'consigneeName' },
      { header: 'Consignee Civil ID', field: 'consigneeCivilId' },
      { header: 'Invoice Number', field: '' },
      { header: 'Invoice Date', field: 'invoiceDate' },
      { header: 'Invoice Type', field: 'invoiceType' },
      { header: 'Currency', field: 'currency' },
      { header: 'Invoice Supplier Name', field: 'shipperName' },
      { header: 'Freight Currency', field: 'consignmentLocalId' },
      { header: 'Freight Charges', field: 'iata' },
      { header: 'Country Of Supply', field: 'countryOfOrigin' }
    ];

    const invoiceItemsColumns = [
      { header: 'BillNumber', field: 'partnerHouseAirwayBill' },
      { header: 'Invoice Number', field: '' },
      { header: 'HSCode', field: 'hsCode' },
      { header: 'GoodsDescription', field: 'goodsDescription' },
      { header: 'Country Of Origin', field: 'countryOfOrigin' },
      { header: 'Manufacturer', field: 'manufacturer' },
      { header: 'No Of Packages', field: 'noOfPieces' },
      { header: 'Item Total Price', field: 'consignmentValue' },
      { header: 'Package Type', field: 'packageType' },
      { header: 'Quantity', field: 'totalQuantity' },
      { header: 'Net Weight', field: 'netWeight' },
      { header: 'Gross Weight', field: 'grossWeight' },
      { header: 'Is Exempted', field: 'isExempted' },
      { header: 'Exemption For', field: 'exemptionFor' },
      { header: 'Exemption Beneficiary', field: 'exemptionBeneficiary' },
      { header: 'Exemption Reference', field: 'exemptionReference' }
    ];

    const groupedByConsoleId = this.groupBy(res, 'consoleId');
    const currentDate = new Date();

    Object.keys(groupedByConsoleId).forEach(consoleId => {
      const consoleData = groupedByConsoleId[consoleId];
      const workbook = new ExcelJS.Workbook();

      // Add Invoices Sheet
      const worksheetInvoices = workbook.addWorksheet(`INVOICES-${1}`);
      const headerRow = worksheetInvoices.addRow(Object.values(invoicesColumns.map(col => col.header)));

      // Style Header Row
      headerRow.eachCell((cell, colNumber) => {
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: '8EA9DB' } // Light Ice Blue color
        };
        cell.font = {
          bold: true,
          color: { argb: 'FFFFFF' } // Black text color
        };
        cell.border = {
          top: { style: 'thin' },
          left: { style: 'thin' },
          bottom: { style: 'thin' },
          right: { style: 'thin' },
        };
      });

      // Add data rows
      consoleData.forEach((item: any) => {
        const row = worksheetInvoices.addRow(invoicesColumns.map(col => item[col.field] || ''));
        row.eachCell({ includeEmpty: true }, (cell, colNumber) => {
          cell.border = {
            top: { style: 'thin' },
            left: { style: 'thin' },
            bottom: { style: 'thin' },
            right: { style: 'thin' },
          };
          // Optional: add alternate row coloring
          if (row.number % 2 === 0) {
            cell.fill = {
              type: 'pattern',
              pattern: 'solid',
              fgColor: { argb: 'DCE6F1' } // Light Gray for alternate rows
            };
          }
        });
      });

      // Add Invoice Items Sheet
      const worksheetInvoiceItems = workbook.addWorksheet(`INVOICEITEM-${1}`);
      const headerRow1 = worksheetInvoiceItems.addRow(Object.values(invoiceItemsColumns.map(col => col.header)));

      // Style Header Row
      headerRow1.eachCell((cell, colNumber) => {
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: '8EA9DB' } // Light Ice Blue color
        };
        cell.font = {
          bold: true,
          color: { argb: 'FFFFFF' } // Black text color
        };
        cell.border = {
          top: { style: 'thin' },
          left: { style: 'thin' },
          bottom: { style: 'thin' },
          right: { style: 'thin' },
        };
      });

      // Add data rows
      consoleData.forEach((item: any) => {
        const row = worksheetInvoiceItems.addRow(invoiceItemsColumns.map(col => item[col.field] || ''));
        row.eachCell({ includeEmpty: true }, (cell, colNumber) => {
          cell.border = {
            top: { style: 'thin' },
            left: { style: 'thin' },
            bottom: { style: 'thin' },
            right: { style: 'thin' },
          };
          // Optional: add alternate row coloring
          if (row.number % 2 === 0) {
            cell.fill = {
              type: 'pattern',
              pattern: 'solid',
              fgColor: { argb: 'DCE6F1' } // Light Gray for alternate rows
            };
          }
        });
      });

      // Write to buffer and trigger download
      workbook.xlsx.writeBuffer().then((data) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = `CCR_${consoleId}_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      });
    });
  }
  groupBy(array: any[], key: string) {
    return array.reduce((result: { [x: string]: any[]; }, currentValue: { [x: string]: any; }) => {
      const groupKey = currentValue[key];
      if (!result[groupKey]) {
        result[groupKey] = [];
      }
      result[groupKey].push(currentValue);
      return result;
    }, {});
  }
}






