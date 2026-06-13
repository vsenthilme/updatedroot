import { DatePipe, Location } from '@angular/common';
import { ChangeDetectorRef, Component, ElementRef, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { DeleteComponent } from '../../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { CostingSheetService } from '../costing-sheet.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { ConsignmentLabelComponent } from '../../../pdf/consignment-label/consignment-label.component';
import { PrealertService } from '../../pre-alert-manifest/prealert.service';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { InvoicepdfComponent } from '../../../pdf/invoice/invoicepdf/invoicepdf.component';
import { format } from 'util';
import { HttpErrorResponse } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-costing-sheet-new',
  templateUrl: './costing-sheet-new.component.html',
  styleUrl: './costing-sheet-new.component.scss'
})
export class CostingSheetNewComponent {
  expenseTable: any[] = [];
  selectedExpense: any[] = [];
  cols: any[] = [];
  target: any[] = [];
  descriptionList: any[] = [];
  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private service: CostingSheetService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
    private cas: CommonAPIService,
    private PrealertService: PrealertService,
    private ConsignmentService: ConsignmentService,
    private label: ConsignmentLabelComponent,
    private numberRangeService: NumberrangeService,
    private invoice: InvoicepdfComponent,
    private el: ElementRef,
    private cdr: ChangeDetectorRef,
    private location: Location,
    private sanitizer: DomSanitizer,
  ) {
    this.descriptionList = [
      { value: 'NAS-Delivery', label: 'NAS-Delivery' },
      { value: 'Global', label: 'Global' },
      { value: 'Approval', label: 'Approval' },
      { value: 'Handling&Fork', label: 'Handling&Fork' },
      { value: 'StampChrgs', label: 'StampChrgs' },
     // { value: 'ClearanceCharge', label: 'ClearanceCharge' },
      { value: 'SpecialApprovals', label: 'SpecialApprovals' },
      { value: 'OtherApprovals', label: 'OtherApprovals' },
      { value: 'Labours', label: 'Labours' },
   //   { value: 'Others', label: 'Others' },
      { value: 'FoodApprovals', label: 'FoodApprovals' },
      { value: 'CustomDuty', label: 'CustomDuty' },
      { value: 'OtherCharges', label: 'OtherCharges' },
     // { value: 'HandlingFees', label: 'HandlingFees' }
    ];
  }


  headerForm = this.fb.group({
    costCenter: [, Validators.required],
    cashNumber: [1001,],
    dateFE: [,],
    date: ['',],
    department: ['Airport Operation',],
    remarks: [],
    cashHolder: [],
    partnerId: [],
    partnerName: [],
    noOfShipments: [],
    remark: [],
    referenceField3: [],
    referenceField2: [],
  });


  form = this.fb.group({
    customsCosting: this.fb.array([]) // Initialize an empty FormArray
  });

  get customCostingDetails(): FormArray {
    return this.form.get('customsCosting') as FormArray;
  }

  removeItem(index: number, line: any) {
    if (line?.controls?.createLines?.value == true) {
      this.customCostingDetails.removeAt(index);
    } else {
      this.deleterecord([line.getRawValue()]);
    }
  }

  scrollToNewLine = false;
  addLines() {
    this.customCostingDetails.push(this.createForm());
    this.scrollToLast();
  }
  createForm(): FormGroup {
    return this.fb.group({
      cashHolder: [],
      cashNumber: [],
      companyId: [this.auth.companyId,],
      costAmount: [],
      costCenter: [],
      costDescription: [],
      partnerId: [],
      partnerName: [],
      date: [],
      finance: [false,],
      dateFE: [],
      department: [],
      invoiceDate: [],
      invoiceDateFE: [],
      invoiceNumber: [],
      languageId: [this.auth.languageId,],
      lineNumber: [String(this.form.controls.customsCosting.value.length + 1),],
      noOfShipments: [0],
      referenceField1: [],
      referenceField10: [],
      referenceField2: [],
      referenceField3: [],
      referenceField4: [],
      referenceField5: [],
      referenceField6: [],
      referenceField7: [],
      referenceField8: [],
      referenceField9: [],
      remark: [],
      statusId: ['1',],
      supplierName: [],
      createLines: [true,],
    });
  }

  patchForm(data: any) {
    const itemsArray = this.form.get('customsCosting') as FormArray;
    data.forEach((element: any) => {
      itemsArray.push(this.patchCustomsCosting(element));
    });
  }

  patchCustomsCosting(line: any) {
    return this.fb.group({
      cashHolder: [line.cashHolder],
      cashNumber: [line.cashNumber],
      companyId: [line.companyId],
      costAmount: [line.costAmount],
      costCenter: [line.costCenter],
      costDescription: [line.costDescription],
      partnerId: [line.partnerId],
      partnerName: [line.partnerName],
      finance: [line.finance],
      date: [line.date],
      dateFE: [line.date ? this.cs.pCalendar(line.date) : null],
      department: [line.department],
      invoiceDate: [line.invoiceDate],
      invoiceDateFE: [line.invoiceDate != null ? this.cs.pCalendar(line.invoiceDate) : null],
      invoiceNumber: [line.invoiceNumber],
      languageId: [line.languageId],
      lineNumber: [line.lineNumber],
      noOfShipments: [line.noOfShipments],
      remark: [line.remark],
      statusId: [line.statusId],
      supplierName: [line.supplierName],
      referenceField2: [line.referenceField2],
      referenceField3: [line.referenceField3],
      referenceField1: [line.referenceField1],
    });
  }

  fullDate: any;
  today: any;
  data: any;
  pageToken: any;
  activeIndex: number = 1;
  numerRangeResult: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    this.dropdownlist();

    if (this.pageToken.pageflow == 'Edit') {
      this.headerForm.controls.cashNumber.disable();
      this.fill(this.pageToken.line);
    }
    if (this.pageToken.pageflow == 'Display') {
      this.fill(this.pageToken.line);
      this.headerForm.disable();
      this.form.disable();
    } 
    if(this.pageToken.pageflow == 'New') { 
      this.descriptionList = this.descriptionList.filter(x => x.value != 'Food Approvals' || x.value != 'Custom Duty');
      this.activeIndex = 0;
      this.spin.show();
      let obj: any = {};
      obj.numberRangeObject = ['CashNumber'];
      this.numberRangeService.search(obj).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.numerRangeResult = res[0];
            let nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.headerForm.controls.cashNumber.patchValue(nextNumber);
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
    //to pass the breadcrumbs value to the main component
    const dataToSend = [this.pageToken.module, 'Customs Costing'];
    this.path.setData(dataToSend);
  }
  ExpenseTableCalculations: any[] = [];
  fill(line: any) {
    //   setTimeout(() => {
    this.spin.show();
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.costCenter = [this.pageToken.line.Manifest];
    this.service.searchCalculation(obj).subscribe({
      next: (res: any) => {
        if (res) {
          res.forEach((x:any) => x.lineNumber = Number(x.lineNumber));
          let result = res.sort((a:any, b:any) => (a.lineNumber > b.lineNumber) ? 1 : -1);
          this.ExpenseTableCalculations = result;
          this.customCostingDetails.clear();
          this.patchForm(res);
          this.headerForm.patchValue(res[0]);
          let date: any = this.cs.pCalendar(this.headerForm.controls.date.value);
          this.headerForm.controls.dateFE.patchValue(date);

          if (this.pageToken.pageflow == 'Display') {
            this.customCostingDetails.controls.forEach(control => {
              control.disable();
            });
          }
        }
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
    //  }, 500);
  }

  deleteDialog() {
    if (this.selectedExpense.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedExpense, module: 'Expense', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedExpense[0]);
      }
    });
  }
  deleterecord(lines: any) {
    this.spin.show();
    this.service.DeleteLine(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: 'line deleted successfully' });
        this.spin.hide();
        setTimeout(() => {
          this.fill(this.pageToken.line);
        }, 1000);
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  mawbList: any[] = [];
  dropdownlist() {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    this.spin.show();
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
  phawbList: any[] = [];
  costCenterChanged() {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.partnerMasterAirwayBill = [this.headerForm.controls.costCenter.value];
    this.spin.show();
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.phawbList = result;
        this.hawbList = this.cas.foreachlistWithoutKey(result, { key: 'partnerHouseAirwayBill', value: 'partnerHouseAirwayBill' });
        this.headerForm.controls.partnerId.patchValue(result[0].partnerId);
        this.headerForm.controls.partnerName.patchValue(result[0].partnerName);
        this.headerForm.controls.noOfShipments.patchValue(result[0].countHawb);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
  save() {

    if (this.headerForm.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill the required field to continue' });
      return;
    }

    const date = this.cs.jsonDate(this.headerForm.controls.dateFE.value)
    this.headerForm.controls.date.patchValue(date);

    this.customCostingDetails.controls.forEach((group: any) => {
      group.patchValue(this.headerForm.getRawValue());

      if (group.controls.invoiceDateFE.value)
        group.controls.invoiceDate.patchValue(this.cs.jsonDate(group.controls.invoiceDateFE.value))
    });

    if (this.pageToken.pageflow == 'New') {
      this.spin.show();
      this.service.Create(this.form.controls.customsCosting.getRawValue()).subscribe({
        next: (res: any) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Created',
            key: 'br',
            detail: 'Customs Costing has been created successfully',
          });
          this.updateNumberRange();
          setTimeout(() => {
            this.downloadCostingSheet(res[0]);
          }, 2000);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show();
      this.service.UpdateCustomCosting(this.form.controls.customsCosting.getRawValue()).subscribe({
        next: (res: any) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Update',
            key: 'br',
            detail: 'Customs Costing has been updated successfully',
          });
          this.downloadCostingSheet(res[0]);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }

  }

  updateNumberRange() {
    this.spin.show();
    this.numerRangeResult.numberRangeCurrent = Number(this.numerRangeResult.numberRangeCurrent) + 1;
    this.numberRangeService.Update(this.numerRangeResult).subscribe({
      next: (res) => {
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
  selectedFiles: FileList | null = null;
  selectFiles(event: any, index: any): void {
    this.selectedFiles = event.target.files;
    const filesArray = Array.from(event.target.files);

    const control = (this.form.controls.customsCosting as FormArray).at(index);
    const modifiedFiles = filesArray.map((file: any, i) => {
      const newFilename = `${control.get('lineNumber')?.value}_${(file.name)}`;
      return new File([file], newFilename, { type: file.type });
    });
    console.log(modifiedFiles);
    console.log(this.selectedFiles);
    console.log(event.target);
    this.uploadFile(modifiedFiles, index);
  }

  imageDetailsTable: any[] = [];
  fileLocation: any;
  uploadFile(modifiedFiles: any, index: any) {
    if (!this.selectedFiles || this.selectedFiles.length === 0) {
      console.log('No files selected for upload.');
      return;
    }
    const control = (this.form.controls.customsCosting as FormArray).at(index);
    let location = 'customsCosting/' + this.headerForm.controls.costCenter.value;
    this.ConsignmentService.uploadFilesPdfConvert(modifiedFiles, location).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'File uploaded successfully',
        });
        result['referenceField1'] = location;
        control.patchValue(result);
        this.selectedFiles = null;
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }

  clearFileInput(input: HTMLInputElement): void {
    input.value = ''; // Reset the value of the file input field
  }
  
  fileUrldownload: any;
  async download(element:any) {
    if(element.controls.referenceField1.value == null){
      this.messageService.add({
        severity: 'warn',
        summary: 'No Data',
        key: 'br',
        detail: 'No record found',
      });
      return
    }
    this.spin.show()
    let obj: any = {};
    obj.lineNo = element.controls.lineNumber.value;
    obj.path = element.controls.referenceField1.value;
    obj.outputPath = element.controls.referenceField1.value + '.pdf';
    const blob = await this.service.mergeInvoice(obj)
      .catch((err: HttpErrorResponse) => {
        this.cs.commonerrorNew(err);
      });
    this.spin.hide();
    if (blob) {
      const blobOb = new Blob([blob], {
        type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      });
      this.fileUrldownload = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blobOb));
      let docurl = window.URL.createObjectURL(blob);
      const a = document.createElement('a')
      a.href = docurl
      a.download = element.controls.referenceField1.value + '.pdf';
      a.click();
      URL.revokeObjectURL(docurl);

    }
    this.spin.hide();
  }

  downloadCostingSheet(id: any) {
    this.service.costingSheetPdf(id).subscribe({
      next: (res: any) => {
        this.invoice.customsCostingSheet(res);
        setTimeout(() => {
        this.location.back();
      }, 2000);
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  hawbList: any[] = [];
  showhawabDropdown = false;
  selectedRow: any;
  costDescriptionChange(line: any, index: any) {
    const lineraw = line.getRawValue()
    if (lineraw.costDescription != 'Special Approvals') {
      this.showhawabDropdown = false;
      return
    }
    this.selectedRow = index
    this.showhawabDropdown = true;
  }
  toggleDropdown(index: any) {
    this.selectedRow = index;
    this.showhawabDropdown = !this.showhawabDropdown;
  }

  @ViewChildren('row') rows!: QueryList<ElementRef>;
  scrollToLast() {
    if (this.rows.length) {
      this.cdr.detectChanges();
      const lastRow = this.rows.toArray()[this.customCostingDetails.length - 1];
      if (lastRow) {
        lastRow.nativeElement.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }


  callTableHeader() {
    this.cols = [
      { field: 'lineNumber', header: 'Line Number' },
      { field: 'invoiceNumber', header: 'Invoice Number' },
      { field: 'invoiceDateFE', header: 'Invoice Date', format: 'date' },
      { field: 'supplierName', header: 'Supplier Name' },
      { field: 'costDescription', header: 'Cost Type' },
      { field: 'costAmount', header: 'Amount' },
      { field: 'actions', header: 'Actions' },
    ];
  }

  calculateFooterTotal(): number {
    let total = 0;
    this.customCostingDetails.controls.forEach((control) => {
      const group = control as FormGroup;
      const lineNumberControl = group.get('costAmount');

      if (lineNumberControl) {
        const value = lineNumberControl.value;
        if (typeof value === 'number') {
          total += value;
        } else if (!isNaN(Number(value))) {
          total += Number(value);
        }
      }
    });

    return parseFloat(total.toFixed(3));
  }

  back() {
    this.location.back();
  }

}

