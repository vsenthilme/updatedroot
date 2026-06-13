import { Component, ViewChild } from '@angular/core';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { Router } from '@angular/router';
import { PathNameService } from '../../../common-service/path-name.service';
import { ProvinceService } from './province.service';
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
  selector: 'app-province',
  templateUrl: './province.component.html',
  styleUrl: './province.component.scss'
})
export class ProvinceComponent {

  provinceTable: any[] = [];
  selectedProvince: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ProvinceService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
  ) { }

  fullDate: any;
  today: any;
  ngOnInit(): void {
    const dataToSend = ['Setup', 'Province'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyName', header: 'Company' },
      { field: 'countryName', header: 'Country' },
      { field: 'provinceId', header: 'Province ID',format:'hyperLink' },
      { field: 'provinceName', header: 'Province Name' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'remark', header: 'Remark' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'countryId', header: 'Country ID' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'statusId', header: 'Status ID' },
      { field: 'languageDescription', header: 'Language' },
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
          this.provinceTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 1000);
  }

  onChange() {
    const choosen = this.selectedProvince[this.selectedProvince.length - 1];
    this.selectedProvince.length = 0;
    this.selectedProvince.push(choosen);
  }

  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { target: this.cols, source: this.target },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedProvince[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedProvince = linedata;
    }
    if (this.selectedProvince.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedProvince[0] : linedata, pageflow: type });
      this.router.navigate(['/main/idMaster/province-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedProvince.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedProvince, module: 'Province', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedProvince[0]);
      }
    })
  }
  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: lines.provinceId + ' deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.provinceTable.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'data') {
          console.log(3)
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    // Excel service
    this.cs.exportAsExcel(exportData, 'Province');
  }

  searchform = this.fb.group({
    countryId: [],
    provinceId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    countryId: 'Country',
    provinceId: 'Province',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  countryDropdown: any = [];
  provinceDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.provinceTable.forEach(res => {

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
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('province') overlayPanel!: OverlayPanel;
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
        this.provinceTable = res;
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
