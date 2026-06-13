import { DatePipe } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { DeleteComponent } from '../../../../common-dialog/delete/delete.component';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { iwExpressLogo } from '../../../../config/pdfFonts';
import { AuthService } from '../../../../core/core';
import { ConsoleBulkComponent } from '../../../airport/console/console-bulk/console-bulk.component';
import { DynamicFieldSelectionComponent } from '../../../airport/console/console-edit/dynamic-field-selection/dynamic-field-selection.component';
import { ConsoleEditpopupComponent } from '../../../airport/console/console-editpopup/console-editpopup.component';
import { ConsoleTransferComponent } from '../../../airport/console/console-transfer/console-transfer.component';
import { ConsoleService } from '../../../airport/console/console.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { Location } from '@angular/common';
import { ConsignmentLabelComponent } from '../../../pdf/consignment-label/consignment-label.component';

@Component({
  selector: 'app-customs-calculation-report-lines',
  templateUrl: './customs-calculation-report-lines.component.html',
  styleUrl: './customs-calculation-report-lines.component.scss'
})
export class CustomsCalculationReportLinesComponent {
  active: number | undefined = 0;
  status: any[] = [];
  selectedConsole: any[] = [];
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsoleService,
    private numberRangeService: NumberrangeService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private label: ConsignmentLabelComponent,
    private location: Location
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  pageToken: any;
  numCondition: any;

