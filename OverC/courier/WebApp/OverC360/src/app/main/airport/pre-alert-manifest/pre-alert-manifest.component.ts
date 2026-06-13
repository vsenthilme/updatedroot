import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { LanguageService } from '../../id-masters/language/language.service';
import { TableRowCollapseEvent, TableRowExpandEvent } from 'primeng/table';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { ConsignmentService } from '../../operation/consignment/consignment.service';
import { BondedManifestService } from '../bonded-manifest/bonded-manifest.service';
import { ConsoleService } from '../console/console.service';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';
import * as XLSX from 'xlsx';
import { PrealertService } from './prealert.service';
import { PreAlertBulkComponent } from './pre-alert-bulk/pre-alert-bulk.component';
import * as ExcelJS from 'exceljs';
import { InvoicepdfComponent } from '../../pdf/invoice/invoicepdf/invoicepdf.component';

@Component({
  selector: 'app-pre-alert-manifest',
  templateUrl: './pre-alert-manifest.component.html',
  styleUrl: './pre-alert-manifest.component.scss',
  animations: [
    trigger('fadeLater', [
      state('fade-in', style({ opacity: 1, transform: 'translateY(0)' })),
      state('fade-out', style({ opacity: 0, transform: 'translateY(0)' })),
      transition('fade-in <=> fade-out', animate('0.6s ease-in-out'))
    ]),
  ]
})
export class PreAlertManifestComponent {

