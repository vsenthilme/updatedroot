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
import { HubPartnerAssignmentService } from './hub-partner-assignment.service';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';

@Component({
  selector: 'app-hub-partner-assignment',
  templateUrl: './hub-partner-assignment.component.html',
  styleUrl: './hub-partner-assignment.component.scss'
})
export class HubPartnerAssignmentComponent {
  hubPartnerAssignmentTable: any[] = [];
  selectedHubPartnerAssignment: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: HubPartnerAssignmentService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
  ) { }

  fullDate: any;
  today: any;
  ngOnInit() {
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Master', 'Hub Partner Assignment'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyName', header: 'Company' },
      { field: 'partnerId', header: 'Partner ID', format:'hyperLink' },
      { field: 'partnerType', header: 'Partner Type' },
      { field: 'partnerName', header: 'Partner Name' },
      { field: 'hubName', header: 'Hub Name' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'remark', header: 'Remark' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'languageId', header: 'Language ID' },
      { field: 'languageDescription', header: 'Language' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'hubCode', header: 'Hub Code' },
      { field: 'hubCategory', header: 'Hub Category' },
      { field: 'statusId', header: 'Status ID' },
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
          this.hubPartnerAssignmentTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        }, error: (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 1000);
  }

  onChange() {
    const choosen = this.selectedHubPartnerAssignment[this.selectedHubPartnerAssignment.length - 1];
    this.selectedHubPartnerAssignment.length = 0;
    this.selectedHubPartnerAssignment.push(choosen);
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
        this.deleterecord(this.selectedHubPartnerAssignment[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
 if(linedata){
  this.selectedHubPartnerAssignment = linedata;
}
    if (this.selectedHubPartnerAssignment.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedHubPartnerAssignment[0] : linedata, pageflow: type });
      this.router.navigate(['/main/master/hubPartnerAssignment-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedHubPartnerAssignment.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedHubPartnerAssignment, module: 'Hub Partner Assignment', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedHubPartnerAssignment[0]);
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res: any) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: lines.partnerId + ' deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err: any) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.hubPartnerAssignmentTable.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'date') {
          console.log(3)
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }

      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Hub Partner Assignment');
  }

  searchform = this.fb.group({
    hubCode: [],
    hubCategory: [],
    partnerId: [],
    partnerType: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    hubCode: 'Hub',
    hubCategory: 'Hub Category',
    partnerId: 'Partner',
    partnerType: 'Partner Type',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  hubCodeDropdown: any = [];
  hubCategoryDropdown: any = [];
  partnerIdDropdown: any = [];
  partnerTypeDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.hubPartnerAssignmentTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.hubCode != null) {
        this.hubCodeDropdown.push({ value: res.hubCode, label: res.hubName });
        this.hubCodeDropdown = this.cs.removeDuplicatesFromArrayList(this.hubCodeDropdown, 'value');
      }
      if (res.hubCategory != null) {
        this.hubCategoryDropdown.push({ value: res.hubCategory, label: res.hubCategory });
        this.hubCategoryDropdown = this.cs.removeDuplicatesFromArrayList(this.hubCategoryDropdown, 'value');
      }
      if (res.partnerId != null) {
        this.partnerIdDropdown.push({ value: res.partnerId, label: res.partnerName });
        this.partnerIdDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerIdDropdown, 'value');
      }
      if (res.partnerType != null) {
        this.partnerTypeDropdown.push({ value: res.partnerType, label: res.partnerType });
        this.partnerTypeDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerTypeDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('hubPartnerAssignment') overlayPanel!: OverlayPanel;
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
        this.hubPartnerAssignmentTable = res;
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
      hubCode: [],
      hubCategory: [],
      partnerId: [],
      partnerType: [],
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
