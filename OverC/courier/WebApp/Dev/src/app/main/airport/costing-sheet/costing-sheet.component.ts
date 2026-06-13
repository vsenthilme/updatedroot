import { Component, ViewChild } from '@angular/core';
import { CostingSheetService } from './costing-sheet.service';
import { DatePipe } from '@angular/common';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { AuthService } from '../../../core/core';
import { HttpErrorResponse } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { ConsignmentService } from '../../operation/consignment/consignment.service';
import { CostingSheetBulkupdateComponent } from './costing-sheet-bulkupdate/costing-sheet-bulkupdate.component';

@Component({
  selector: 'app-costing-sheet',
  templateUrl: './costing-sheet.component.html',
  styleUrl: './costing-sheet.component.scss'
})
export class CostingSheetComponent {

  expenseTable: any[] = [];
  selectedCostingSheet: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: CostingSheetService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
    private sanitizer: DomSanitizer,
    private ConsignmentService: ConsignmentService
  ) { }

  fullDate: any;
  today: any;
  activeLink: any;
  ngOnInit() {
    const link = this.router.url;
    this.activeLink = link.split('/')[2];

    if (this.activeLink == 'airport') {
      //to pass the breadcrumbs value to the main component
      const dataToSend = ['Mid-Mile', 'Customs Costing'];
      this.path.setData(dataToSend);
    }
    else {
      //to pass the breadcrumbs value to the main component
      const dataToSend = ['Finance', 'Customs Costing'];
      this.path.setData(dataToSend);
    }

    this.initialCall();
    //this.callTableHeader();
  }

  callTableHeader() {
    this.cols = [
      { field: 'costCenter', header: 'Manifest', format: 'hyperLink' },
      { field: 'partnerName', header: 'Customer' },
      { field: 'costDescription', header: 'Global' },
      { field: 'costDescription', header: 'Approval' },
      { field: 'createdBy', header: 'Customs' },
      { field: 'createdBy', header: 'Stamps' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'total', header: 'Total' },
      { field: 'createdOn', header: 'Created On', format: 'date' },


    ];
    this.target = [
    ];
  }

  initialCall() {
    setTimeout(() => {
      this.spin.show();
      this.service.search(this.searchform.getRawValue()).subscribe({
        next: (res: any) => {
          if (this.activeLink == 'finance') {
            var result = res.filter((x: any) => x.finance == true);
            this.transformData(result);
          } else {
            this.transformData(res);
          }
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
    const choosen = this.selectedCostingSheet[this.selectedCostingSheet.length - 1];
    this.selectedCostingSheet.length = 0;
    this.selectedCostingSheet.push(choosen);
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if (linedata) {
      this.selectedCostingSheet = linedata;
    }
    if (this.selectedCostingSheet.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    else {
      if (type != 'New') {
        if ((linedata.statusId == '3' || linedata.statusId == '5') && (this.activeLink == 'airport')) {
          let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: 'Display', module: this.activeLink == 'finance' ? 'Finance' : 'Mid-Mile' });
          this.router.navigate(['/main/airport/costingSheet-new/' + paramdata]);
          return;
        }
        else {
          let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: type, module: this.activeLink == 'finance' ? 'Finance' : 'Mid-Mile' });
          this.router.navigate(['/main/airport/costingSheet-new/' + paramdata]);
          return;
        }
      } else {
        let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: type, module: this.activeLink == 'finance' ? 'Finance' : 'Mid-Mile' });
        this.router.navigate(['/main/airport/costingSheet-new/' + paramdata]);
        return;
      }

    }
  }

  deleteDialog() {
    if (this.selectedCostingSheet.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedCostingSheet, module: 'Customs  Costing', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedCostingSheet[0]);
      }
    });
  }
  deleterecord(lines: any) {
    this.spin.show();
    const costCenter = this.selectedCostingSheet.map(item => item.costCenter);
    this.service.searchCalculation({ costCenter: costCenter }).subscribe({
      next: (result) => {
        this.service.Delete(result).subscribe({
          next: (res) => {
            this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: lines.costCenter + ' deleted successfully' });
            this.spin.hide();
            this.initialCall();
          }, error: (err) => {
            this.cs.commonerrorNew(err);
            this.spin.hide();
          }
        })
      }
    })

  }
  sendToFinance() {
    const costCenterResult = this.selectedCostingSheet.map(item => item.costCenter);
    this.spin.show();
    this.service.searchCalculation({ costCenter: costCenterResult }).subscribe({
      next: (res: any) => {
        res.forEach((x: any) => {
          x.finance = true;
          x.statusId = 3;
          x.statusDescription = 'Send to finance';
        });
        this.service.UpdateCustomCosting(res).subscribe({
          next: (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Updated',
              key: 'br',
              detail: 'Selected Values has been updated successfully',
            });
            this.router.navigate(['/main/airport/costingSheet']);
            this.spin.hide();
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })

  }
  downloadExcel() {
    const exportData = this.expenseTable.map(item => {
      const exportItem: any = {};

      // Collect keys and ensure order
      const keys = Object.keys(item).filter(key => key !== 'Created By' && key !== 'Created On');
      keys.push('Created By', 'Created On'); // Ensure 'createdBy' and 'createdOn' are last

      // Map the data fields to the exportItem
      keys.forEach(key => {
        if (key === 'Created On') {
          // Format the date field
          exportItem[key] = this.datePipe.transform(item[key], 'dd-MM-yyyy');
        } else {
          // Copy other fields directly
          exportItem[key] = item[key];
        }
      });

      return exportItem;
    });
    this.cs.exportAsExcel(exportData, 'Customs Costing');
  }

  columns: string[] = [];
  rowsArray: any[] = [];

  transformData(result: any) {
    console.log(result)
    const tempColumns = new Set<string>();
    const tempRows: { [key: string]: { [key: string]: string } } = {};

    result.forEach((item: any) => {
      tempColumns.add(item.costDescription);

      if (!tempRows[item.costCenter]) {
        tempRows[item.costCenter] = {};
      }
      tempRows[item.costCenter][item.costDescription] = item.costAmount;

      tempRows[item.costCenter]['Customer'] = item.partnerName;
      tempRows[item.costCenter]['Manifest'] = item.costCenter;
      tempRows[item.costCenter]['costCenter'] = item.costCenter;
      tempRows[item.costCenter]['partnerId'] = item.partnerId;
      tempRows[item.costCenter]['companyId'] = item.companyId;
      tempRows[item.costCenter]['languageId'] = item.languageId;
      tempRows[item.costCenter]['Status'] = item.statusDescription;
      tempRows[item.costCenter]['Created By'] = item.createdBy;
      tempRows[item.costCenter]['Created On'] = item.createdOn;
      tempRows[item.costCenter]['cashNumber'] = item.cashNumber;
      tempRows[item.costCenter]['finance'] = item.finance;
      tempRows[item.costCenter]['statusId'] = item.statusId;
      tempRows[item.costCenter]['Total'] = item.totalCostAmount;
      tempRows[item.costCenter]['referenceField1'] = item.referenceField1;
    });

    this.columns = Array.from(tempColumns);
    this.columns.unshift('Customer');
    this.columns.unshift('Manifest');
    this.columns.unshift('Status');
    this.columns.push('Total');
    this.columns.push('Created By', 'Created On');

    this.expenseTable = Object.keys(tempRows).map(key => {
      return { costCenter: key, ...tempRows[key] };
    });
  }


  searchform = this.fb.group({
    companyId: [[this.auth.companyId]],
    costCenter: [],
    cashNumber: [],
    partnerId: [],
    languageId: [[this.auth.languageId]],
    statusId: [],
  })

  readonly fieldDisplayNames: Record<string, string> = {
    costCenter: 'Cost Center',
    cashNumber: 'Cash Number',
    partnerId: 'Partner ID',
  };

  partnerIdDropdown: any = [];
  cashNumberDropdown: any = [];
  costCenterDropdown: any = [];



  getSearchDropdown() {

    this.expenseTable.forEach(res => {

      if (res.partnerId != null) {
        this.partnerIdDropdown.push({ value: res.partnerId, label: res.partnerId });
        this.partnerIdDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerIdDropdown, 'value');
      }
      if (res.costCenter != null) {
        this.costCenterDropdown.push({ value: res.costCenter, label: res.costCenter });
        this.costCenterDropdown = this.cs.removeDuplicatesFromArrayList(this.costCenterDropdown, 'value');
      }
      if (res.cashNumber != null) {
        this.cashNumberDropdown.push({ value: res.cashNumber, label: res.cashNumber });
        this.cashNumberDropdown = this.cs.removeDuplicatesFromArrayList(this.cashNumberDropdown, 'value');
      }
    })
  }

  @ViewChild('costingSheet') overlayPanel!: OverlayPanel;
  closeOverLay() {
    this.overlayPanel.hide();
  }

  fieldsWithValue: any
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues)
      .filter(key => formValues[key as keyof typeof formValues] !== null && formValues[key as keyof typeof formValues] !== undefined).map(key => this.fieldDisplayNames[key] || key);

    this.spin.show();
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res: any) => {
        if (this.activeLink == 'finance') {
          var result = res.filter((x: any) => x.finance == true);
          this.transformData(result);
        } else {
          this.transformData(res);
        }
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
    // this.searchform = this.fb.group({
    //   languageId: [[this.auth.languageId],]
    // })
    this.search();
  }

  chipClear(value: any) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find(key => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.searchform.get(formControlKey)?.reset();
      this.search();
    }
  }

  fileUrldownload: any;
  docurl: any;

  async downloadMerge(element: any) {
    console.log(element)
    this.spin.show()
    let obj: any = {};
    obj.path = 'customsCosting/' + element.costCenter;
    obj.outputPath = 'customsCosting/' +  element.costCenter + '.pdf';
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
      a.download = 'customsCosting/' + element.costCenter + '.pdf';
      a.click();
      URL.revokeObjectURL(docurl);

    }
    this.spin.hide();
  }


  selectedFiles: File | null = null;
  onFileSelected(event: any, line: any): void {
    const file: File = event.target.files[0];
    this.selectedFiles = file;

    const newFileName = line.referenceField1;
    const newFile = new File([file], newFileName, { type: file.type });
    let location = 'customsCosting/' + line.costCenter + '/';
    this.ConsignmentService.uploadPDFConvert(newFile, location).subscribe({
      next: (result: any) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'File uploaded successfully',
        });
        this.selectedFiles = null;
        this.clearFileInput(event.target);
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  clearFileInput(input: HTMLInputElement): void {
    input.value = ''; // Reset the value of the file input field
  }
  updateBulk() {
    const dialogRef = this.dialog.open(CostingSheetBulkupdateComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { title: 'Custom Calculations', code: this.selectedCostingSheet },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.initialCall();
    });
  }
  sendToOperations() {
    if (this.selectedCostingSheet.length == 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        key: 'br',
        detail: 'Kindly select any row',
      });
    }
    else {
      const dialogRef = this.dialog.open(CostingSheetBulkupdateComponent, {
        disableClose: true,
        width: '70%',
        maxWidth: '80%',
        position: { top: '6.5%', left: '30%' },
        data: { title: 'Send To Operation', code: this.selectedCostingSheet },
      });

      dialogRef.afterClosed().subscribe((result) => {
        this.initialCall();
      });
    }
  }

  approve(data: any = null, type: any = null) {
    if (type == 'single') {
      let obj: any = {};
      obj.companyId = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.partnerMasterAirWayBill = [data.costCenter];
      this.spin.show();
      this.service.approveCostSheet(obj).subscribe({
        next: (res: any) => {
          this.messageService.add({ severity: 'success', summary: 'Approved', key: 'br', detail: 'Records approved successfully' });
          this.initialCall();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
    if (type == 'Bulk') {

      if (this.selectedCostingSheet.length === 0) {
        this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
        return
      }

      let obj: any = {};
      obj.companyId = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.partnerMasterAirWayBill = this.selectedCostingSheet.map(item => item.costCenter);
      this.spin.show();
      this.service.approveCostSheet(obj).subscribe({
        next: (res: any) => {
          this.messageService.add({ severity: 'success', summary: 'Approved', key: 'br', detail: 'Records approved successfully' });
          this.initialCall();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }
}