import { Component, ElementRef } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ConsignorService } from '../../../master/consignor/consignor.service';
import { CustomerService } from '../../../master/customer/customer.service';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { PreAlertEditpopupComponent } from '../pre-alert-editpopup/pre-alert-editpopup.component';
import { DatePipe, Location } from '@angular/common';
import { PreAlertBulkComponent } from '../pre-alert-bulk/pre-alert-bulk.component';

@Component({
  selector: 'app-pre-alert-update',
  templateUrl: './pre-alert-update.component.html',
  styleUrl: './pre-alert-update.component.scss'
})
export class PreAlertUpdateComponent {
  partnerType: any[] = []
  active: number | undefined = 0;

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsignmentService,
    private customerService: CustomerService,
    private consignorService: ConsignorService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private el: ElementRef,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private location: Location,
  ) {
  }

  pageToken: any;

  form = this.fb.group({
    companyId: [this.auth.companyId, Validators.required],
    languageId: [this.auth.languageId, Validators.required],
    partnerHouseAirwayBill: [],
    partnerMasterAirwayBill: [],
    statusText: [],
  })

  submitted = false;
  pageFlow: any;

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    if (this.pageToken.report == true) {
      this.pageFlow = 'Total Shipments';
      const dataToSend = ['Mid-Mile', 'Total Shipments Tracking'];
      this.path.setData(dataToSend);
      this.reportTableHeader();
    }
    else {
      this.pageFlow = 'Pre Alert Manifest - ' + this.pageToken.pageflow;
      const dataToSend = ['Mid-Mile', 'Pre Alert Manifest', this.pageToken.pageflow];
      this.path.setData(dataToSend);
      this.callTableHeader();
    }
    

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.partnerMasterAirwayBill.disable();
      this.form.controls.partnerHouseAirwayBill.disable();
    }
  }

  cols: any[] = [];
  target: any[] = [];
  callTableHeader() {
    this.cols = [
      // { field: 'companyName', header: 'Company' },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'partnerName', header: 'Partner Name' },
      { field: 'flightNo', header: 'Flight No' },
      { field: 'flightName', header: 'Flight Name' },
      { field: 'estimatedTimeOfDeparture', header: 'Departure', format: 'date1' },
      { field: 'estimatedTimeOfArrival', header: 'Arrival', format: 'date1' },
      { field: 'bayanHv', header: 'Bayan HV' },
      { field: 'shipper', header: 'Shipper Name' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'incoTerm', header: 'Inco Terms' },
      { field: 'hsCode', header: 'HS Code' },
      { field: 'origin', header: 'Origin Port' },
      { field: 'description', header: 'Description' },
      { field: 'iata', header: 'IATA' },
      { field: 'noOfPieces', header: 'Number of pieces' },
      { field: 'totalWeight', header: 'Total Shipment Weight' },
      { field: 'consignmentValue', header: 'Total Value' },
      { field: 'consignmentValueLocal', header: 'ConsignmentValueLocal' },
      { field: 'currency', header: 'Currency' },
      { field: 'hawbTypeDescription', header: 'Event' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date' },
      { field: 'totalCostPerShipment', header: 'Cost Per Shipment'},
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
  }
  reportTableHeader() {
    this.cols = [
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB' },
      { field: 'partnerType', header: 'Partner Type' },
      { field: 'flightNo', header: 'Flight No' },
      { field: 'flightName', header: 'Flight Name' },
      { field: 'estimatedTimeOfDeparture', header: 'Departure', format: 'date' },
      { field: 'estimatedTimeOfArrival', header: 'Arrival', format: 'date' },
      { field: 'bayanHv', header: 'Bayan HV' },
      { field: 'shipper', header: 'Shipper Name' },
      { field: 'consigneeName', header: 'Consignee Name' },
      { field: 'incoTerm', header: 'Inco Terms' },
      { field: 'hsCode', header: 'HS Code' },
      { field: 'origin', header: 'Origin Port' },
      { field: 'description', header: 'Description' },
      { field: 'iata', header: 'IATA' },
      { field: 'noOfPieces', header: 'Number of pieces' },
      { field: 'totalWeight', header: 'Total Shipment Weight' },
      { field: 'consignmentValue', header: 'Total Value' },
      { field: 'consignmentValueLocal', header: 'ConsignmentValueLocal' },
      { field: 'currency', header: 'Currency' },
      { field: 'hawbTypeDescription', header: 'Event' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
  }


  preAlertManifestTableArray: any[] = [];
  selectedPreAlertManifest: any[] = [];

  fill(line: any) {
    this.form.patchValue(line);

    if (this.pageToken.report == true) {
      this.reportTableHeader();
    } else {
      this.callTableHeader();
    }

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.partnerMasterAirwayBill = [this.pageToken.line.partnerMasterAirwayBill];

    this.service.searchPrealert(obj).subscribe({
      next: (res: any) => {
        this.preAlertManifestTableArray = res;
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  panelOpenState = false;
  back() {
    this.location.back();
  }



  // PreAlert Edit Table Popup 

  editItem(data: any, item: any): void {
    const dialogRef = this.dialog.open(PreAlertEditpopupComponent, {
      disableClose: true,
      width: '70%',
      //height: '50%',
      maxWidth: '82%',
      position: { top:'6.5%', left: '30%' },
      data: { pageflow: data, code: item },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fill(this.pageToken.line);
    });
  }

  //Download
  downloadExcel() {

    const preAlertColumn = [
      { field: 'partnerMasterAirwayBill', header: 'MAWB' },
      { field: 'partnerHouseAirwayBill', header: 'HAWB' },
      { field: 'totalWeight', header: 'Weight' },
      { field: 'noOfPieces', header: 'PCS' },
      { field: 'consignmentValue', header: 'Value' },
      { field: 'bayanHv', header: 'Bayan HV' },
      { field: 'currency', header: 'Currency' },
      { field: 'description', header: 'Description(en)' },
      { field: 'consigneeName', header: 'Cnee Name' },
      { field: 'shipper', header: 'Shipper' },
      { field: 'origin', header: 'Origin' },
      { field: 'originCode', header: 'Origin Code' },
      { field: 'customsValue', header: 'Value KD' },
      { field: 'iata', header: 'IATA' },
      { field: 'hsCode', header: 'HSCode' },
      { field: 'incoTerm', header: 'DDU & DDB' },
    ]
    const exportData = this.preAlertManifestTableArray.map(item => {
      const exportItem: any = {};
      preAlertColumn.forEach(col => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsPrealertExcel(exportData, 'Pre-Alert Manifest');
  }
  updateBulk() {
    if (this.selectedPreAlertManifest.length == 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
      return;
    }
    const dialogRef = this.dialog.open(PreAlertBulkComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'PreAlertManifest', code: this.selectedPreAlertManifest },
    });

    dialogRef.afterClosed().subscribe((result) => {
      // this.initialCall();
      this.ngOnInit();
    });
  }

}
