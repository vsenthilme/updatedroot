import { Component, ViewChild } from '@angular/core';
import { ConsignorService } from './consignor.service';
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
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';

@Component({
  selector: 'app-consignor',
  templateUrl: './consignor.component.html',
  styleUrl: './consignor.component.scss'
})
export class ConsignorComponent {

  consignorTable: any[] = [];
  selectedConsignor: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ConsignorService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private fb: FormBuilder,
    private auth: AuthService,
    private spin: NgxSpinnerService
  ) { }

  fullDate: any;
  today: any;
  ngOnInit() {
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Master', 'Consignor'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyName', header: 'Company' },
      { field: 'consignorId', header: 'Consignor ID', format:'hyperLink' },
      { field: 'consignorName', header: 'Consignor Name' },
      { field: 'subProductName', header: 'Sub Product' },
      { field: 'productName', header: 'Product' },
      { field: 'customerName', header: 'Customer' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'remark', header: 'Remarks' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'languageId', header: 'Language ID' },
      { field: 'languageDescription', header: 'Language' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'subProductId', header: 'Sub Product ID' },
      { field: 'productId', header: 'Product ID' },
      { field: 'customerId', header: 'Customer ID' },
      { field: 'statusId', header: 'Status ID' },
      { field: 'agingCount', header: 'Aging Count' },
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
      this.service.search(obj).subscribe({
        next: (res: any) => {
          console.log(res);
          res = this.cs.removeDuplicatesFromArrayList(res, 'consignorId');
          this.consignorTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }, 1000);
  }

  onChange() {
    const choosen = this.selectedConsignor[this.selectedConsignor.length - 1];
    this.selectedConsignor.length = 0;
    this.selectedConsignor.push(choosen);
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
        this.deleterecord(this.selectedConsignor[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedConsignor = linedata;
    }
    if (this.selectedConsignor.length === 0 && type != 'New') {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedConsignor[0] : linedata,
        pageflow: type,
      });
      this.router.navigate(['/main/master/consignor-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedConsignor.length === 0) {
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
        line: this.selectedConsignor,
        module: 'Consignor',
        body: 'This action cannot be undone. All values associated with this field will be lost.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        let obj: any = {};
        obj.languageId = [this.auth.languageId];
        obj.companyId = [this.auth.companyId];
        obj.consignorId = [this.selectedConsignor[0].consignorId];

        this.service.search(obj).subscribe({
          next: (res: any) => {
            console.log(res);
            this.deleterecord(res);
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.DeleteBulk(lines).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Deleted',
          key: 'br',
          detail: lines[0].consignorId + ' Deleted successfully',
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
    const exportData = this.consignorTable.map((item) => {
      const exportItem: any = {};
      this.cols.forEach((col) => {
        if (col.format == 'date') {
          console.log(3);
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
    this.cs.exportAsExcel(exportData, 'Consignor');
  }

  searchform = this.fb.group({
    subProductId: [],
    subProductValue: [],
    productId: [],
    customerId: [],
    consignorId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    subProductId: 'Sub Product',
    subProductValue: 'Sub Product Value',
    productId: 'Product',
    customerId: 'Customer',
    consignorId: 'Consignor',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  subProductDropdown: any = [];
  subProductValueDropdown: any = [];
  productDropdown: any = [];
  customerDropdown: any = [];
  consignorDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.consignorTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.subProductId != null) {
        this.subProductDropdown.push({ value: res.subProductId, label: res.subProductName });
        this.subProductDropdown = this.cs.removeDuplicatesFromArrayList(this.subProductDropdown, 'value');
      }
      if (res.subProductValue != null) {
        this.subProductValueDropdown.push({ value: res.subProductValue, label: res.referenceField1 });
        this.subProductValueDropdown = this.cs.removeDuplicatesFromArrayList(this.subProductValueDropdown, 'value');
      }
      if (res.productId != null) {
        this.productDropdown.push({ value: res.productId, label: res.productName });
        this.productDropdown = this.cs.removeDuplicatesFromArrayList(this.productDropdown, 'value');
      }
      if (res.customerId != null) {
        this.customerDropdown.push({ value: res.customerId, label: res.customerName });
        this.customerDropdown = this.cs.removeDuplicatesFromArrayList(this.customerDropdown, 'value');
      }
      if (res.customerId != null) {
        this.customerDropdown.push({ value: res.customerId, label: res.customerName });
        this.customerDropdown = this.cs.removeDuplicatesFromArrayList(this.customerDropdown, 'value');
      }
      if (res.consignorId != null) {
        this.consignorDropdown.push({ value: res.consignorId, label: res.consignorName });
        this.consignorDropdown = this.cs.removeDuplicatesFromArrayList(this.consignorDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('consignor') overlayPanel!: OverlayPanel;
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
        this.consignorTable = res;
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
      subProductId: [],
      subProductValue: [],
      productId: [],
      customerId: [],
      consignorId: [],
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

}
