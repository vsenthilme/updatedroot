import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MessageService } from 'primeng/api';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { PathNameService } from '../../../common-service/path-name.service';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { CurrencyExchangeRateService } from './currency-exchange-rate.service';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';

@Component({
  selector: 'app-currency-exchange-rate',
  templateUrl: './currency-exchange-rate.component.html',
  styleUrl: './currency-exchange-rate.component.scss'
})
export class CurrencyExchangeRateComponent {

  currencyExchangeRateTable: any[] = [];
  selectedCurrencyExchangeRate: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: CurrencyExchangeRateService,
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
    const dataToSend = ['Master', 'Exchange Rate'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyName', header: 'Company' },
      { field: 'fromCurrencyId', header: 'From Currency', format:'hyperLink' },
      { field: 'toCurrencyId', header: 'To Currency' },
      { field: 'fromCurrencyValue', header: 'From Currency Value' },
      { field: 'toCurrencyValue', header: 'To Currency Value' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'languageId', header: 'Language ID' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'statusId', header: 'Status ID' },
      { field: 'remark', header: 'Remark' },
      { field: 'languageDescription', header: 'Language' },
      { field: 'fromCurrencyDescription', header: 'From Currency Description' },
      { field: 'toCurrencyDescription', header: 'To Currency Description' },
      { field: 'statusId', header: 'Status ID' },
      { field: 'referenceField1', header: 'Reference Field 1' },
      { field: 'referenceField2', header: 'Reference Field 2' },
      { field: 'referenceField3', header: 'Reference Field 3' },
      { field: 'referenceField4', header: 'Reference Field 4' },
      { field: 'referenceField6', header: 'Reference Field 6' },
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
          this.currencyExchangeRateTable = res;
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
    const choosen = this.selectedCurrencyExchangeRate[this.selectedCurrencyExchangeRate.length - 1];
    this.selectedCurrencyExchangeRate.length = 0;
    this.selectedCurrencyExchangeRate.push(choosen);
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
        this.deleterecord(this.selectedCurrencyExchangeRate[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedCurrencyExchangeRate = linedata;
    }
    if (this.selectedCurrencyExchangeRate.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCurrencyExchangeRate[0] : linedata, pageflow: type });
      this.router.navigate(['/main/master/currencyExchangeRate-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedCurrencyExchangeRate.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedCurrencyExchangeRate, module: 'Exchange Rate', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedCurrencyExchangeRate[0]);
      }
    });
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: 'CurrencyExchangeRate deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.currencyExchangeRateTable.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'date') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }

      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Currency Exchange Rate');
  }

  searchform = this.fb.group({
    fromCurrencyId: [],
    toCurrencyId: [],
    statusId: []
  })

  readonly fieldDisplayNames: Record<string, string> = {
    fromCurrencyId: 'From Currency',
    toCurrencyId: 'To Currency',
    statusId: 'Status'
  };

  fromCurrencyDropdown: any = [];
  toCurrencyDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.currencyExchangeRateTable.forEach(res => {

      if (res.fromCurrencyId != null) {
        this.fromCurrencyDropdown.push({ value: res.fromCurrencyId, label: res.fromCurrencyDescription });
        this.fromCurrencyDropdown = this.cs.removeDuplicatesFromArrayList(this.fromCurrencyDropdown, 'value');
      }
      if (res.toCurrencyId != null) {
        this.toCurrencyDropdown.push({ value: res.toCurrencyId, label: res.toCurrencyDescription });
        this.toCurrencyDropdown = this.cs.removeDuplicatesFromArrayList(this.fromCurrencyDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('currencyExchangeRate') overlayPanel!: OverlayPanel;
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
        this.currencyExchangeRateTable = res;
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
      fromCurrencyId: [],
      toCurrencyId: [],
      statusId: []
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



