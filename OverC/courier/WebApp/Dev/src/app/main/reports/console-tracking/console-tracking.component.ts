import { DatePipe } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { AuthService } from '../../../core/core';
import { ReportService } from '../report.service';

@Component({
  selector: 'app-console-tracking',
  templateUrl: './console-tracking.component.html',
  styleUrl: './console-tracking.component.scss'
})
export class ConsoleTrackingComponent {
  consoleTrackingReportTable: any[] = [];
  selectedConsoleTracking: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ReportService,
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
    const dataToSend = ['Mid-Mile', 'Console Tracking'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'noOfShipmentsScanned', header: 'Total Shipments', format: 'hyperLink' },
      { field: 'noOfConsoles', header: 'Consolidated Shipments', format: 'hyperLink' },
      { field: 'noOfUnconsolidatedShipments', header: 'Unconsolidated Shipments', format: 'hyperLink' },
    ];
    this.target = [
      { field: 'languageId', header: 'Language ID' },
      { field: 'companyId', header: 'Company ID' },
    ];
  }

  initialCall() {
    // setTimeout(() => {
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    this.service.search(obj).subscribe({
      next: (res: any[] = []) => {
        this.consoleTrackingReportTable = res;
        this.getSearchDropdown();
        this.spin.hide();
      }, error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
    // }, 1000);
  }

  onChange() {
    const choosen = this.selectedConsoleTracking[this.selectedConsoleTracking.length - 1];
    this.selectedConsoleTracking.length = 0;
    this.selectedConsoleTracking.push(choosen);
  }

  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { target: this.cols, source: this.target, },
    });
  }

  downloadExcel() {
    const exportData = this.consoleTrackingReportTable.map(item => {
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
    this.cs.exportAsExcel(exportData, 'Console Tracking');
  }

  searchform = this.fb.group({
    partnerMasterAirwayBill: [],
    partnerHouseAirwayBill: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    partnerMasterAirwayBill: 'Partner MAWB',
    partnerHouseAirwayBill: 'Partner HAWB',
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  partnerMAWBDropdown: any = [];
  partnerHAWBDropdown: any = [];

  getSearchDropdown() {

    this.consoleTrackingReportTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.partnerMasterAirwayBill != null) {
        this.partnerMAWBDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.partnerMAWBDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerMAWBDropdown, 'value');
      }
      if (res.partnerHouseAirwayBill != null) {
        this.partnerHAWBDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.partnerHAWBDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerHAWBDropdown, 'value');
      }
    })
  }

  @ViewChild('consoleTrackingReport') overlayPanel!: OverlayPanel;
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
        this.consoleTrackingReportTable = res;
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
      partnerMasterAirwayBill: [],
      partnerHouseAirwayBill: [],
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

  noOfShipments(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true });
      this.router.navigate(['/main/airport/preAlertManifest-update/' + paramdata]);
    }
  }

  noOfConsoles(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any Row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true, module: 'consolidated'  });
      this.router.navigate(['/main/airport/console-edit/' + paramdata]);
    }
  }

  noOfUnconsolidated(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any Row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true, module: 'unconsolidated' });
      this.router.navigate(['/main/airport/console-edit/' + paramdata]);
    }
  }

}