  preAlertManifestTable: any[] = [];
  selectedPreAlertManifest: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ConsignmentService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private spin: NgxSpinnerService,
    private manifest: BondedManifestService,
    private console: ConsoleService,
    private fb: FormBuilder,
    private prealertService: PrealertService,
    private download: InvoicepdfComponent,
  ) { }

  fullDate: any;
  today: any;


  originalData = [
    { description: 'global', id: '1001', amount: '250' },
    { description: 'nas', id: '1001', amount: '100' },
    { description: 'nas', id: '1003', amount: '200' },
    { description: 'test3', id: '1002', amount: '1000' },
    { description: 'test3', id: '1001', amount: '333' }
  ];
  columns: string[] = [];  // To hold column names dynamically
  rows: { [key: string]: { [key: string]: string } } = {};  

  transformData() {
    const tempColumns = new Set<string>();
    const tempRows: { [key: string]: { [key: string]: string } } = {};

    // Collect unique columns and organize rows by id
    this.originalData.forEach(item => {
      tempColumns.add(item.description);

      if (!tempRows[item.id]) {
        tempRows[item.id] = {};
      }
      tempRows[item.id][item.description] = item.amount;
    });

    this.columns = Array.from(tempColumns);

    this.columns.unshift('id');

    this.rows = tempRows;
  }

  getRowIds(): string[] {
    return Object.keys(this.rows);
  }

  ngOnInit() {
   // this.transformData();
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Mid-Mile', 'Pre-Alert Manifest '];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyName', header: 'Company' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB', format: 'hyperLink' },
      { field: 'countHawb', header: 'Shipment Count' },
      // { field: 'partnerId', header: 'Partner ID' },
      { field: 'partnerName', header: 'Partner Name' },
      { field: 'flightNo', header: 'Flight No' },
      { field: 'flightName', header: 'Flight Name' },
      // { field: 'consoleIndicator', header: 'Console', format: 'boolean' },
      // { field: 'manifestIndicator', header: 'Bonded Manifest', format: 'boolean' },
      // { field: 'preAlertManifestIndicator', header: 'Pre-Alert Manifest', format: 'boolean' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'languageId', header: 'Language' },
      { field: 'estimatedTimeOfDeparture', header: 'Departure Time', format: 'date1' },
      { field: 'estimatedTimeOfArrival', header: 'Arrival Time', format: 'date1' },
      { field: 'partnerType', header: 'Partner Type' },
      { field: 'referenceField1', header: 'Reference Field 1' },
      { field: 'referenceField2', header: 'Reference Field 2' },
      { field: 'referenceField3', header: 'Reference Field 3' },
      { field: 'referenceField4', header: 'Reference Field 4' },
      { field: 'referenceField5', header: 'Reference Field 5' },
    ];
  }
  updateBulk() {
    const dialogRef = this.dialog.open(PreAlertBulkComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'PreAlertManifest', code: this.selectedPreAlertManifest },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.initialCall();
    });
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.searchPrealert(obj).subscribe({
        next: (res: any) => {
          this.preAlertManifestTable = res;
          this.preAlertManifestTable = this.cs.removeDuplicatesFromArrayList(this.preAlertManifestTable, 'partnerMasterAirwayBill')
          this.getSearchDropdown();
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 2000);
  }


  isSelected(item: any): boolean {
    return this.selectedPreAlertManifest.includes(item);
  }

  onChange() {
    const choosen = this.selectedPreAlertManifest[this.selectedPreAlertManifest.length - 1];
    this.selectedPreAlertManifest.length = 0;
    this.selectedPreAlertManifest.push(choosen);
  }

  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { target: this.cols, source: this.target, },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedPreAlertManifest[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {

    if (this.selectedPreAlertManifest.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedPreAlertManifest[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/preAlertManifest-new/' + paramdata]);
    }
  }


  openEdit(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedPreAlertManifest = linedata;
    }
    if (this.selectedPreAlertManifest.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedPreAlertManifest[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/preAlertManifest-update/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedPreAlertManifest.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedPreAlertManifest, module: 'Pre Alert Manifest', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const partnerMasterAirwayBill = this.selectedPreAlertManifest.map(item => item.partnerMasterAirwayBill);
        this.service.searchPrealert({ partnerMasterAirwayBill: partnerMasterAirwayBill }).subscribe({
          next: (result) => {
            this.deleterecord(result);
          }
        })
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.prealertService.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: 'Prealert has been deleted successfully' });
        this.initialCall();
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  downloadExcel() {

    const preAlertColumn = [
      { field: 'partnerMasterAirwayBill', header: 'MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'HAWB' },
      { field: 'totalWeight', header: 'Weight' },
      { field: 'noOfPieces', header: 'PCS' },
      { field: 'consignmentValue', header: 'Value' },
      { field: 'bayanHv', header: 'Bayan HV' },
      { field: 'currency', header: 'Currency' },
      { field: 'description', header: 'Description(en)' },
      { field: 'consigneeName', header: 'Cnee Name' },
      { field: 'shipper', header: 'Shipper' },
      { field: 'origin', header: 'Origin' },
      { field: 'originCode', header: 'Origin Code' },
      { field: 'customsValue', header: 'Value KD' },
      { field: 'iata', header: 'IATA' },
      { field: 'hsCode', header: 'HSCode' },
      { field: 'incoTerm', header: 'DDU & DDB' },
    ]
    const exportData = this.preAlertManifestTable.map(item => {
      const exportItem: any = {};
      preAlertColumn.forEach(col => {
        // if (col.format == 'date') {
        //   exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        // } else {
        //   exportItem[col.header] = item[col.field];
        // }
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsPrealertExcel(exportData, 'Pre-Alert Manifest');
  }

  onRowExpand(event: TableRowExpandEvent) {
  }
  onRowCollapse(event: TableRowCollapseEvent) {
  }

  getColspan(): number {
    return this.cols.length + 2; // +1 for the expanded content column
  }

  createConsole() {
    if (this.selectedPreAlertManifest.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    this.spin.show();
    // this.console.CreateFromConsignment(this.selectedPreAlertManifest).subscribe({
    //   next: (res) => {
    //     this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: 'Console has been created successfully' });
    //     this.spin.hide();
    //     this.initialCall();
    //   }, error: (err) => {
    //     this.spin.hide();
    //     this.cs.commonerrorNew(err);
    //   }
    // })
    const consignmentId = this.selectedPreAlertManifest.map(item => item.partnerMasterAirwayBill);
    this.service.searchPrealert({ partnerMasterAirwayBill: consignmentId }).subscribe({
      next: (result) => {
        this.console.CreateFromConsignment(result).subscribe({
          next: (res) => {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: 'Console has been created successfully' });
            this.spin.hide();
            this.initialCall();
          }, error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          }
        })
      }
    })

  }

  createManifest() {
    if (this.selectedPreAlertManifest.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    this.spin.show();
    // this.manifest.CreatefromPrealert(this.selectedPreAlertManifest).subscribe({
    //   next: (res) => {
    //     this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: 'Manifest has been created successfully' });
    //     this.spin.hide();
    //     this.downloadBondedManifest(res);
    //     this.initialCall()
    //   }, error: (err) => {
    //     this.spin.hide();
    //     this.cs.commonerrorNew(err);
    //   }
    // })
    const consignmentId = this.selectedPreAlertManifest.map(item => item.partnerMasterAirwayBill);
    this.service.searchPrealert({ partnerMasterAirwayBill: consignmentId }).subscribe({
      next: (result) => {
        this.manifest.CreatefromPrealert(result).subscribe({
          next: (res) => {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: 'Manifest has been created successfully' });
            this.spin.hide();
            this.initialCall();
            this.downloadBondedManifest1(res);
          }, error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          }
        })
      }
    })
  }

  searchform = this.fb.group({
    houseAirwayBill: [],
    masterAirwayBill: [],
    partnerId: [],
    pieceId: [],
    pieceItemId: [],
    manifestIndicator: [],
    consoleIndicator: [],
    shipperId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    houseAirwayBill: 'Consignment No',
    masterAirwayBill: 'MAWB',
    partnerId: 'Partner',
    shipperId: 'Shipper ID',
    pieceId: 'Piece ID',
    pieceItemId: 'Piece Item ID',
    statusId: 'Status'
  };

  houseAirwayBillDropdown: any = [];
  masterAirwayBillDropdown: any = [];
  partnerDropdown: any = [];
  statusDropdown: any = [];
  indicatorDropdown: any = [];

  getSearchDropdown() {

    this.preAlertManifestTable.forEach(res => {

      if (res.houseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.houseAirwayBill, label: res.houseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, 'value');
      }
      if (res.partnerId != null) {
        this.partnerDropdown.push({ value: res.partnerId, label: res.partnerName });
        this.partnerDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerDropdown, 'partnerId');
      }
      if (res.masterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.masterAirwayBill, label: res.masterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, 'partnerId');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'statusId');
      }
    })
    this.indicatorDropdown = [{ value: 1, label: 'Created' }, { value: 0, label: 'Not Created' }];
  }

  @ViewChild('preAlertManifest') overlayPanel!: OverlayPanel;
  closeOverLay() {
    this.overlayPanel.hide();
  }

  fieldsWithValue: any
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues)
      .filter(key => formValues[key as keyof typeof formValues] !== null && formValues[key as keyof typeof formValues] !== undefined && key !== 'companyId' && key !== 'languageId')
      .map(key => this.fieldDisplayNames[key] || key);

    this.spin.show();
    this.service.searchPrealert(this.searchform.getRawValue()).subscribe({
      next: (res: any) => {
        this.preAlertManifestTable = res;
        //  this.preAlertManifestTable = this.cs.removeDuplicatesFromArrayList(this.preAlertManifestTable, 'masterAirwayBill')
        this.spin.hide();
        this.overlayPanel.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  reset() {
    this.searchform.reset();
    this.searchform = this.fb.group({
      houseAirwayBill: [],
      masterAirwayBill: [],
      partnerId: [],
      pieceId: [],
      pieceItemId: [],
      shipperId: [],
      statusId: [],
      manifestIndicator: [],
      consoleIndicator: [],
      companyId: [[this.auth.companyId],],
      languageId: [[this.auth.languageId],]
    })
    this.search();
  }

  chipClear(value: any) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find(key => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.searchform.get(formControlKey)?.reset();
      this.search();
    }
  }

  getSeverity(value: number) {
    return value === 0 ? 'red' : 'green';
  }

  preAlertIndicator(type: any = 'Indicator', linedata: any[]) {
    console.log(linedata);

    if (this.selectedPreAlertManifest.length === 0 && type == 'Indicator') {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedPreAlertManifest[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/preAlertManifest-indicator/' + paramdata]);
    }
  }



  // Download Excel function
  bols: any[] = [];
  items: any[] = [];

  downloadBondedManifest(res: any) {

    this.bols = [
      { field: '', header: 'Temporary Manifest Number' },
      { field: 'partnerHouseAirwayBill', header: 'Bill of Lading No' },
      { field: 'estimatedTimeOfArrival', header: 'Bill of Lading Date', format: 'date' },
      { field: 'description', header: 'Description' },
      { field: 'billOfLadingFor', header: 'Bill of Lading For' },
      { field: '', header: 'Net Weight (kgs)' },
      { field: 'manifestedGrossWeight', header: 'Manifested Gross Weight (Kgs)' },
      { field: 'grossWeight', header: 'Gross Weight (kgs)' },
      { field: 'tareWeight', header: 'Tare Weight (kgs)' },
      { field: 'manifestedQuantity', header: 'Manifested Quantity' },
      { field: 'landedQuantity', header: 'Landed Quantity' },
      { field: 'totalQuantity', header: 'Total Quantity' },
      { field: 'volume', header: 'Volume (cbm)' },
      // { field: 'countryOfOrigin', header: 'Port Of Shipping' },
      { field: 'airportOriginCode', header: 'Port Of Shipping' },
      { field: 'finalDestination', header: 'Final Destination' },
      { field: '', header: 'Consignee (Registered)' },
      { field: '', header: 'Notify' },
      { field: 'consigneeName', header: 'Consignee (Free text)' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'remarks', header: 'Remarks' },
      { field: 'isConsolidatedShipment', header: 'Is Consolidated Shipment' },
      { field: 'isSplitBillOfLading', header: 'Is Split Bill of Lading' },
      { field: 'consolidatedBillNo', header: 'Consolidate Bill Number' },
      { field: 'isPendingShipment', header: 'Is Pending shipment' },
      { field: 'bwhInvestor', header: 'BWHInvestor' },
    ];

    this.items = [
      { field: '', header: 'Bill of Lading No' },
      { field: '', header: 'Kind' },
      { field: '', header: 'Goods Type' },
      { field: '', header: 'FCL/LCL' },
      { field: '', header: 'Container No' },
      { field: '', header: 'Container Type' },
      { field: '', header: 'Container Size' },
      { field: '', header: 'Quantity' },
      { field: '', header: 'Description' },
      { field: '', header: 'Gross Weight (kgs)' },
      { field: '', header: 'Net Weight (kgs)' },
      { field: '', header: 'Tare Weight (kgs)' },
      { field: '', header: 'Volume' },
      { field: '', header: 'Mark ID' },
      { field: '', header: 'Mark Type' },
      { field: '', header: 'Seal No' },
      { field: '', header: 'Vehicle Model (Year)' },
      { field: '', header: 'Vehicle Type' },
      { field: '', header: 'Chasis No' },
      { field: '', header: 'Engine No ' },
      { field: '', header: 'Year of Manufacture' },
      { field: '', header: 'Vehicle Body Color' },
      { field: '', header: 'Vehicle Brand' },
      { field: '', header: 'Vehicle Nationality' },
      { field: '', header: 'Load' },
      { field: '', header: 'Passenger' },
      { field: '', header: 'Engine Power' },
      { field: '', header: 'No Of Cylinders' },
      { field: '', header: 'Country Of Origin' },
    ];

    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    let sheetNumber = 1;
    const bolsData = res.map((item: { [x: string]: any; }) => {
      const exportItem: any = {};
      this.bols.forEach(col => {
        if (col.format === 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    const bolsSheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(bolsData);
    XLSX.utils.book_append_sheet(wb, bolsSheet, `BOLs`);

    const itemsData = res.map((item: { [x: string]: any; }) => {
      const exportItem: any = {};
      this.items.forEach(col => {
        if (col.format === 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });
    const itemsSheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(itemsData);
    XLSX.utils.book_append_sheet(wb, itemsSheet, `Items`); //_${new Date().getDate()}-${new Date().getMonth() + 1}-${new Date().getFullYear()}
    sheetNumber += 2;

    XLSX.writeFile(
      wb,
      `Bonded-Manifest_${new Date().getDate()}-${new Date().getMonth() + 1}-${new Date().getFullYear()}.xlsx`
    );
  }

  async downloadBondedManifest1(res: any) {
    // Define your columns
    const bols = [
      { field: '', header: 'Temporary Manifest Number' },
      { field: 'partnerHouseAirwayBill', header: 'Bill of Lading No' },
      { field: 'estimatedTimeOfArrival', header: 'Bill of Lading Date', format: 'date' },
      { field: 'description', header: 'Description' },
      { field: 'billOfLadingFor', header: 'Bill of Lading For' },
      { field: '', header: 'Net Weight (kgs)' },
      { field: 'manifestedGrossWeight', header: 'Manifested Gross Weight (Kgs)' },
      { field: 'grossWeight', header: 'Gross Weight (kgs)' },
      { field: '', header: 'Tare Weight (kgs)' },
      { field: 'manifestedQuantity', header: 'Manifested Quantity' },
      { field: 'landedQuantity', header: 'Landed Quantity' },
      { field: 'totalQuantity', header: 'Total Quantity' },
      { field: 'volume', header: 'Volume (cbm)' },
      // { field: 'countryOfOrigin', header: 'Port Of Shipping' },
      { field: 'airportOriginCode', header: 'Port Of Shipping' },
      { field: 'finalDestination', header: 'Final Destination' },
      { field: '', header: 'Consignee (Registered)' },
      { field: '', header: 'Notify' },
      { field: 'consigneeName', header: 'Consignee (Free text)' },
      { field: 'shipperName', header: 'Shipper' },
      { field: 'remarks', header: 'Remarks' },
      { field: 'isConsolidatedShipment', header: 'Is Consolidated Shipment' },
      { field: 'isSplitBillOfLading', header: 'Is Split Bill of Lading' },
      { field: 'partnerMasterAirwayBill', header: 'Consolidate Bill Number' },
      { field: 'isPendingShipment', header: 'Is Pending shipment' },
      { field: 'bwhInvestor', header: 'BWHInvestor' },
    ];

    const items = [
      { field: '', header: 'Bill of Lading No' },
      { field: '', header: 'Kind' },
      { field: '', header: 'Goods Type' },
      { field: '', header: 'FCL/LCL' },
      { field: '', header: 'Container No' },
      { field: '', header: 'Container Type' },
      { field: '', header: 'Container Size' },
      { field: '', header: 'Quantity' },
      { field: '', header: 'Description' },
      { field: '', header: 'Gross Weight (kgs)' },
      { field: '', header: 'Net Weight (kgs)' },
      { field: '', header: 'Tare Weight (kgs)' },
      { field: '', header: 'Volume' },
      { field: '', header: 'Mark ID' },
      { field: '', header: 'Mark Type' },
      { field: '', header: 'Seal No' },
      { field: '', header: 'Vehicle Model (Year)' },
      { field: '', header: 'Vehicle Type' },
      { field: '', header: 'Chasis No' },
      { field: '', header: 'Engine No ' },
      { field: '', header: 'Year of Manufacture' },
      { field: '', header: 'Vehicle Body Color' },
      { field: '', header: 'Vehicle Brand' },
      { field: '', header: 'Vehicle Nationality' },
      { field: '', header: 'Load' },
      { field: '', header: 'Passenger' },
      { field: '', header: 'Engine Power' },
      { field: '', header: 'No Of Cylinders' },
      { field: '', header: 'Country Of Origin' },
    ];

    // Create a new workbook
    const workbook = new ExcelJS.Workbook();
    const currentDate = new Date();
    // Add the BOLs sheet
    const worksheet = workbook.addWorksheet('BOLs');

    // Add headers
    const headerRow = worksheet.addRow(bols.map(col => col.header));

    const highlightConfigurations = [
      { index: 2, color: 'FFFF00' },
      { index: 3, color: 'FFFF00' },
      { index: 4, color: 'FFFF00' },
      { index: 7, color: 'FFFF00' },
      { index: 8, color: 'FFFF00' },
      { index: 10, color: 'FFFF00' },
      { index: 11, color: 'FFFF00' },
      { index: 12, color: 'FFFF00' },
      { index: 14, color: 'FFFF00' },
      { index: 18, color: 'FFFF00' },
      { index: 19, color: 'FFFF00' },
      { index: 23, color: 'FFFF00' },
      // Add more configurations as needed
    ];


    headerRow.eachCell((cell, index) => {
      // Check if the cell index matches any of the highlight configurations
      const highlightConfig = highlightConfigurations.find(config => config.index === index);

      if (highlightConfig) {
        // Set the background color according to the highlight configuration
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: highlightConfig.color }, // Use the color from the configuration
        };
      } else {
        // Set the background color for non-highlighted cells
        cell.fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: 'C0C0C0' }, // Default color
        };
      }

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
      bols.forEach(col => {
        if (col.field == 'partnerMasterAirwayBill') {
          // Remove hyphens from PartnerMasterAirwayBill
          exportItem[col.header] = item[col.field].replace(/-/g, '');
        }
        else if (col.format == 'number') {
          exportItem[col.header] = index + 1;
        }
        else if (col.format == 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        }
        else if (col.field != 'partnerMasterAirwayBill'){
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

    // Add the Items sheet
    const itemsSheet = workbook.addWorksheet('Items');

    // Add headers
    const headerRow1 = itemsSheet.addRow(items.map(col => col.header));

    headerRow1.eachCell((cell, index) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'C0C0C0' }, // Replace with your desired background color
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

    // Add data rows
    res.forEach((item: any, index: any) => {
      const exportItem: any = {};
      items.forEach(col => {
        exportItem[col.header] = item[col.field];
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

    // Save the workbook to a file
    workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

      // Create a temporary anchor element
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.style.display = 'none';
      a.href = url;
      a.download = `Bonded_Manifest_${currentDate.getDate()}-${currentDate.getMonth() + 1}-${currentDate.getFullYear()}.xlsx`;
      document.body.appendChild(a);

      // Simulate click to trigger download
      a.click();

      // Clean up
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    });
  }
}