  // form builder initialize
  form = this.fb.group({
    actualCurrency: [],
    actualValue: [],
    addIata: [],
    addInsurance: [],
    airportOriginCode: [],
    bondedId: [],
    calculatedTotalDuty: [],
    companyId: [this.auth.companyId],
    companyName: [],
    consigneeCivilId: [],
    consigneeName: [],
    consignmentCurrency: [],
    consignmentLocalId: [],
    consignmentValue: [],
    consignmentValueLocal: [],
    consoleGroupName: [],
    consoleId: [],
    consoleName: [],
    countryOfOrigin: [],
    createdBy: [],
    createdOn: [],
    currency: [],
    customsCurrency: [],
    customsInsurance: [],
    customsKd: [],
    customsValue: [],
    dduCharge: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    duty: [],
    dutyPercentage: [],
    exchangeRate: [],
    exemptionBeneficiary: [],
    exemptionFor: [],
    exemptionReference: [],
    expectedDuty: [],
    finalDestination: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    grossWeight: [],
    hawbTimeStamp: [],
    hawbType: [],
    hawbTypeDescription: [],
    hawbTypeId: [],
    hsCode: [],
    hubCode: [],
    iata: [],
    iataCharge: [],
    incoTerms: [],
    invoiceDate: [],
    invoiceNumber: [],
    invoiceSupplierName: [],
    invoiceType: [],
    isConsolidatedShipment: [],
    isExempted: [],
    isPendingShipment: [],
    isSplitBillOfLading: [],
    landedQuantity: [],
    languageDescription: [],
    languageId: [this.auth.languageId],
    manifestedGrossWeight: [],
    manifestedQuantity: [],
    netWeight: [],
    noOfPackageMawb: [],
    noOfPieceHawb: [],
    noOfPieces: [],
    notifyParty: [],
    partnerHouseAirwayBill: [],
    partnerId: [],
    partnerMasterAirwayBill: [],
    partnerName: [],
    partnerType: [],
    paymentType: [],
    pieceId: [],
    pieceTimeStamp: [],
    pieceType: [],
    pieceTypeDescription: [],
    pieceTypeId: [],
    primaryDo: [],
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
    secondaryDo: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipmentBagId: [],
    shipperId: [],
    shipperName: [],
    specialApprovalCharge: [],
    specialApprovalValue: [],
    subProductId: [],
    subProductName: [],
    tareWeight: [],
    totalQuantity: [],
    updatedBy: [],
    updatedOn: [],
    volume: [],
    statusText: [],
    statusId: [],

  });

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = 'required') {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  onChange() {
    const choosen = this.selectedConsole[this.selectedConsole.length - 1];
    this.selectedConsole.length = 0;
    this.selectedConsole.push(choosen);
  }
  nextNumber: any;
  pageFlow: any;
  tableStyle: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);
    this.dropdownlist();

    this.pageFlow = 'Customs Calculation Report';
    const dataToSend = ['Mid-Mile', 'Customs Calculation Report'];
    this.path.setData(dataToSend);
    this.consolidatedReportTableHeader();
    this.tableStyle = { 'width': '290rem' };

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();
    this.form.controls.partnerMasterAirwayBill.disable();
    this.form.controls.statusText.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.subProductId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
      this.form.controls.statusId.disable();
    }

  }


  cols: any[] = [];
  target: any[] = [];

  consolidatedReportTableHeader() {
    this.cols = [

      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB', showFooter: false },
      { field: 'houseAirwayBill', header: 'Consignment', showFooter: false },
      { field: 'incoTerm', header: 'Inco Terms', showFooter: false },
      { field: 'createdOn', header: 'Date', format: 'date', showFooter: false },
      { field: 'shipperName', header: 'Shipper/Customer', showFooter: false },
      // { field: 'createdOn', header: 'From ', format: 'date', showFooter: false },
      // { field: 'createdOn', header: 'To', format: 'date', showFooter: false },
      { field: 'consignmentValue', header: 'Declared Value', showFooter: false },
      { field: 'currency', header: 'Currency', showFooter: false },
      // { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: 'iata', header: 'IATA Charges', showFooter: false },
      { field: 'customsInsurance', header: 'Insurance', showFooter: false },
      { field: 'consignmentValueLocal', header: 'Customs Value KD', showFooter: false },
      { field: 'addIata', header: 'Adding IATA', showFooter: false },
      { field: 'addInsurance', header: 'Adding Insurance', showFooter: false },
      { field: 'customsValue', header: 'Final Customs value', showFooter: false },
      { field: 'duty', header: 'Customs Duty > 100', showFooter: false },
      { field: 'calculatedTotalDuty', header: 'Total Duty', showFooter: true },


    ];
    this.target = [
    ];
  }



  languageIdList: any[] = [];
  companyIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  addValues() { }

  customsCalculationArray: any[] = [];

  removeItem(index: number) {
    this.customsCalculationArray.splice(index, 1);
  }

  fill(line: any) {
    this.form.patchValue(line);
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.partnerMasterAirwayBill = [line.partnerMasterAirwayBill];
      this.service.search(obj).subscribe({
        next: (res: any) => {
          this.customsCalculationArray = res;
          this.customsCalculationArray = this.customsCalculationArray.map(item => ({
            ...item,
            duty: item.duty != null ? item.duty : 0,  //intialize 0 when duty is null
            consignmentValue: this.formatNumber(item.consignmentValue),
            consignmentValueLocal: this.formatNumber(item.consignmentValueLocal),
            addIata: this.formatNumber(item.addIata),
            addInsurance: this.formatNumber(item.addInsurance),
            customsValue: this.formatNumber(item.customsValue),
            calculatedTotalDuty: this.formatNumber(item.calculatedTotalDuty),
          }

          ));
          this.spin.hide();
        },

        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }, 500);
  }
  lineSentforFill: any;
  //format the decimal of values directly in result to 3 digits
  private formatNumber(value: any): number {

    const num = Number(value);
    return isNaN(num) ? 0 : parseFloat(num.toFixed(3));
  }
  calculateFooterTotal(field: string): number {
    let total = 0;
    this.customsCalculationArray.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }


  downloadExcel() {

    const customsCalculationReport = [
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB', showFooter: false },
      { field: 'houseAirwayBill', header: 'Consignment', showFooter: false },
      { field: 'incoTerm', header: 'Inco Terms', showFooter: false },
      { field: 'createdOn', header: 'Date', format: 'date', showFooter: false },
      { field: 'shipperName', header: 'Shipper/Customer', showFooter: false },
      // { field: 'createdOn', header: 'From ', format: 'date', showFooter: false },
      // { field: 'createdOn', header: 'To', format: 'date', showFooter: false },
      { field: 'consignmentValue', header: 'Declared Value', showFooter: false },
      { field: 'currency', header: 'Currency', showFooter: false },
      // { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: 'iata', header: 'IATA Charges', showFooter: false },
      { field: 'customsInsurance', header: 'Insurance', showFooter: false },
      { field: 'consignmentValueLocal', header: 'Customs Value KD', showFooter: false },
      { field: 'addIata', header: 'Adding IATA', showFooter: false },
      { field: 'addInsurance', header: 'Adding Insurance', showFooter: false },
      { field: 'customsValue', header: 'Final Customs value', showFooter: false },
      { field: 'duty', header: 'Customs Duty > 100', showFooter: false },
      { field: 'calculatedTotalDuty', header: 'Total Duty', showFooter: true },
    ];

    const exportData = this.customsCalculationArray.map(item => {
      const exportItem: any = {};
      customsCalculationReport.forEach(col => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Customs Calculation Report');
  }
  // download Excel function
  consoleManifest: any[] = [];
  invoices: any[] = [];
  invoiceItems: any[] = [];

  panelOpenState = false;
  back() {
    this.location.back();
  }

}




















// downloadCCR(res: any) {


//   const consoleManifestColumns = [
//     { field: 'partnerMasterAirwayBill', header: '#', format: 'number' },
//     { field: 'partnerHouseAirwayBill', header: 'AWB' },
//     { field: 'countryOfOrigin', header: 'Origin' },
//     { field: 'airportOriginCode', header: 'Origin Code' },
//     { field: 'shipperName', header: 'Shipper' },
//     { field: 'grossWeight', header: 'WT KG' },
//     { field: 'noOfPieces', header: 'PCS' },
//     { field: 'description', header: 'Description' },
//     { field: 'consigneeName', header: 'Consignee Name' },
//     { field: 'currency  ', header: 'Currency' },
//     { field: 'consignmentValue', header: 'Value' },
//     { field: 'customsValue', header: 'Customs KD' },
//     { field: 'iata', header: 'IATA KD' },
//     { field: 'hsCode', header: 'HS Code' },
//   ];

//   const invoicesColumns = [
//     { header: 'Airway Bill No', field: 'partnerHouseAirwayBill' },
//     { header: 'Consignee Name', field: 'consigneeName' },
//     { header: 'Consignee Civil ID', field: 'consigneeCivilId' },
//     { header: 'Invoice Number', field: 'invoiceNumber' },
//     { header: 'Invoice Date', field: 'invoiceDate' },
//     { header: 'Invoice Type', field: 'invoiceType' },
//     { header: 'Currency', field: 'currency' },
//     { header: 'Invoice Supplier Name', field: 'invoiceSupplierName' },
//     { header: 'Freight Currency', field: 'consignmentLocalId' },
//     { header: 'Freight Charges', field: 'freightCharges' },
//     { header: 'Country Of Supply', field: 'countryOfOrigin' }
//   ];

//   const invoiceItemsColumns = [
//     { header: 'BillNumber', field: 'partnerHouseAirwayBill' },
//     { header: 'InvoiceNumber', field: 'invoiceNumber' },
//     { header: 'HSCode', field: 'hsCode' },
//     { header: 'GoodsDescription', field: 'goodsDescription' },
//     { header: 'Country Of Origin', field: 'countryOfOrigin' },
//     { header: 'Manufacturer', field: 'manufacturer' },
//     { header: 'No Of Packages', field: 'noOfPieces' },
//     { header: 'Item Total Price', field: 'consignmentValue' },
//     { header: 'Package Type', field: 'packageType' },
//     { header: 'Quantity', field: 'totalQuantity' },
//     { header: 'Net Weight', field: 'netWeight' },
//     { header: 'Gross Weight', field: 'grossWeight' },
//     { header: 'Is Exempted', field: 'isExempted' },
//     { header: 'Exemption For', field: 'exemptionFor' },
//     { header: 'Exemption Beneficiary', field: 'exemptionBeneficiary' },
//     { header: 'Exemption Reference', field: 'exemptionReference' }
//   ];

//   let index = 0;
//   const workbook = new ExcelJS.Workbook();
//   const currentDate = new Date();

//   const groupedByConsoleId = this.groupBy(res, 'consoleId');
//   for (const consoleId in groupedByConsoleId) {
//     if (groupedByConsoleId.hasOwnProperty(consoleId)) {
//       const consoleData = groupedByConsoleId[consoleId];

//       const worksheetConsole = workbook.addWorksheet(`CONSOLE-${index + 1}`);

//       // Add image to worksheet (assuming iwExpressLogo.headerLogo is your base64 image)
//       const base64Image1 = iwExpressLogo.headerLogo;
//       const logoId = workbook.addImage({
//         base64: base64Image1,
//         extension: 'png',
//       });
//       worksheetConsole.addImage(logoId, {
//         tl: { col: 4, row: 0 }, // Top-left position
//         ext: { width: 350, height: 100 }, // Width and height
//       });

//       // Skip 5 rows before adding headers and data
//       for (let i = 0; i < 4; i++) {
//         worksheetConsole.addRow([]); // Add empty rows to skip
//       }

//       // Add new row
//       const newRow = {
//         index: '',
//         partnerHouseAirwayBill: consoleData[0].consoleGroupName || '',
//         airportOriginCode: consoleData[0].consoleName || '',
//         countryOfOrigin: '',
//         shipperName: '',
//         grossWeight: '',
//         noOfPieces: '',
//         description: consoleId,
//         consigneeName: consoleData[0].partnerMasterAirwayBill,
//         currency: '',
//         consignmentValue: 'Date',
//         consignmentValueLocal: this.datePipe.transform(currentDate, 'dd-MM-yyyy'),
//         iata: '',
//         hsCode: '',
//       };
//       const headerRowFirst =  worksheetConsole.addRow(Object.values(newRow));
//       headerRowFirst.eachCell((cell, index) => {
//         cell.font = {
//           bold: true,
//           color: { argb: '0000' }, // White text color
//         };
//         cell.border = {
//           top: { style: 'thin' },
//           left: { style: 'thin' },
//           bottom: { style: 'thin' },
//           right: { style: 'thin' },
//         };
//       });

//       const headerRow = worksheetConsole.addRow(Object.values(consoleManifestColumns.map(col => col.header)));

//       headerRow.eachCell((cell, index) => {
//         cell.fill = {
//           type: 'pattern',
//           pattern: 'solid',
//           fgColor: { argb: '8EA9DB' }, // Replace with your desired background color
//         };
//         cell.font = {
//           bold: true,
//           color: { argb: '0000' }, // White text color
//         };
//         cell.border = {
//           top: { style: 'thin' },
//           left: { style: 'thin' },
//           bottom: { style: 'thin' },
//           right: { style: 'thin' },
//         };
//       });

//       // Add console data rows
//       consoleData.forEach((item: any, index: number) => {
//         const exportItem: any = {};
//         consoleManifestColumns.forEach(col => {
//           if (col.format == 'number') {
//             exportItem[col.header] = index + 1;
//           } else {
//             exportItem[col.header] = item[col.field];
//           }
//         });
//         const cellRow =   worksheetConsole.addRow(Object.values(exportItem));
//         cellRow.eachCell({ includeEmpty: true },(cell, index) => {
//           cell.border = {
//             top: { style: 'thin' },
//             left: { style: 'thin' },
//             bottom: { style: 'thin' },
//             right: { style: 'thin' },
//           };
//         });
//       });

//       const groupedByCcrId = this.groupBy(consoleData, 'ccrId');

//       for (const ccrId in groupedByCcrId) {
//         if (groupedByCcrId.hasOwnProperty(ccrId)) {
//           const ccrData = groupedByCcrId[ccrId];

//           const worksheetInvoices = workbook.addWorksheet(`INVOICES-${index + 1}`);


//           const headerRow = worksheetInvoices.addRow(Object.values(invoicesColumns.map(col => col.header)));

//           headerRow.eachCell((cell, index) => {
//             cell.fill = {
//               type: 'pattern',
//               pattern: 'solid',
//               fgColor: { argb: '8EA9DB' }, // Replace with your desired background color
//             };
//             cell.font = {
//               bold: true,
//               color: { argb: '0000' }, // White text color
//             };
//             cell.border = {
//               top: { style: 'thin' },
//               left: { style: 'thin' },
//               bottom: { style: 'thin' },
//               right: { style: 'thin' },
//             };
//           });

//           ccrData.forEach((item: any) => {
//             const exportItem: any = {};
//             invoicesColumns.forEach(col => {
//                 exportItem[col.header] = item[col.field];
//             });

//             const cellRow =  worksheetInvoices.addRow(Object.values(exportItem));
//             cellRow.eachCell({ includeEmpty: true },(cell, index) => {
//               cell.border = {
//                 top: { style: 'thin' },
//                 left: { style: 'thin' },
//                 bottom: { style: 'thin' },
//                 right: { style: 'thin' },
//               };
//             });
//           });

//           const worksheetInvoiceItems = workbook.addWorksheet(`INVOICEITEM-${index + 1}`);

//           const headerRow1 = worksheetInvoiceItems.addRow(Object.values(invoiceItemsColumns.map(col => col.header)));

//           headerRow1.eachCell((cell, index) => {
//             cell.fill = {
//               type: 'pattern',
//               pattern: 'solid',
//               fgColor: { argb: '8EA9DB' }, // Replace with your desired background color
//             };
//             cell.font = {
//               bold: true,
//               color: { argb: '0000' }, // White text color
//             };
//             cell.border = {
//               top: { style: 'thin' },
//               left: { style: 'thin' },
//               bottom: { style: 'thin' },
//               right: { style: 'thin' },
//             };
//           });

//           ccrData.forEach((item: any) => {
//             const exportItem: any = {};
//             invoiceItemsColumns.forEach(col => {
//                 exportItem[col.header] = item[col.field];
//             });
//             const cellRow =  worksheetInvoiceItems.addRow(Object.values(exportItem));
//             cellRow.eachCell({ includeEmpty: true },(cell, index) => {
//               cell.border = {
//                 top: { style: 'thin' },
//                 left: { style: 'thin' },
//                 bottom: { style: 'thin' },
//                 right: { style: 'thin' },
//               };
//             });
//           });
//         }
//       }
//     }
//     index++;
//   }

//   workbook.xlsx.writeBuffer().then((data) => {
//     const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

//     // Create a temporary anchor element
//     const url = window.URL.createObjectURL(blob);
//     const a = document.createElement('a');
//     a.style.display = 'none';
//     a.href = url;
//     a.download = `CCR_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
//     document.body.appendChild(a);

//     // Simulate click to trigger download
//     a.click();

//     // Clean up
//     window.URL.revokeObjectURL(url);
//     document.body.removeChild(a);
//   });
// }
