import { Component, ViewChild } from '@angular/core';
import { ClearanceChargesService } from './clearance-charges.service';
import { DatePipe } from '@angular/common';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { AuthService } from '../../../core/core';

@Component({
  selector: 'app-clearance-charges',
  templateUrl: './clearance-charges.component.html',
  styleUrl: './clearance-charges.component.scss'
})
export class ClearanceChargesComponent {
  clearanceTable: any[] = [];
  selectedClearance: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ClearanceChargesService,
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
    const dataToSend = ['Master', 'Clearance Charges '];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyId', header: 'Company' },
      { field: 'partnerName', header: 'Partner', format:'hyperLink' },
      { field: 'noOfShipmentsFrom', header: 'From Shipment' },
      { field: 'noOfShipmentsTo', header: 'To Shipment' },
      { field: 'clearanceCharges', header: 'Clearance Charges' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'formulaField1', header: 'Formula', format: 'extra' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
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
          this.clearanceTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err:any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }, 1000);
  }

  onChange() {
    const choosen = this.selectedClearance[this.selectedClearance.length - 1];
    this.selectedClearance.length = 0;
    this.selectedClearance.push(choosen);
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedClearance = linedata;
    }
    if (this.selectedClearance.length === 0 && type != 'New') {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedClearance[0] : linedata,
        pageflow: type,
      });
      this.router.navigate(['/main/master/clearanceCharges-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedClearance.length === 0) {
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
        line: this.selectedClearance,
        module: 'Clearance Charges',
        body: 'This action cannot be undone. All values associated with this field will be lost.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
       this.deleterecord(this.selectedClearance)
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res:any) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Deleted',
          key: 'br',
          detail: lines[0].partnerId + '  Deleted successfully',
        });
        this.spin.hide();
        this.initialCall();
      },
      error: (err:any) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      },
    });
  }

  downloadExcel() {
    const exportData = this.clearanceTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, 'Customer');
  }

  searchform = this.fb.group({
    clearanceChargesId: [],
    partnerId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    clearanceChargesId: 'Clearance Charges ID',
    partnerId: 'partner',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  clearanceChargesIdDropdown: any = [];
  partnerIdDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.clearanceTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.clearanceChargesId != null) {
        this.clearanceChargesIdDropdown.push({ value: res.clearanceChargesId, label: res.clearanceChargesId });
        this.clearanceChargesIdDropdown = this.cs.removeDuplicatesFromArrayList(this.clearanceChargesIdDropdown, 'value');
      }
      if (res.partnerId != null) {
        this.partnerIdDropdown.push({ value: res.partnerId, label: res.partnerName });
        this.partnerIdDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerIdDropdown, 'value');
      }
    
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('customer') overlayPanel!: OverlayPanel;
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
        res = this.cs.removeDuplicatesFromArrayList(res, 'partnerId');
        this.clearanceTable = res;
        this.spin.hide();
        this.overlayPanel.hide();
      },
      error: (err:any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }
  reset() {
    this.searchform.reset();
    this.searchform = this.fb.group({
      clearanceChargesId: [],
      partnerId: [],
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

