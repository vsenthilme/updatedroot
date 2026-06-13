import { Component, ViewChild } from '@angular/core';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { Router } from '@angular/router';
import { PathNameService } from '../../../common-service/path-name.service';
import { CompanyService } from './company.service';
import { MessageService } from 'primeng/api';
import { MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrl: './company.component.scss',
})
export class CompanyComponent {

  companyTable: any[] = [];
  selectedCompany: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: CompanyService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private spin: NgxSpinnerService,
    private fb: FormBuilder
  ) { }

  fullDate: any;
  today: any;
  ngOnInit() {
    //to pass the breadcrumbs value to the main component
    const dataToSend = ['Setup', 'Company '];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyId', header: 'Company ID', format:'hyperLink'},
      { field: 'companyName', header: 'Company Name' },
      { field: 'addressLine1', header: 'Address Line 1' },
      { field: 'addressLine2', header: 'Address Line 2' },
      { field: 'addressLine3', header: 'Address Line 3' },
      { field: 'addressLine4', header: 'Address Line 4' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'statusId', header: 'Status ID' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'languageDescription', header: 'Language' },
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
      this.service.search(obj).subscribe({
        next: (res: any) => {
          this.companyTable = res;
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
    const choosen = this.selectedCompany[this.selectedCompany.length - 1];
    this.selectedCompany.length = 0;
    this.selectedCompany.push(choosen);
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
        this.deleterecord(this.selectedCompany[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
if(linedata){
  this.selectedCompany = linedata;
}
if (this.selectedCompany.length === 0 && type != 'New') {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedCompany[0] : linedata,
        pageflow: type,
      });
      this.router.navigate(['/main/idMaster/company-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedCompany.length === 0) {
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
        line: this.selectedCompany,
        module: 'Company',
        body: 'This action cannot be undone. All values associated with this field will be lost.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedCompany[0]);
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines.companyId).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Deleted',
          key: 'br',
          detail: lines.companyId + ' Deleted successfully',
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
    const exportData = this.companyTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, 'Company');
  }

  searchform = this.fb.group({
    countryId: [],
    provinceId: [],
    districtId: [],
    cityId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    countryId: 'Country',
    provinceId: 'Province',
    districtId: 'District',
    cityId: 'City',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  countryDropdown: any = [];
  provinceDropdown: any = [];
  districtDropdown: any = [];
  cityDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.companyTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.countryId != null) {
        this.countryDropdown.push({ value: res.countryId, label: res.countryName });
        this.countryDropdown = this.cs.removeDuplicatesFromArrayList(this.countryDropdown, 'value');
      }
      if (res.provinceId != null) {
        this.provinceDropdown.push({ value: res.provinceId, label: res.provinceName });
        this.provinceDropdown = this.cs.removeDuplicatesFromArrayList(this.provinceDropdown, 'value');
      }
      if (res.districtId != null) {
        this.districtDropdown.push({ value: res.districtId, label: res.districtName });
        this.districtDropdown = this.cs.removeDuplicatesFromArrayList(this.districtDropdown, 'value');
      }
      if (res.cityId != null) {
        this.cityDropdown.push({ value: res.cityId, label: res.cityName });
        this.cityDropdown = this.cs.removeDuplicatesFromArrayList(this.cityDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('company') overlayPanel!: OverlayPanel;
  closeOverLay() {
    this.overlayPanel.hide();
  }

  fieldsWithValue: any
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues)
      .filter(key => formValues[key as keyof typeof formValues] !== null && formValues[key as keyof typeof formValues] !== undefined && key !== 'companyId' && key !== 'languageId').map(key => this.fieldDisplayNames[key] || key);

    this.spin.show();
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res: any) => {
        this.companyTable = res;
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
      countryId: [],
      provinceId: [],
      districtId: [],
      cityId: [],
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
