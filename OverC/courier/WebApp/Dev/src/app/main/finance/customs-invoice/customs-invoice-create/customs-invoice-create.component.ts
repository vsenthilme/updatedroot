import { Component, ViewChild } from '@angular/core';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ConsoleService } from '../../../airport/console/console.service';
import { Calendar } from 'primeng/calendar';
import { PrealertService } from '../../../airport/pre-alert-manifest/prealert.service';
import { InvoiceService } from '../invoice.service';

@Component({
  selector: 'app-customs-invoice-create',
  templateUrl: './customs-invoice-create.component.html',
  styleUrl: './customs-invoice-create.component.scss'
})
export class CustomsInvoiceCreateComponent {
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
    private service: InvoiceService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
    private PrealertService: PrealertService,
  ) {
    this.status = [
      { value: '2', label: 'Updated' },
      { value: '1', label: 'Created' }
    ];
  }


  invoiceTable: any[] = [];
  pageToken: any;
  submitted = false;

  // form builder initialize
  form = this.fb.group({
    companyId: [this.auth.companyId,],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    description: [],
    invoiceDate: [],
    invoiceDateFE: [, Validators.required],
    invoiceHeaderId: [],
    invoiceLines: this.fb.array([]),
    invoiceNo: [],
    languageId: [this.auth.languageId,],
    partnerId: [],
    partnerName: [],
    referenceField1: [''],
    referenceField10: [''],
    referenceField2: [''],
    referenceField3: [''],
    referenceField4: [''],
    referenceField5: [''],
    referenceField6: [''],
    referenceField7: [''],
    referenceField8: [''],
    referenceField9: [''],
    statusId: ['1',],
    updatedBy: [],
    updatedOn: []
  });

  get invoiceLines(): FormArray {
    return this.form.get('invoiceLines') as FormArray;
  }

  patchForm(data: any) {
    const itemsArray = this.form.get('invoiceLines') as FormArray;
    data.forEach((line: any) => {
      itemsArray.push(this.patchLine(line));
    });
  }

  patchLine(data: any) {
    return this.fb.group({
      approvals: [data.approvals],
      clearanceCharge: [data.clearanceCharge],
      companyId: [this.auth.companyId,],
      createdBy: [data.createdBy],
      createdOn: [data.createdOn],
      customDuty: [data.customDuty || 0],
      handlingFees: [data.handlingFees || 0],
      costPerShipment: [data.costPerShipment || 0],
      deletionIndicator: [data.deletionIndicator || 0],
      foodApproval: [data.foodApproval],
      invoiceLineId: [data.invoiceLineId || null],
      invoiceNo: [data.invoiceNo],
      languageId: [this.auth.languageId,],
      noOfShipments: [data.noOfShipments || 0],
      otherApproval: [data.otherApproval],
      partnerMasterAirwayBill: [data.partnerMasterAirwayBill],
      referenceField1: [data.referenceField1],
      referenceField10: [data.referenceField10],
      referenceField2: [data.referenceField2],
      referenceField3: [data.referenceField3],
      referenceField4: [data.referenceField4],
      referenceField5: [data.referenceField5],
      referenceField6: [data.referenceField6],
      referenceField7: [data.referenceField7],
      referenceField8: [data.referenceField8],
      referenceField9: [data.referenceField9],
      totalValue: [data.totalValue || 0],
      totalApproval: [data.totalApproval || 0],
      updatedBy: [data.updatedBy],
      updatedOn: [data.updatedOn]
    });
  }

  searchForm = this.fb.group({
    fromDate: ['',],
    partnerId: [, Validators.required],
    partnerMasterAirwayBill: [],
    toDate: ['',],
    fullDate: [],
  })

  onChange() {
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
  }
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    this.dropdownlist();

    const dataToSend = ['Finance', 'Customs Invoice', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.callTableHeader();

    if (this.pageToken.pageflow != 'New') {
      this.form.controls.invoiceNo.disable();
      this.fill(this.pageToken.line);
    }
  }

  cols: any[] = [];
  target: any[] = [];
  callTableHeader() {
    this.cols = [
      { field: 'partnerMasterAirwayBill', header: 'MAWB', format: 'Input' },
      { field: 'noOfShipments', header: 'Total No of Shipments', format: 'Input' },
      { field: 'clearanceCharge', header: 'Clearance Charges AWBs' },
      { field: 'totalApproval', header: 'Total Approvals' },
      { field: 'handlingFees', header: 'Handling Fees' },
      { field: 'customDuty', header: 'Custom Duty 5%' },
      { field: 'totalValue', header: 'Total Value' }

    ];
    this.target = [
    ];
  }

  customerList: any[] = [];
  mawbList: any[] = [];
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.customer.url,
    ]).subscribe({
      next: (results: any) => {
        this.customerList = this.cas.forLanguageFilter(results[0], this.cas.dropdownlist.setup.customer.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }
  fill(line: any) {
    this.service.search({ invoiceNo: [line.invoiceNo] }).subscribe({
      next: (result) => {
        this.invoiceLines.clear();
        this.form.patchValue(result[0]);
        if(this.form.controls.invoiceDate.value){
        let date: any = this.cs.pCalendar(this.form.controls.invoiceDate.value);
        this.form.controls.invoiceDateFE.patchValue(date);
        }
        this.patchForm(result[0].invoiceLines);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
  save() {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }


    if (this.pageToken.pageflow == 'New') {
      this.spin.show();

      let date: any = this.cs.jsonDate(this.form.controls.invoiceDateFE.value);
      this.form.controls.invoiceDate.patchValue(date);
      
      const selectedValues = this.selectedInvoice.map(item => item.getRawValue());
      this.invoiceLines.clear();
      this.patchForm(selectedValues);

      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Created',
            key: 'br',
            detail: res[0].invoiceNo + ' has been created successfully',
          });
          this.router.navigate(['/main/finance/invoice']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      let date: any = this.cs.jsonDate(this.form.controls.invoiceDateFE.value);
      this.form.controls.invoiceDate.patchValue(date);
      this.spin.show();
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].invoiceNo + ' has been updated successfully',
          });
          this.router.navigate(['/main/finance/invoice']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  getColspan(): number {
    return this.cols.length + 2; // +1 for the expanded content column
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
    if (this.searchForm.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Error', key: 'br', detail: 'Please fill the customer field to continue' });
      return;
    }

    this.spin.show();
    this.service.executeInvoiceReport(this.searchForm.getRawValue()).subscribe({
      next: (res) => {
      if(res.length > 0){
        this.submitted = true;  
        this.invoiceLines.clear();
        this.patchForm(res);
        this.selectedInvoice = this.invoiceLines.controls as FormArray[];
      }
      this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    })
  }

  customerChange() {
    const list: any = this.customerList.filter(x => x.value == this.searchForm.controls.partnerId.value);
    this.form.controls.partnerName.patchValue(list[0].label);
    this.form.controls.partnerId.patchValue(list[0].value);
    this.form.controls.partnerId.disable();

    
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.partnerId = [this.form.controls.partnerId.value];
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.mawbList = this.cas.foreachlistWithoutKey(result, { key: 'partnerMasterAirwayBill', value: 'partnerMasterAirwayBill' });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })

  }

  // To calculate total table value
  calculcateTotal(line: any) {
    let lines = line.getRawValue();
    let totalValue = 0;
    totalValue =
      Number(lines.clearanceCharge)
      + Number(lines.totalApproval)
      + Number(lines.handlingFees)
      + Number(lines.costPerShipment)
      + Number(lines.customDuty)

    let totalValue1 = totalValue.toFixed(3);

    line.controls.totalValue.patchValue(totalValue1);
  }

  isSelected(item: any): boolean {
    return this.selectedInvoice.includes(item);
  }
}


