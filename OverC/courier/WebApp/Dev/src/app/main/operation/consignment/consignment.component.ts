import { Component, ViewChild } from '@angular/core';
import { ConsignmentService } from './consignment.service';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { ConsignmentLabelComponent } from '../../pdf/consignment-label/consignment-label.component';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';
import { ConsignmentUpdatebulkComponent } from './consignment-updatebulk/consignment-updatebulk.component';
import { DownloadTemplateComponent } from './download-template/download-template.component';
import { InvoicepdfComponent } from '../../pdf/invoice/invoicepdf/invoicepdf.component';
import { CostingSheetService } from '../../airport/costing-sheet/costing-sheet.service';

@Component({
  selector: 'app-consignment',
  templateUrl: './consignment.component.html',
  styleUrl: './consignment.component.scss'
})
export class ConsignmentComponent {
  consignmentTable: any[] = [];
  selectedConsignment: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ConsignmentService,
    private invoiceService: CostingSheetService,
    public dialog: MatDialog,
    private datePipe: DatePipe, private auth: AuthService,
    private spin: NgxSpinnerService,
    private pdf: ConsignmentLabelComponent,
    private fb: FormBuilder,
    private label: ConsignmentLabelComponent,
    private invoice: InvoicepdfComponent,
  ) { }

  fullDate: any;
  today: any;
  ngOnInit() {
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Operations', 'Consignment '];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [

      { field: 'houseAirwayBill', header: 'Consignment No', format: 'hyperLink', style: 'min-width: 5rem' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'hawbTypeDescription', header: 'Status', style: 'min-width: 5rem' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date', style: 'min-width: 5rem' },
      { field: 'partnerName', header: 'Partner', style: 'min-width: 5rem' },
      { field: 'productName', header: 'Product', style: 'min-width: 10rem' },
      //   { field: 'subProductName', header: 'Sub Product', style: 'min-width: 10rem' },
      { field: 'countryOfOrigin', header: 'Origin', style: 'min-width: 5rem' },
      { field: 'countryOfDestination', header: 'Destination', style: 'min-width: 5rem' },
      { field: 'serviceTypeText', header: 'Service Type', style: 'min-width: 5rem' },
      { field: 'paymentType', header: 'Payment Type', style: 'min-width: 5rem' },
      { field: 'incoTerms', header: 'Inco Terms', style: 'min-width: 5rem' },
      { field: 'createdBy', header: 'Created By', style: 'min-width: 5rem' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'statusId', header: 'Status ID' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'countryId', header: 'Country ID' },
      { field: 'countryName', header: 'Country Name' },
      { field: 'provinceId', header: 'Province ID' },
      { field: 'provinceName', header: 'Province Name' },
      { field: 'districtId', header: 'District ID' },
      { field: 'districtName', header: 'District Name' },
      { field: 'cityId', header: 'City ID' },
      { field: 'cityName', header: 'City Name' },
      { field: 'referenceField1', header: 'Reference Field 1' },
      { field: 'referenceField2', header: 'Reference Field 2' },
      { field: 'referenceField3', header: 'Reference Field 3' },
      { field: 'referenceField4', header: 'Reference Field 4' },
      { field: 'referenceField5', header: 'Reference Field 5' },
      { field: 'referenceField6', header: 'Reference Field 6' },
      { field: 'referenceField7', header: 'Reference Field 7' },
      { field: 'referenceField8', header: 'Reference Field 8' },
      { field: 'referenceField9', header: 'Reference Field 9' },
      { field: 'referenceField10', header: 'Reference Field 10' },
      { field: 'updatedBy', header: 'Updated By' },
      { field: 'updatedOn', header: 'Updated On', format: 'date' },
    ];
  }

  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
     // obj.startDate = this.calculateStartDate();
      //obj.endDate = new Date().toISOString();

      this.service.search(obj).subscribe({
        next: (res: any) => {
          console.log(res);
          this.consignmentTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }, 2000);
  }

  onChange() {
    const choosen = this.selectedConsignment[this.selectedConsignment.length - 1];
    this.selectedConsignment.length = 0;
    this.selectedConsignment.push(choosen);
  }

  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { target: this.cols, source: this.target },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment[0]);
      }
    });
  }
  updateBulk() {
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(ConsignmentUpdatebulkComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'Consignment', code: this.selectedConsignment },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.initialCall();
    });
  }
  openCrud(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedConsignment = linedata;
    }
    if (this.selectedConsignment.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsignment[0] : linedata, pageflow: type });
      this.router.navigate(['/main/operation/consignment-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: {
        line: this.selectedConsignment,
        module: 'Consignment',
        body: 'This action cannot be undone. All values associated with this field will be lost.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment);
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Deleted',
          key: 'br',
          detail: 'Selected records deleted successfully',
        });
        this.spin.hide();
        this.initialCall();
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      },
    });
  }

  downloadExcel() {
    const exportData = this.consignmentTable.map((item) => {
      const exportItem: any = {};
      this.cols.forEach((col) => {
        if (col.format == 'date') {
          exportItem[col.header] = this.datePipe.transform(
            item[col.field],
            'dd-MM-yyyy'
          );
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Consignment');
  }

  downloadLabel(line: any, type: any) {
    this.pdf.generatePdfBarocde(line, type);
  }

  downloadInvoice(line: any, type: any) {
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.partnerHouseAirwayBill = [line.partnerHouseAirwayBill];
    obj.houseAirwayBill = [line.houseAirwayBill];

    this.invoiceService.customsClearanceInvoice(obj).subscribe({
      next: (result) => {
        if (result.length > 0) {
          this.invoice.generateDDUInvoice(result[0], type);
          this.spin.hide();
        } else {
          this.messageService.add({
            severity: 'warn',
            summary: 'No Data',
            key: 'br',
            detail: 'No Data found',
          });
          this.spin.hide();
        }
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }

  selectedFiles: File | null = null;
  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    this.selectedFiles = file;
    this.spin.show();
    this.service.uploadConsignment(this.selectedFiles).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Uploaded',
          key: 'br',
          detail: 'File uploaded successfully',
        });
        this.selectedFiles = null;
        this.clearFileInput(event.target);
        this.initialCall();
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.selectedFiles = null;
        this.clearFileInput(event.target);
        this.cs.commonerrorNew(err);
      }
    });
  }

  clearFileInput(input: HTMLInputElement): void {
    input.value = ''; // Reset the value of the file input field
  }
  ///  Filter Code

  searchform = this.fb.group({
    houseAirwayBill: [],
    masterAirwayBill: [],
    partnerId: [],
    pieceId: [],
    pieceItemId: [],
    shipperId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],],
   // startDate: this.calculateStartDate(),
   // endDate: new Date().toISOString()
  })

  readonly fieldDisplayNames: Record<string, string> = {
    houseAirwayBill: 'Consignment No',
    masterAirwayBill: 'MAWB',
    partnerId: 'Partner',
    pieceId: 'Piece',
    pieceItemId: 'Piece Item',
    shipperId: 'Shipper',
    statusId: 'Status'
  };

  houseAirwayBillDropdown: any = [];
  masterAirwayBillDropdown: any = [];
  partnerDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.consignmentTable.forEach(res => {

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
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('consignment') overlayPanel!: OverlayPanel;
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
        this.consignmentTable = res;
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
      companyId: [[this.auth.companyId],],
      languageId: [[this.auth.languageId],],
     // startDate: this.calculateStartDate(),
     // endDate: new Date().toISOString()
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
  isSelected(item: any): boolean {
    return this.selectedConsignment.includes(item);
  }

  uniqueHouseAirway: any[] = [];
  generateInvoice() {
    this.uniqueHouseAirway = [];
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    this.uniqueHouseAirway = this.selectedConsignment.map(item => item.houseAirwayBill);
    this.label.getResultInvoice(this.uniqueHouseAirway)
  }


  uniquePieceId: any[] = [];
  generateLabel() {
    this.uniquePieceId = [];
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    }
    this.uniqueHouseAirway = this.selectedConsignment.map(item => item.houseAirwayBill);
    this.label.getResultLabelFromConsignment(this.uniqueHouseAirway)
  }

  downloadTemplate() {
    const dialogRef = this.dialog.open(DownloadTemplateComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        if (result.creationType == 'Single') {
          this.openCrud('New')
        } else {
          let obj: any = {};
          obj.value = { imageRefId: result.fileName, referenceImageUrl: '/consignment/templates/' }
          this.label.downloadDocument(obj);
        }
      }
    });
  }

  calculateStartDate(): string {
    const currentDate = new Date();
    const startDate = new Date();
    startDate.setDate(currentDate.getDate() - 30);
    return startDate.toISOString();
  }
}
