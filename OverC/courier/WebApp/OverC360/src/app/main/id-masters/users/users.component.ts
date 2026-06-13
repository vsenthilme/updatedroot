import { Component, ViewChild } from '@angular/core';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { Router } from '@angular/router';
import { PathNameService } from '../../../common-service/path-name.service';
import { UsersService } from './users.service';
import { MessageService } from 'primeng/api';
import { MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  userTable: any[] = [];
  selectedUser: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: UsersService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private fb: FormBuilder,
    private auth: AuthService,
    private spin: NgxSpinnerService,
  ) { }

  fullDate: any;
  today: any;
  ngOnInit(): void {
    const dataToSend = ['Setup', 'Users'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyIdAndDescription', header: 'Company' },
      { field: 'userRoleId', header: 'User Role' ,format:'hyperLink'},
      { field: 'userName', header: 'User Name' },
      { field: 'emailId', header: 'Email Id' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: 'userTypeIdAndDescription', header: 'User Type' },
      { field: 'languageId', header: 'Language ID' },
      { field: 'languageIdAndDescription', header: 'Language' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'userId', header: 'User ID' },
      { field: 'userRoleId', header: 'User Role ID' },
      { field: 'userTypeId', header: 'User Type ID' },
      { field: 'password', header: 'Password' },
      { field: 'firstName', header: 'First Name' },
      { field: 'lastName', header: 'Last Name' },
      { field: 'timeZone', header: 'Time Zone' },
      { field: 'dateFormatId', header: 'Date Format ID' },
      { field: 'currencyDecimal', header: 'Currency Decimal' },
      { field: 'portalLoggedIn', header: 'Portal Logged In' },
      { field: 'hhtLoggedIn', header: 'HHT Logged In' },
      { field: 'resetPassword', header: 'Reset Password' },
      { field: 'referenceField1', header: 'Reference Field 1' },
      { field: 'referenceField2', header: 'Reference Field 2' },
      { field: 'referenceField3', header: 'Reference Field 3' },
      { field: 'referenceField4', header: 'Reference Field 4' },
      { field: 'referenceField5', header: 'Reference Field 5' },
      { field: 'referenceField6', header: 'Reference Field 6' },
      { field: 'referenceField7', header: 'Reference Field 7' },
      { field: 'referenceField8', header: 'Reference Field 8' },
      { field: 'referenceField9', header: 'Reference Field 9' },
      { field: 'referenceField10', header: 'Reference Field 10' },
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
          this.userTable = res;
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
    const choosen = this.selectedUser[this.selectedUser.length - 1];
    this.selectedUser.length = 0;
    this.selectedUser.push(choosen);
  }

  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '6.5%', left: '30%' },
      data: { target: this.cols, source: this.target },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedUser[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedUser = linedata;
    }    
    if (this.selectedUser.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedUser[0] : linedata, pageflow: type });
      this.router.navigate(['/main/idMaster/users-new/' + paramdata]);
    }
  }

  deleteDialog() {
    if (this.selectedUser.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedUser, module: 'Users', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedUser[0]);
      }
    })
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: lines.userId + lines.companyId + lines.languageId + ' deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.userTable.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'data') {
          console.log(3)
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    // Excel service
    this.cs.exportAsExcel(exportData, 'Users');
  }

  searchform = this.fb.group({
    userId: [],
    userRoleId: [],
    userTypeId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    userId: 'User',
    userRoleId: 'User Role',
    userTypeId: 'User Type',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  userDropdown: any = [];
  userRoleDropdown: any = [];
  userTypeDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.userTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.userId != null) {
        this.userDropdown.push({ value: res.userId, label: res.userName });
        this.userDropdown = this.cs.removeDuplicatesFromArrayList(this.userDropdown, 'value');
      }
      if (res.userRoleId != null) {
        this.userRoleDropdown.push({ value: res.userRoleId, label: res.userRoleIdAndDescription });
        this.userRoleDropdown = this.cs.removeDuplicatesFromArrayList(this.userRoleDropdown, 'value');
      }
      if (res.userTypeId != null) {
        this.userTypeDropdown.push({ value: res.userTypeId, label: res.userTypeIdAndDescription });
        this.userTypeDropdown = this.cs.removeDuplicatesFromArrayList(this.userTypeDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('user') overlayPanel!: OverlayPanel;
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
        this.userTable = res;
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
      userId: [],
      userRoleId: [],
      userTypeId: [],
      statusId: [],
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
