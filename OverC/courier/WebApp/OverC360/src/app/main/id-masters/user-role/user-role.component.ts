import { Component, OnInit, ViewChild } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { Router } from '@angular/router';
import { PathNameService } from '../../../common-service/path-name.service';
import { UserRoleService } from './user-role.service';
import { MessageService } from 'primeng/api';
import { MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../../../common-dialog/delete/delete.component';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../../core/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { CustomTableComponent } from '../../../common-dialog/custom-table/custom-table.component';
import { Subscription } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { FormBuilder } from '@angular/forms';
import { OverlayPanel } from 'primeng/overlaypanel';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrl: './user-role.component.scss'
})
export class UserRoleComponent {

  userRoleTable: any[] = [];
  selectedUserRole: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: UserRoleService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private fb: FormBuilder,
    private auth: AuthService,
    private spin: NgxSpinnerService,
  ) { }

  fullDate: any;
  today: any;
  ngOnInit(): void {
    const dataToSend = ['Setup', 'User Role'];
    this.path.setData(dataToSend);

    this.callTableHeader();
    this.initialCall();
  }

  callTableHeader() {
    this.cols = [
      { field: 'companyIdAndDescription', header: 'Company' },
      { field: 'roleId', header: 'Role ID',format:'hyperLink' },
      { field: 'userRoleName', header: 'User Role' },
      { field: 'menuName', header: 'Menu' },
      { field: 'subMenuName', header: 'Sub Menu' },
      { field: 'description', header: 'Role Description' },
      { field: 'authorizationObjectValue', header: 'Authorization Object Value' }
    ];
    this.target = [
      { field: 'languageId', header: 'Language ID' },
      { field: 'languageIdAndDescription', header: 'Language' },
      { field: 'companyId', header: 'Company ID' },
      { field: 'userRoleId', header: 'UserRole ID' },
      { field: 'menuId', header: 'Menu ID' },
      { field: 'subMenuId', header: 'Sub Menu ID' },
      { field: 'moduleId', header: 'Module ID' },
      { field: 'statusId', header: 'Status ID' },
      { field: 'authorizationObjectId', header: 'Authorization Object ID' },
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
          res = this.cs.removeDuplicatesFromArrayList(res, 'roleId')
          this.userRoleTable = res;
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
    const choosen = this.selectedUserRole[this.selectedUserRole.length - 1];
    this.selectedUserRole.length = 0;
    this.selectedUserRole.push(choosen);
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
        this.deleterecord(this.selectedUserRole[0]);
      }
    });
  }

  openCrud(type: any = 'New', linedata: any = null): void {
    if(linedata){
      this.selectedUserRole = linedata;
    }
    if (this.selectedUserRole.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return
    } 
    let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedUserRole[0] : linedata, pageflow: type });
    this.router.navigate(['/main/idMaster/userrole-new/' + paramdata]);
  }

  deleteDialog() {
    if (this.selectedUserRole.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '60%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { line: this.selectedUserRole, module: 'User Role', body: 'This action cannot be undone. All values associated with this field will be lost.' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedUserRole[0]);
      }
    })
  }

  deleterecord(lines: any) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: 'success', summary: 'Deleted', key: 'br', detail: lines.roleId + lines.companyId + lines.languageId + ' deleted successfully' });
        this.spin.hide();
        this.initialCall();
      }, error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    })
  }

  downloadExcel() {
    const exportData = this.userRoleTable.map(item => {
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
    this.cs.exportAsExcel(exportData, 'UserRole');
  }

  searchform = this.fb.group({
    menuId: [],
    subMenuId: [],
    roleId: [],
    statusId: [],
    companyId: [[this.auth.companyId],],
    languageId: [[this.auth.languageId],]
  })

  readonly fieldDisplayNames: Record<string, string> = {
    menuId: 'Menu',
    subMenuId: 'Sub Menu',
    roleId: 'Role ID',
    statusId: 'Status'
  };

  languageDropdown: any = [];
  companyDropdown: any = [];
  menuDropdown: any = [];
  subMenuDropdown: any = [];
  roleDropdown: any = [];
  statusDropdown: any = [];

  getSearchDropdown() {

    this.userRoleTable.forEach(res => {

      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, 'value');
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, 'value');
      }
      if (res.menuId != null) {
        this.menuDropdown.push({ value: res.menuId, label: res.menuName });
        this.menuDropdown = this.cs.removeDuplicatesFromArrayList(this.menuDropdown, 'value');
      }
      if (res.subMenuId != null) {
        this.subMenuDropdown.push({ value: res.subMenuId, label: res.subMenuName });
        this.subMenuDropdown = this.cs.removeDuplicatesFromArrayList(this.subMenuDropdown, 'value');
      }
      if (res.roleId != null) {
        this.roleDropdown.push({ value: res.roleId, label: res.roleId });
        this.roleDropdown = this.cs.removeDuplicatesFromArrayList(this.roleDropdown, 'value');
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, 'value');
      }
    })
    //  this.statusDropdown = [{ value: '17', label: 'Inactive' }, { value: '16', label: 'Active' }];
  }

  @ViewChild('userRole') overlayPanel!: OverlayPanel;
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
        this.userRoleTable = res;
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
      menuId: [],
      subMenuId: [],
      roleId: [],
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
