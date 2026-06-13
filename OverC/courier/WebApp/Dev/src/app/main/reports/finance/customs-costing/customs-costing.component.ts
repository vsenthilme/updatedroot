import { Component, ViewChild } from '@angular/core';
import { FormBuilder, Validators,FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { Calendar } from 'primeng/calendar';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { InvoiceService } from '../../../finance/customs-invoice/invoice.service';
import { PrealertService } from '../../../airport/pre-alert-manifest/prealert.service';
import { ReportService } from '../../report.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-customs-costing',
  templateUrl: './customs-costing.component.html',
  styleUrl: './customs-costing.component.scss'
})
export class CustomsCostingComponent {
  costSheetTable: any[] = [];
  invoiceTag: any;
  selectedCostSheet: any;

  active: number | undefined = 0;
  status: any[] = [];
  selectedInvoice: any[] = [];
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private service: ReportService,
    private messageService: MessageService,
    private PrealertService: PrealertService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
  ) {
    this.status = [
      { value: '2', label: 'Updated' },
      { value: '1', label: 'Created' }
    ];
  }


  invoiceTable: any[] = [];
  pageToken: any;
  submitted = false;

  searchForm = this.fb.group({
    fromDate: [,],
    partnerId: [, Validators.required],
    partnerMasterAirwayBill: [],
    toDate: [,],
    fullDate: [],
  })

  onChange() {
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
  }
  ngOnInit() {
    this.dropdownlist();

    const dataToSend = ['Finance', 'Reports', 'Customs Costing'];
    this.path.setData(dataToSend);

    this.callTableHeader();


  }

  cols: any[] = [];
  target: any[] = [];
  callTableHeader() {
    this.cols = [
      { field: 'partnerMasterAirwayBill', header: 'Manifest', showFooter: false, },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB', showFooter: false, },
      { field: 'houseAirwayBill', header: 'Consignment No', showFooter: false, },
      { field: 'shipper', header: 'Shipper', showFooter: false, },
      { field: 'consigneePhoneNo', header: 'Consignee Phone', showFooter: false, },
      { field: 'addDestinationDetails', header: 'Consignee Address', showFooter: false, },
      { field: 'description', header: 'Description', showFooter: false, },
      { field: 'hsCode', header: 'HS Code', showFooter: false, },
      { field: 'noOfPieces', header: 'No Of Piece', showFooter: false, },
      { field: 'totalWeight', header: 'Weight', showFooter: false, },
      { field: 'incoTerm', header: 'IncoTerm', showFooter: false, },
      { field: 'consignmentValue', header: 'Value', showFooter: false, },
      { field: 'currency', header: 'Currency', showFooter: false, },
      { field: 'customsValue', header: 'Value KD', showFooter: false, },
      { field: 'nasDelivery', header: 'NAS Delivery', format: 'Input', showFooter: true, },
      { field: 'global', header: 'Global', format: 'Input', showFooter: true, },
      { field: 'handlingFork', header: 'Handling Fork' , format: 'Input', showFooter: true,},
      { field: 'stampCharges', header: 'Stamp Charges', format: 'Input', showFooter: true, },
      { field: 'labours', header: 'Labours', format: 'Input', showFooter: true, },
      { field: 'otherCharges', header: 'Other Charges', format: 'Input', showFooter: true, },
      { field: 'customDuty', header: 'Custom Duty', format: 'Input', showFooter: true, },
      { field: 'approvals', header: 'Approvals', format: 'Input', showFooter: true, },
      { field: 'totalCostPerShipment', header: 'Cost per Shipment' , format: 'Input', showFooter: true},



    ];
    this.target = [
    ];
  }
 
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = "required") {
    const controlInstance = this.searchForm.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  customerList: any[] = [];
  mawbList: any[] = [];
  dropdownlist() {
    this.spin.show();
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.mawbList = this.cas.foreachlistWithoutKey(result, { key: 'partnerMasterAirwayBill', value: 'partnerMasterAirwayBill' });
        this.customerList = this.cas.foreachlistWithoutKey(result, { key: 'partnerId', value: 'partnerName' });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
  @ViewChild('myStartCalendar') startCalendar!: Calendar;

  filter(type: any) {
    if (this.searchForm.controls.fullDate.value != null) {
      this.searchForm.controls.fromDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[0]) ? this.searchForm.controls.fullDate.value[0] : null);
      this.searchForm.controls.toDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[1]) ? this.searchForm.controls.fullDate.value[1] : null);
      if (this.searchForm.controls.toDate.value == null) {
        return;
      }
    }
  }

  execute() {
    this.spin.show();
    this.submitted = true;
    if (this.searchForm.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill required fields to continue' });
      this.spin.hide();
      return;
    }
    else{
    this.service.executeCostSheet(this.searchForm.getRawValue()).subscribe({
      next: (res : any) => {
        if (res.length > 0) {
          this.submitted = true;
          this.costSheetTable = res;
        }
        this.spin.hide();
      },
      error: (err : any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    })
  }
  }
  // To calculate total table value
  calculcateTotal(line: any) {
    let lines = line.getRawValue();
    let totalValue = 0;
    totalValue =
      Number(lines.clearanceCharge)
      + Number(lines.otherApproval)
      + Number(lines.foodApproval)
      + Number(lines.approvals)
      + Number(lines.handlingFees)
      + Number(lines.costPerShipment)
      + Number(lines.customDuty)

    let totalValue1 = totalValue.toFixed(3);

    line.controls.totalValue.patchValue(totalValue1);
  }
   reset(){
    this.searchForm.reset();
   }
  downloadExcel() {
    const exportData = this.costSheetTable.map(item => {
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
    this.cs.exportAsExcel(exportData, ' Customs Costing');
  }

  
  calculateFooterTotal(field: string): number {
    let total = 0;
    this.costSheetTable.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }
}


