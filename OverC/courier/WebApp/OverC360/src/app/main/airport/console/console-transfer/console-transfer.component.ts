import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ConsoleService } from '../console.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-console-transfer',
  templateUrl: './console-transfer.component.html',
  styleUrl: './console-transfer.component.scss'
})
export class ConsoleTransferComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ConsoleTransferComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsoleService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private location: Location
  ) {

  }

  form = this.fb.group({
    fromConsoleId: [],
    houseAirwayBill: [],
    toConsoleId: [],
  });


  ngOnInit(): void {
    this.getConsoleDropdown();
    this.selecetedTrasnfer = this.data;
    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
    }
  }
  consoleList: any[] = [];
  getConsoleDropdown() {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    this.spin.show();
    this.service.search(obj).subscribe({
      next: (result) => {
        this.consoleList = this.cas.forLanguageFilterWithoutKey(result, { key: 'consoleId', value: 'consoleId', languageId: 'languageId', companyId: 'companyId' });
        this.consoleList = this.cs.removeDuplicatesFromArrayList(this.consoleList, 'value')
        this.consoleList = this.consoleList.filter(item => item.value !== this.data[0].consoleId);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })

  }
  selecetedTrasnfer: any[] = [];
  selecetedTrasnfer2: any[] = [];
  fromConsoleId: any
  save() {
    this.spin.show();
    for (let i = 0; i < this.selecetedTrasnfer.length; i++) {
      this.selecetedTrasnfer[i].fromConsoleId = this.selecetedTrasnfer[i].consoleId;
      this.selecetedTrasnfer[i].houseAirwayBill = this.selecetedTrasnfer[i].houseAirwayBill;
      this.selecetedTrasnfer[i].toConsoleId = this.form.controls.toConsoleId.value;
    }
    this.service.Transfer(this.selecetedTrasnfer).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'Selcted ' + res[0].partnerHouseAirwayBill + ' has been Transfered successfully',
        });
        //this.router.navigate(['/main/airport/console']);
        this.dialogRef.close();
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  back() {
    this.location.back();
  }

}

