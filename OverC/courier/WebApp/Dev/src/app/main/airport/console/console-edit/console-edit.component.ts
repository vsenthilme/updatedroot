import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { SubProductsValuesComponent } from '../../../id-masters/sub-product/sub-products-values/sub-products-values.component';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { ConsoleService } from '../console.service';
import { ConsoleEditpopupComponent } from '../console-editpopup/console-editpopup.component';
import { ConsoleTransferComponent } from '../console-transfer/console-transfer.component';
import { DatePipe, Location } from '@angular/common';
import * as XLSX from 'xlsx';
import { ConsoleBulkComponent } from '../console-bulk/console-bulk.component';
import { DynamicFieldSelectionComponent } from './dynamic-field-selection/dynamic-field-selection.component';
import { ConsignmentLabelComponent } from '../../../pdf/consignment-label/consignment-label.component';
import { format } from 'util';
import { DeleteComponent } from '../../../../common-dialog/delete/delete.component';
import { Workbook } from 'exceljs';
import * as ExcelJS from 'exceljs';
import { iwExpressLogo } from "../../../../config/pdfFonts";
import { CostingSheetBulkupdateComponent } from '../../costing-sheet/costing-sheet-bulkupdate/costing-sheet-bulkupdate.component';
@Component({
  selector: 'app-console-edit',
  templateUrl: './console-edit.component.html',
  styleUrl: './console-edit.component.scss'
})
export class ConsoleEditComponent {
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
    companyId: [],
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
    languageId: [],
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

