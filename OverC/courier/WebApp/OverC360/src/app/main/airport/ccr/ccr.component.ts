import { Component, ElementRef, ViewChild } from '@angular/core';
import { CcrService } from './ccr.service';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TableRowExpandEvent, TableRowCollapseEvent } from 'primeng/table';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';
import { ConsoleBulkComponent } from '../console/console-bulk/console-bulk.component';
import { ConsignmentLabelComponent } from '../../pdf/consignment-label/consignment-label.component';

@Component({
  selector: 'app-ccr',
  templateUrl: './ccr.component.html',
  styleUrl: './ccr.component.scss'
})
export class CcrComponent {

  actualResult: any[] = [];
  ccrTable: any[] = [];
  selectedCcr: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(private messageService: MessageService, private cs: CommonServiceService, private router: Router, private path: PathNameService, private service: CcrService,
    public dialog: MatDialog, private datePipe: DatePipe, private auth: AuthService, private fb: FormBuilder, private spin: NgxSpinnerService,
  private label: ConsignmentLabelComponent) { }

  fullDate: any;
  today: any;
  ngOnInit() {
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Mid-Mile', 'CCR'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyId', header: 'Company' },
      { field: 'ccrId', header: 'CCR ID' },
      { field: 'houseAirwayBill', header: 'Consignment No' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'statusText', header: 'Status' },
      { field: 'eventText', header: 'Event' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [

      { field: 'customsCcrNo', header: 'Customs CCR No', showFooter: false },
      { field: 'countryOfOrigin', header: 'Origin', showFooter: false },
      { field: 'airportOriginCode', header: 'Airport Origin Code', showFooter: false },
      { field: 'hsCode', header: 'HS Code', showFooter: false },
      { field: 'goodsDescription', header: 'Commodity', showFooter: false },
      { field: 'invoiceNumber', header: 'Invoice No' , showFooter: false},
      { field: 'invoiceTpe', header: 'Invoice Type', showFooter: false },
      { field: 'invoiceDate', header: 'Invoice Date', format: 'date', showFooter: false },
      { field: 'invoiceSupplierName', header: 'Invoice Supplier Name', showFooter: false },
      { field: 'isExempted', header: 'Is Exempted', showFooter: false },
      { field: 'exemptionFor', header: 'Exempted For', showFooter: false },
      { field: 'exemptionBeneficiary', header: 'Exempted Beneficiary', showFooter: false },
      { field: 'exemptionReference', header: 'Exempted Reference', showFooter: false },
      { field: 'consigneeName', header: 'Consignee Name', showFooter: false },
      { field: 'consignmentValue', header: 'Consignment Value', showFooter: false },
      { field: 'consignmentCurrency', header: 'Consignment Currency', showFooter: false},
      { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: 'iata', header: 'IATA', showFooter: false },
      { field: 'customsInsurance', header: 'Customs Insurance', showFooter: false },
      { field: 'duty', header: 'Duty', showFooter: true },
      { field: 'consignmentValueLocal', header: 'Consignment Value Local', showFooter: false },
      { field: 'addIata', header: 'Add IATA', showFooter: false },
      { field: 'addInsurance', header: 'Add Insurance', showFooter: false },
      { field: 'customsValue', header: 'Custom', showFooter: false },
      { field: 'calculatedTotalDuty', header: 'Calculated Total duty', showFooter: true },
      { field: 'dduCharge', header: 'DDU Charge', showFooter: false },
      { field: 'specialApprovalCharge', header: 'Spl Approval Charge', showFooter: false },
      { field: 'totalDuty', header: 'Duty From Bayan', showFooter: true },
    ];
  }
  updateBulk(){
    const dialogRef = this.dialog.open(ConsoleBulkComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: {title: 'CCR',code :  this.selectedCcr} ,
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
      this.service.search(obj).subscribe({
        next: (res: any) => {
          this.ccrTable = res;
          this.actualResult = res;
          this.ccrTable =  this.cs.removeDuplicatesFromArrayList(this.ccrTable, 'ccrId')
          this.getSearchDropdown();
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 2000);
  }

  onChange() {
    const choosen = this.selectedCcr[this.selectedCcr.length - 1];
    this.selectedCcr.length = 0;
    this.selectedCcr.push(choosen);
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
        this.deleterecord(this.selectedCcr[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if (this.selectedCcr.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCcr[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/ccr-new/' + paramdata]);
    }
  }
  openEdit(type: any = 'New', linedata: any = null): void {
    if (this.selectedCcr.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCcr[0] : linedata, pageflow: type });
      this.router.navigate(['/main/airport/ccr-edit/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedCcr, module: 'CCR', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedCcr);
      }
    });
  }
  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: 'Selected records deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.ccrTable.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      this.target.forEach(col => {
        if (col.format && col.format === 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'CCR Manifest');
  }

  onRowExpand(event: TableRowExpandEvent) {
  }
  onRowCollapse(event: TableRowCollapseEvent) {
  }

  getColspan(): number {
    return this.cols.length + 2; // +1 for the expanded content column
  }
  isSelected(item: any): boolean {
    return this.selectedCcr.includes(item);
  }

  @ViewChild('fileInput') fileInput!: ElementRef;
  
  uploadBayan(){
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    this.fileInput.nativeElement.click();
  }
  selectedFiles: File | null = null;
  onFileSelected(event: any): void {
    const filePath = '/' + this.selectedCcr[0].ccrId + '/';
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
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }

  searchform = this.fb.group({
    houseAirwayBill: [],
    masterAirwayBill: [],
    partnerId: [],
    ccrId: [],
    consoleId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    houseAirwayBill: 'Consignment No',
    masterAirwayBill: 'MAWB',
    partnerId: 'Partner',
    ccrId: 'CCR ID',
    consoleId: 'Console ID',
    statusId: 'Status'
  };

  houseAirwayBillDropdown: any = [];
  masterAirwayBillDropdown: any = [];
  partnerDropdown: any = [];
  statusDropdown: any = [];
  ccrIdDropdown: any = [];
  consoleIdDropdown: any = [];

  getSearchDropdown() {

    this.ccrTable.forEach(res => {

      if (res.houseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.houseAirwayBill, label: res.houseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, 'value');
      }
      if (res.partnerId != null) {
        this.partnerDropdown.push({ value: res.partnerId, label: res.partnerName });
        this.partnerDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerDropdown, 'value');
      }
      if (res.masterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.masterAirwayBill, label: res.masterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
      if (res.ccrId != null) {
        this.ccrIdDropdown.push({ value: res.ccrId, label: res.ccrId });
        this.ccrIdDropdown = this.cs.removeDuplicatesFromArrayList(this.ccrIdDropdown, 'value');
      }
      if (res.consoleId != null) {
        this.consoleIdDropdown.push({ value: res.consoleId, label: res.consoleId });
        this.consoleIdDropdown = this.cs.removeDuplicatesFromArrayList(this.consoleIdDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('ccr') overlayPanel!: OverlayPanel;
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
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res: any) => {
        this.ccrTable = res;
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
      ccrId: [],
      consoleId: [],
      statusId: [],
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

  removeDuplicated: any [] = []

  generateLabel(){
    this.uniquePieceId = [];
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    let obj: any = {};
    obj.ccrId=[this.selectedCcr[0].ccrId];
    this.service.search(obj).subscribe({
      next: (res: any) => {
        this.uniquePieceId = this.cs.removeDuplicatesFromArrayList(res, 'pieceId');
        const pieceId = this.uniquePieceId.map(item => item.pieceId);
        this.label.getResultLabel(pieceId)
      },
      error: (err) => {
       this.spin.hide();
       this.cs.commonerrorNew(err);
      },
    });
  }
  houseAirwayBill:any;
  
  generateInvoice(){
    this.uniqueHouseAirway = [];
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    let obj: any = {};
    obj.ccrId=[this.selectedCcr[0].ccrId];
    this.service.search(obj).subscribe({
      next: (res: any) => {
        this.uniqueHouseAirway = this.cs.removeDuplicatesFromArrayList(res, 'houseAirwayBill');
        const houseAirwayBillArray = this.uniqueHouseAirway.map(item => item.houseAirwayBill);
        this.label.getResultInvoice(houseAirwayBillArray)
      },
      error: (err) => {
       this.spin.hide();
       this.cs.commonerrorNew(err);
      },
    });
  }


  uniquePieceId: any[] = []; 
  uniqueHouseAirway: any[] = []; 
  generateMerge(){
    this.uniqueHouseAirway = [];
    this.uniquePieceId = [];
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    let obj: any = {};
    obj.ccrId=[this.selectedCcr[0].ccrId];
    this.service.search(obj).subscribe({
      next: (res: any) => {
        this.uniquePieceId = this.cs.removeDuplicatesFromArrayList(res, 'pieceId');
        this.uniqueHouseAirway = this.cs.removeDuplicatesFromArrayList(res, 'houseAirwayBill');
        const pieceId = this.uniquePieceId.map(item => item.pieceId);
        const houseAirwayBillArray = this.uniqueHouseAirway.map(item => item.houseAirwayBill);
        this.label.generateMutiple(pieceId, houseAirwayBillArray)
      },
      error: (err) => {
       this.spin.hide();
       this.cs.commonerrorNew(err);
      },
    });

  }

}