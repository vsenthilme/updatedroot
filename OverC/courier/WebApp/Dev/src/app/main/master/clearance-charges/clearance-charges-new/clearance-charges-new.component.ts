import { Component } from '@angular/core';
import { ClearanceChargesService } from '../clearance-charges.service';
import { ClearancePopupComponent } from '../clearance-popup/clearance-popup.component';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { CustomerValueComponent } from '../../customer/customer-value/customer-value.component';

@Component({
  selector: 'app-clearance-charges-new',
  templateUrl: './clearance-charges-new.component.html',
  styleUrl: './clearance-charges-new.component.scss'
})
export class ClearanceChargesNewComponent {

  active: number | undefined = 0;
  status: any[] = []

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ClearanceChargesService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  pageToken: any;

  // form builder initialize
  form = this.fb.group({
    languageId: [this.auth.languageId],
    companyId: [this.auth.companyId],
    addMinCharge: [],
    partnerId: [],
    partnerName: [],
    clearanceCharges: [],
    currency: [],
    deletionIndicator: [],
    formulaField1: [],
    formulaField10: [],
    formulaField2: [],
    formulaField3: [],
    formulaField4: [],
    formulaField5: [],
    formulaField6: [],
    formulaField7: [],
    formulaField8: [],
    formulaField9: [],
    multiplier: [],
    noOfShipmentsFrom: [],
    noOfShipmentsTo: [],
    parameter: [],
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
    statusId: ['16',],
    validityDateFrom: [],
    validityDateTo: [],
  });

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = 'required') {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Master', 'Clearance Charges', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.partnerId.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  customerList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.customer.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.customerList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.customer.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  clearanceTable: any[] = [];

  add() {
    if(!this.form.controls.partnerId.value){
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Kindly select customer to continue'});
      return
    }
    const dialogRef = this.dialog.open(ClearancePopupComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.clearanceTable.length + 1,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clearanceTable.push(result);
        console.log(this.clearanceTable)
      }
    });
  }

  removeItem(index: number) {
    this.clearanceTable.splice(index, 1);
  }

  fill(line: any) {
    this.form.patchValue(line);
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.partnerId = [line.partnerId];

    this.service.search(obj).subscribe({
      next: (res: any) => {
        this.clearanceTable = res;
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  save() {

    this.submitted = true;
    if (this.clearanceTable.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show();
      this.clearanceTable.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.partnerId = this.form.controls.partnerId.value;
        x.partnerName = this.form.controls.partnerName.value;
        x.statusId = this.form.controls.statusId.value;
      });
      this.service.UpdateBulk(this.clearanceTable).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].partnerId + ' has been updated successfully',
          });
          this.router.navigate(['/main/master/clearanceCharges']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.clearanceTable.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.partnerId = this.form.controls.partnerId.value;
        x.partnerName = this.form.controls.partnerName.value;
        x.statusId = this.form.controls.statusId.value;
      });
      this.service.CreateBulk(this.clearanceTable).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res[0].partnerId + ' has been created successfully',
            });
            this.router.navigate(['/main/master/clearanceCharges']);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  editItem(data: any, i: any): void {
    const dialogRef = this.dialog.open(ClearancePopupComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { pageflow: data, code: this.clearanceTable[i] },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clearanceTable.splice(i, 0);
        this.clearanceTable.splice(i, 1, result);
        this.clearanceTable = [...this.clearanceTable]

      }
    });
  }

  customerChange(){
    const list:any = this.customerList.filter(x => x.value == this.form.controls.partnerId.value);
    this.form.controls.partnerName.patchValue(list[0].label);
  }

}