    if (this.pageToken.report == true) {
      if (this.pageToken.module == 'unconsolidated') {
        this.pageFlow = 'Unconsolidated Shipments';
        const dataToSend = ['Mid-Mile', 'UnConsolidated Shipments Tracking'];
        this.path.setData(dataToSend);
        this.unconsolidatedReportTableHeader();
        this.tableStyle = { 'width': '100rem' };
      }
      if (this.pageToken.module == 'consolidated') {
        this.pageFlow = 'Consolidated Shipments';
        const dataToSend = ['Mid-Mile', 'Consolidated Shipments Tracking'];
        this.path.setData(dataToSend);
        this.consolidatedReportTableHeader();
        this.tableStyle = { 'width': '290rem' };
      }
    } else {
      this.pageFlow = 'Console - ' + this.pageToken.pageflow;
      const dataToSend = ['Mid-Mile', 'Console', this.pageToken.pageflow];
      this.path.setData(dataToSend);
      this.callTableHeader();
      this.tableStyle = { 'width': '290rem' };
    }

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
  unconsolidatedReportTableHeader() {
    this.cols = [
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'houseAirwayBill', header: 'Consignment ID' },
      { field: 'pieceId', header: 'Piece ID' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'hawbTypeId', header: 'Action' },
      { field: 'hawbTypeDescription', header: 'Action Name' },
      { field: 'hawbTimeStamp', header: 'Scanned On', format: 'date' },
      { field: 'createdBy', header: 'Scanned Officer' },
    ];
    this.target = [
    ];
  }
  consolidatedReportTableHeader() {
    this.cols = [
      { field: 'consoleId', header: 'Console No' },
      { field: 'consoleName', header: 'Console Name' },
      { field: 'consoleGroupName', header: 'Console Group' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'hawbTypeDescription', header: 'Event' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date' },
      { field: 'description', header: 'Commodity' },
      { field: 'noOfPieces', header: 'No of Piece' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'countryOfOrigin', header: 'Origin' },
      { field: 'grossWeight', header: 'Weight' },
      { field: 'airportOriginCode', header: 'Airport Origin Code' },
      { field: 'hsCode', header: 'HS Code' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'consignmentValue', header: 'Consignment Value' },
      { field: 'currency', header: 'Consignment Currency' },
      { field: 'customsValue', header: 'Customs Value' },
      { field: 'iata', header: 'IATA Charges' },
      { field: 'isExempted', header: 'Is Exempted' },
      { field: 'exemptionFor', header: 'Exemption For' },
      { field: 'exemptionBeneficiary', header: 'Exemption Beneficiary' },
      { field: 'exemptionReference', header: 'Exemption Reference' },
      { field: 'ccrId', header: 'CCR ID' },
      { field: 'customsCcrNo', header: 'Custom CCR No' },
      { field: 'primaryDo', header: 'Primary DO' },
      { field: 'secondaryDo', header: 'Secondary DO' },
      { field: 'totalDuty', header: 'Bayan HV' },
      { field: 'calculatedTotalDuty', header: 'Calculated Duty' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
    ];
  }
  callTableHeader() {
    this.cols = [
      { field: 'consoleId', header: 'Console No' },
      { field: 'consoleName', header: 'Console Name' },
      { field: 'consoleGroupName', header: 'Console Group' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'hawbTypeDescription', header: 'Event' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date' },
      { field: 'description', header: 'Commodity' },
      { field: 'noOfPieces', header: 'No of Piece' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'countryOfOrigin', header: 'Origin' },
      { field: 'grossWeight', header: 'Weight' },
      { field: 'airportOriginCode', header: 'Airport Origin Code' },
      { field: 'hsCode', header: 'HS Code' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'consignmentValue', header: 'Consignment Value' },
      { field: 'currency', header: 'Consignment Currency' },
      { field: 'consignmentValueLocal', header: 'Customs Value' },
      { field: 'iata', header: 'IATA Charges' },
      { field: 'isExempted', header: 'Is Exempted' },
      { field: 'exemptionFor', header: 'Exemption For' },
      { field: 'exemptionBeneficiary', header: 'Exemption Beneficiary' },
      { field: 'exemptionReference', header: 'Exemption Reference' },
      { field: 'ccrId', header: 'CCR ID' },
      { field: 'customsCcrNo', header: 'Custom CCR No' },
      { field: 'primaryDo', header: 'Primary DO' },
      { field: 'secondaryDo', header: 'Secondary DO' },
      { field: 'totalDuty', header: 'Bayan HV' },
      { field: 'calculatedTotalDuty', header: 'Calculated Duty' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
    ];
  }


  languageIdList: any[] = [];
  companyIdList: any[] = [];

  dropdownlist() {
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  addValues() { }

  subProductArray: any[] = [];



  removeItem(index: number) {
    this.subProductArray.splice(index, 1);
  }

  fill(line: any) {
    setTimeout(() => {
      this.form.patchValue(line);
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.partnerMasterAirwayBill = [line.partnerMasterAirwayBill];
      obj.consoleId = [line.consoleId];
      obj.unconsolidatedFlag = this.pageToken.module == 'consolidated' ? [0] : this.pageToken.module == 'unconsolidated' ? [1] : null;

      if (this.pageToken.report == true && this.pageToken.module == 'unconsolidated') {
        this.service.searchUnconsole(obj).subscribe({
          next: (res: any) => {
            this.subProductArray = res;
            this.spin.hide();
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
      } else {
        this.service.search(obj).subscribe({
          next: (res: any) => {
            this.subProductArray = res;
            this.spin.hide();
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
      }
    }, 2000);
  }
  lineSentforFill: any;
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      if (this.selectedConsole.length == 0) {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          key: 'br',
          detail: 'Kindly select any row',
        });
        return;
      }
      this.spin.show();
      this.selectedConsole.forEach((x: any) => {
        x.eventCode = 10;
      });
      const a = this.subProductArray.filter(x => x.eventCode == 10);
      this.service.updateSingle(this.selectedConsole).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].consoleId + ' has been updated successfully',
          });
          if (this.subProductArray.length == a.length) {
            this.router.navigate(['/main/airport/console']);
          } else {
            setTimeout(() => {
              this.fill(this.pageToken.line);
            }, 2000);
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  createConsole() {
    if (this.selectedConsole.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }

    this.service.CreateManual(this.selectedConsole).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Created',
          key: 'br',
          detail: res[0].consoleId + ' has been created successfully',
        });
        this.fill(this.pageToken.line);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
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

    this.service.createCCR(this.selectedConsole).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: res[0].partnerHouseAirwayBill + ' has been created successfully',
        });
        this.downloadCCR(res);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  editItem(data: any, item: any): void {
    const dialogRef = this.dialog.open(ConsoleEditpopupComponent, {
      disableClose: true,
      width: '70%',
      //height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { pageflow: data, code: item },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.fill(this.pageToken.line);
      }
    });
  }

  transfer() {
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    }
    const dialogRef = this.dialog.open(ConsoleTransferComponent, {
      disableClose: true,
      width: '70%',
      height: '40%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.selectedConsole,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.fill(this.pageToken.line)
      }
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

  // download Excel function
  consoleManifest: any[] = [];
  invoices: any[] = [];
  invoiceItems: any[] = [];

  downloadConsole() {
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }

    const cols = [
      { field: 'partnerMasterAirwayBill', header: '#', format: 'number' },
      { field: 'partnerHouseAirwayBill', header: 'AWB' },
      { field: 'countryOfOrigin', header: 'Origin' },
      { field: 'airportOriginCode', header: 'Origin Code' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'grossWeight', header: 'WT KG' },
      { field: 'noOfPieces', header: 'PCS' },
      { field: 'description', header: 'Description' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'currency', header: 'Currency' },
      { field: 'consignmentValue', header: 'Value' },
      { field: 'consignmentValueLocal', header: 'Customs KD' },
      { field: 'iata', header: 'IATA KD' },
      { field: 'hsCode', header: 'HS Code' },
    ];

    const groupedByConsoleId = this.groupBy(this.selectedConsole, 'consoleId');

    const workbook = new ExcelJS.Workbook();
    const currentDate = new Date();
    const worksheetPromises = [];
    let sheetIndex = 1;

    for (const consoleId in groupedByConsoleId) {
      if (groupedByConsoleId.hasOwnProperty(consoleId)) {
        const consoleData = groupedByConsoleId[consoleId];

        const worksheet = workbook.addWorksheet(`${sheetIndex}`);
        sheetIndex++;

        // Add image to worksheet (assuming iwExpressLogo.headerLogo is your base64 image)
        const base64Image1 = iwExpressLogo.headerLogo;
        const logoId = workbook.addImage({
          base64: base64Image1,
          extension: 'png',
        });
        worksheet.addImage(logoId, {
          tl: { col: 4, row: 0 }, // Top-left position
          ext: { width: 350, height: 100 }, // Width and height
        });

        // Skip 5 rows before adding headers and data
        for (let i = 0; i < 4; i++) {
          worksheet.addRow([]); // Add empty rows to skip
        }

        // Add headers


        // New row to be added before console data
        const newRow = {
          '#': '',
          'AWB': consoleData[0].consoleGroupName != null ? consoleData[0].consoleGroupName : '',
          'Origin': consoleData[0].consoleName != null ? consoleData[0].consoleName : '',
          'Origin Code': '',
          'Shipper': '',
          'WT KG': '',
          'PCS': '',
          'Description': consoleId,
          'Consignee Name': consoleData[0].partnerMasterAirwayBill,
          'Currency': '',
          'Value': 'Date',
          'Customs KD': this.datePipe.transform(currentDate, 'dd-MM-yyyy'),
          'IATA KD': '',
          'HS Code': '',
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
        const headerRow = worksheet.addRow(Object.values(cols.map(col => col.header)));

        headerRow.eachCell((cell, index) => {
          cell.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: '8EA9DB' }, // Replace with your desired background color
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
        // Map console data and convert to rows
        consoleData.forEach((item: any, index: any) => {
          const exportItem: any = {};
          cols.forEach(col => {
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

        worksheetPromises.push(worksheet);
      }
    }
    // Prepare file for download
    workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

      // Create a temporary anchor element
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.style.display = 'none';
      a.href = url;
      a.download = `CONSOLE_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
      document.body.appendChild(a);

      // Simulate click to trigger download
      a.click();

      // Clean up
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    });
  }

  outScan: any[] = [];
  callHubCode() {
    const dialogRef = this.dialog.open(DynamicFieldSelectionComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'Hub Code', code: this.selectedConsole },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.outScan = [];

        this.selectedConsole.forEach(x => {
          for (let i = 0; i < 2; i++) {
            // Create a new object or clone x
            let newItem = { ...x }; // Using spread operator to clone x

            // Set properties based on i
            newItem['hawbType'] = (i === 0) ? 'event' : 'status';
            newItem['hawbTypeId'] = (i === 0) ? '10' : '9';
            newItem['hubCode'] = result;

            // Push newItem into outScan
            this.outScan.push(newItem);

          }
        });
        this.updateConsole('outscan');
      }
    });
  }
  selectedCostingSheet: any[] = [];
  updateConsole(event: any) {

    if (this.selectedConsole.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }

    if (event == 'self') {
      this.selectedConsole.forEach(x => {
        x['hawbType'] = 'status';
        x['hawbTypeId'] = '8';
      })
      this.selectedCostingSheet = this.selectedConsole;
      this.selectedCostingSheet.forEach(x => {
        x['costCenter'] = x.partnerMasterAirwayBill
        x['costDescription'] = 'Document Handling';
      })
      console.log(this.selectedCostingSheet)
      const dialogRef = this.dialog.open(CostingSheetBulkupdateComponent, {
        disableClose: true,
        width: '70%',
        maxWidth: '80%',
        position: { top: '6.5%', left: '30%' },
        data: { title: 'Custom Calculation', code: this.selectedCostingSheet },
      });

      dialogRef.afterClosed().subscribe((result) => {
        //this.initialCall();
      });
    }
    if (event == 'cleared') {
      this.selectedConsole.forEach(x => {
        x['hawbType'] = 'status';
        x['hawbTypeId'] = '7';
      })
    }
    if (event == 'outscan') {
      this.selectedConsole = [];
      this.selectedConsole = this.outScan;
    }
    this.spin.show();
    this.service.UpdateStatusforConsole(this.selectedConsole).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: res[0].consoleId + ' has been updated successfully',
        });
        this.spin.hide();
        setTimeout(() => {
          this.fill(this.pageToken.line);
          this.selectedConsole = [];
        }, 2000);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }
  updateGateway(data: any) {
    if (data.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }


    this.spin.show();
    this.service.UpdateGatewayScan(data).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: res[0].consoleId + ' has been updated successfully',
        });
        this.spin.hide();
        setTimeout(() => {
          this.fill(this.pageToken.line);
          this.selectedConsole = [];
        }, 2000);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  generateLabel() {
    this.uniquePieceId = [];
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    const pieceId = this.selectedConsole.map(item => item.pieceId);
    this.label.getResultLabel(pieceId)
    // this.service.search(obj).subscribe({
    //   next: (res: any) => {
    //     this.uniquePieceId = this.cs.removeDuplicatesFromArrayList(res, 'pieceId');
    //     const pieceId = this.uniquePieceId.map(item => item.pieceId);
    //     this.label.getResultLabel(pieceId)
    //   },
    //   error: (err) => {
    //     this.spin.hide();
    //     this.cs.commonerrorNew(err);
    //   },
    // });
  }
  houseAirwayBill: any;

  generateInvoice() {
    this.uniqueHouseAirwayBill = [];
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    let obj: any = {};
    const houseAirwayBill = this.selectedConsole.map(item => item.houseAirwayBill);
    this.label.getResultInvoice(houseAirwayBill)
    // this.service.search(obj).subscribe({
    //   next: (res: any) => {
    //     if (res) {
    //       this.uniqueHouseAirwayBill = this.cs.removeDuplicatesFromArrayList(res, 'houseAirwayBill');
    //       const houseAirwayBill = this.uniqueHouseAirwayBill.map(item => item.houseAirwayBill);
    //       this.label.getResultInvoice(houseAirwayBill)
    //     }
    //   },
    //   error: (err) => {
    //     this.spin.hide();
    //     this.cs.commonerrorNew(err);
    //   },
    // });
  }


  uniquePieceId: any[] = [];
  uniqueHouseAirwayBill: any[] = [];
  generateMerge() {
    this.uniqueHouseAirwayBill = [];
    this.uniquePieceId = [];
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    let obj: any = {};
    const pieceId = this.selectedConsole.map(item => item.pieceId);
    const houseAirwayBill = this.selectedConsole.map(item => item.houseAirwayBill);
    this.label.generateMutiple(pieceId, houseAirwayBill)
    // this.service.search(obj).subscribe({
    //   next: (res: any) => {
    //     this.uniquePieceId = this.cs.removeDuplicatesFromArrayList(res, 'pieceId');
    //     this.uniqueHouseAirwayBill = this.cs.removeDuplicatesFromArrayList(res, 'houseAirwayBill');
    //     const pieceId = this.uniquePieceId.map(item => item.pieceId);
    //     const houseAirwayBill = this.uniqueHouseAirwayBill.map(item => item.houseAirwayBill);
    //     this.label.generateMutiple(pieceId, houseAirwayBill)
    //   },
    //   error: (err) => {
    //     this.spin.hide();
    //     this.cs.commonerrorNew(err);
    //   },
    // });

  }



  @ViewChild('fileInput1') fileInput1!: ElementRef;
  uploadBayan() {
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    this.fileInput1.nativeElement.click();
  }
  selectedFiles: File | null = null;
  onFileSelectedBayan(event: any): void {
    const filePath = '/' + this.selectedConsole[0].consoleId + '/';
    const file: File = event.target.files[0];
    this.selectedFiles = file;
    this.spin.show();
    this.service.uploadBayan(this.selectedFiles, filePath).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Uploaded',
          key: 'br',
          detail: 'File uploaded successfully',
        });
        this.fill(this.pageToken.line);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }

  panelOpenState = false;
  back() {
    this.location.back();
  }


  updateBulk() {
    if (this.selectedConsole.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }
    const dialogRef = this.dialog.open(ConsoleBulkComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'Console', code: this.selectedConsole },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.fill(this.pageToken.line);
    });
  }


  deleteDialog() {
    if (this.selectedConsole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedConsole, module: 'Console', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedConsole);
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    console.log(lines)
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: 'Selected records deleted successfully' });
        this.spin.hide();
        this.fill(this.pageToken.line);
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
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


  // createLocation() {

  //   const dialogRef = this.dialog.open(DynamicFieldSelectionComponent, {
  //     disableClose: true,
  //     width: '70%',
  //     maxWidth: '80%',
  //     position: { top: '6.5%', left: '30%' },
  //     data: { title: 'Location', code: this.selectedConsole },
  //   });

  //   dialogRef.afterClosed().subscribe((result) => {
  //     if (result) {
  //       this.selectedConsole.forEach(x => x['location'] = result);
  //       this.spin.show();
  //       this.service.createLocation(this.selectedConsole).subscribe({
  //         next: (res) => {
  //           this.downloadLocation(res);
  //           this.spin.hide();
  //         }, error: (err) => {
  //           this.spin.hide();
  //           this.cs.commonerrorNew(err);
  //         },
  //       })
  //     }
  //   });
  // }


  // downloadLocation(res: any) {

  //   const locationColumns = [
  //     { header: 'S No', field: 'partnerMasterAirwayBill', format: 'number' },
  //     { header: 'Console ID', field: 'consoleId' },
  //     { header: 'NO OF PCS', field: 'totalNoOfPieces' },
  //     { header: 'NATURE OF GOODS', field: 'natureOfGoods' },
  //     { header: 'ORIGIN', field: 'origin' },
  //     { header: 'CONSIGNEE NAME', field: 'consigneeName' },
  //     { header: 'GROSS WT.', field: 'totalSumOfWeights' },
  //     { header: 'MAWB', field: 'partnerMasterAirwayBill' },
  //     { header: 'LOCATION ', field: 'location' }
  //   ];

  //   const workbook = new ExcelJS.Workbook();
  //   const worksheet = workbook.addWorksheet(`LOCATION`);
  //   const currentDate = new Date();
  //   const worksheetPromises = [];
  //   const newRow = {
  //     'Origin Code': '',
  //     'Shipper': 'Operator',
  //     'WT KG': 'IW EXPRESS',
  //     'PCS': '',
  //     'Description': '',
  //     'Consignee Name': '',
  //     'Currency': 'Date',
  //     'Value': this.datePipe.transform(currentDate, 'dd-MM-yyyy'),
  //     'Customs KD': '',
  //   };

  //   const headerRowFirst = worksheet.addRow(Object.values(newRow));

  //   headerRowFirst.eachCell((cell, index) => {
  //     cell.font = {
  //       bold: true,
  //       color: { argb: '0000' }, // White text color
  //     };
  //     cell.border = {
  //       top: { style: 'thin' },
  //       left: { style: 'thin' },
  //       bottom: { style: 'thin' },
  //       right: { style: 'thin' },
  //     };
  //   });


  //   const headerRow = worksheet.addRow(Object.values(locationColumns.map(col => col.header)));

  //   headerRow.eachCell((cell, index) => {
  //     cell.fill = {
  //       type: 'pattern',
  //       pattern: 'solid',
  //       fgColor: { argb: 'ff0000' }, // Replace with your desired background color
  //     };
  //     cell.font = {
  //       bold: true,
  //       color: { argb: '0000' }, // White text color
  //     };
  //     cell.border = {
  //       top: { style: 'thin' },
  //       left: { style: 'thin' },
  //       bottom: { style: 'thin' },
  //       right: { style: 'thin' },
  //     };
  //   });

  //   res.forEach((item: any, index: any) => {
  //     const exportItem: any = {};
  //     locationColumns.forEach(col => {
  //       if (col.format == 'number') {
  //         exportItem[col.header] = index + 1;
  //       } else {
  //         exportItem[col.header] = item[col.field];
  //       }
  //     });
  //     const cellRow = worksheet.addRow(Object.values(exportItem));
  //     cellRow.eachCell({ includeEmpty: true }, (cell, index) => {
  //       cell.border = {
  //         top: { style: 'thin' },
  //         left: { style: 'thin' },
  //         bottom: { style: 'thin' },
  //         right: { style: 'thin' },
  //       };
  //     });
  //   });

  //   // Call ExcelService to export data to Excel
  //   worksheetPromises.push(worksheet);

  //   workbook.xlsx.writeBuffer().then((data) => {
  //     const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

  //     // Create a temporary anchor element
  //     const url = window.URL.createObjectURL(blob);
  //     const a = document.createElement('a');
  //     a.style.display = 'none';
  //     a.href = url;
  //     a.download = `LOCATION_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
  //     document.body.appendChild(a);

  //     // Simulate click to trigger download
  //     a.click();

  //     // Clean up
  //     window.URL.revokeObjectURL(url);
  //     document.body.removeChild(a);
  //   });
  // }
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