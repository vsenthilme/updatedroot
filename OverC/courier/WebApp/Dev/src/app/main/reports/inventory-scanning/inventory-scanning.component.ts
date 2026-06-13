import { DatePipe } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { AuthService } from '../../../core/core';
import { ConsignmentService } from '../../operation/consignment/consignment.service';

@Component({
  selector: 'app-inventory-scanning',
  templateUrl: './inventory-scanning.component.html',
  styleUrl: './inventory-scanning.component.scss'
})
export class InventoryScanningComponent {
  inventoryScanningTable: any[] = [];
  selectedInventoryScanning: any[] = [];
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
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
  ) { }

  fullData: any;
  today: any;
  activeLink: any;
  pageFlow:any;

  ngOnInit(): void {
    const link = this.router.url;
    this.activeLink = link.split('/')[3];

    if (this.activeLink == 'pendingCustoms') {
      const dataToSend = ['Mid-Mile', 'Pending Customs'];
      this.path.setData(dataToSend);
      this.pageFlow = 'Pending Customs';
    } else {
      const dataToSend = ['Mid-Mile', 'Inventory Scan'];
      this.path.setData(dataToSend);
      this.pageFlow = 'Inventory Scan';
    }



    this.callTableHeader();
    this.initialCall();

  }

  callTableHeader() {
    this.cols = [
      // { field: 'hawbTypeId', header: 'HAWB Type ID' },
      // { field: 'hawbType', header: 'HAWB Type' },
      // { field: 'hawbTypeDescription', header: 'HAWB Type Description' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'houseAirwayBill', header: 'Consignment No' },
      { field: 'pieceId', header: 'Piece ID' },
      // { field: 'masterAirwayBill', header: 'Master Airway Bill' },
      // { field: 'pieceTypeId', header: 'Piece Type ID' },
      // { field: 'pieceType', header: 'Piece Type' },
      // { field: 'pieceTypeDescription', header: 'Piece Type Description' },
      { field: 'hawbTimeStamp', header: 'Scanned Time', format: 'date' },
      // { field: 'pieceTimeStamp', header: 'Scanned Time', format: 'date' },
      { field: 'updatedBy', header: 'Scanned Officer' },
      // { field: 'createdOn', header: 'Created On', format: 'date' },
    ];

    this.target = [
      { field: 'pieceId', header: 'Piece ID' },
      { field: 'companyName', header: 'Company' },
      { field: 'languageDescription', header: 'Language' },
      { field: 'companyId', header: ' Company ID' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'bagId', header: 'Bag ID' },
    ];
  }

  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      if (this.activeLink == 'pendingCustoms') { obj.hawbTypeId = ["6",]; } else { obj.hawbTypeId = ["47"] }
      this.service.searchStatus(obj).subscribe({
        next: (res: any) => {
          this.inventoryScanningTable = res;
          this.inventoryScanningTable = this.cs.removeDuplicatesFromArrayList(this.inventoryScanningTable, 'partnerMasterAirwayBill')
          this.getSearchDropdown();
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 1000);
  }

  isSelected(item: any): boolean {
    return this.selectedInventoryScanning.includes(item);
  }

  onChange() {
    const choosen = this.selectedInventoryScanning[this.selectedInventoryScanning.length - 1];
    this.selectedInventoryScanning.length = 0;
    this.selectedInventoryScanning.push(choosen);
  }

  downloadExcel() {

    const inventoryScanningReport = [
      { field: 'hawbTypeId', header: 'HAWB Type ID' },
      { field: 'hawbType', header: 'HAWB Type' },
      { field: 'hawbTypeDescription', header: 'HAWB Type Description' },
      { field: 'hawbTimeStamp', header: 'Time Stamp', format: 'date' },
      { field: 'houseAirwayBill', header: 'Consignment No' },
      { field: 'masterAirwayBill', header: 'Master Airway Bill' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'pieceTypeId', header: 'Piece Type ID' },
      { field: 'pieceType', header: 'Piece Type' },
      { field: 'pieceTypeDescription', header: 'Piece Type Description' },
      { field: 'pieceTimeStamp', header: 'Time Stamp', format: 'date' },
      { field: 'pieceId', header: 'Piece ID' },
      { field: 'companyName', header: 'Company' },
      { field: 'languageDescription', header: 'Language' },
      { field: 'companyId', header: ' Company ID' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'bagId', header: 'Bag ID' },

      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];

    const exportData = this.inventoryScanningTable.map(item => {
      const exportItem: any = {};
      inventoryScanningReport.forEach(col => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Inventory Scan Report');
  }

  searchform = this.fb.group({
    partnerHouseAirwayBill: [],
    partnerMasterAirwayBill: [],
    hawbTypeId: [],
    hawbTimeStamp: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    partnerHouseAirwayBill: 'Partner HAWB',
    partnerMasterAirwayBill: 'Partner MAWB',
    hawbTypeId: 'Action',
    hawbTimeStamp: 'Date Time'
  };

  houseAirwayBillDropdown: any[] = [];
  masterAirwayBillDropdown: any = [];
  statusDropdown: any[] = [];
  timeStampDropdown: any[] = [];

  getSearchDropdown() {

    this.inventoryScanningTable.forEach(res => {

      if (res.partnerHouseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, 'value');
      }
      if (res.partnerMasterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, 'value');
      }
      if (res.hawbTypeId != null) {
        this.statusDropdown = [{ value: 6, label: '6 - Pending Customs' }, { value: 47, label: '47 - Gateway Inventory' }]
      }
      if (res.hawbTimeStamp != null) {
        const formattedDate = this.datePipe.transform(res.hawbTimeStamp, 'MMM d, y, h:mm a');
        this.timeStampDropdown.push({ value: res.hawbTimeStamp, label: formattedDate });
        this.timeStampDropdown = this.cs.removeDuplicatesFromArrayList(this.timeStampDropdown, 'value');
      }
    })

  }

  @ViewChild('inventoryScanningReport') overlayPanel!: OverlayPanel;
  closeOverLay() {
    this.overlayPanel.hide();
  }

  fieldsWithValue: any;

  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues)
      .filter(key => formValues[key as keyof typeof formValues] !== null && formValues[key as keyof typeof formValues] !== undefined && key !== 'companyId' && key !== 'languageId')
      .map(key => this.fieldDisplayNames[key] || key);

    this.spin.show();
    this.service.searchStatus(this.searchform.getRawValue()).subscribe({
      next: (res: any) => {
        this.inventoryScanningTable = res;
        this.spin.hide();
        this.overlayPanel.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  reset() {
    this.searchform.reset();
    this.searchform = this.fb.group({
      partnerHouseAirwayBill: [],
      partnerMasterAirwayBill: [],
      hawbTypeId: [],
      hawbTimeStamp: [],
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

