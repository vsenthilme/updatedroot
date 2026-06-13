import { SelectionModel } from '@angular/cdk/collections';
import { Component,Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/dialog_modules/delete/delete.component';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { ExcelService } from 'src/app/common-service/excel.service';
import { AuthService } from 'src/app/core/core';
import { ClientNewComponent } from '../../../master/client/client-new/client-new.component';
import { EntityNewComponent } from '../entity-new/entity-new.component';
import { EntityService } from '../entity.service';

@Component({
  selector: 'app-entity-popup',
  templateUrl: './entity-popup.component.html',
  styleUrls: ['./entity-popup.component.scss']
})
export class EntityPopupComponent implements OnInit {
  screenid = 1212;
  displayedColumns: string[] = ['clientId','clientName', 'entityId','entityName', 'statusId', ];
  public icon = 'expand_more';
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  constructor(public dialog: MatDialog,
    private service: EntityService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private cs: CommonService,
    private cas: CommonApiService,
    private excel: ExcelService,
    private fb: FormBuilder,
    private auth: AuthService) {}
  toggleFloat() {

    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;

  }
  showFiller = false;
  animal: string | undefined;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  code:any;
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    let obj: any = {};
    obj.statusId= [0];
    obj.entityId=[this.data.entityId];
    this.sub.add(this.service.search(obj).subscribe(res => {

    
      this.spin.hide();
      this.dataSource = new MatTableDataSource<any>(res);

      this.selection = new SelectionModel<any>(true, []);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }))

  }
  dataSource = new MatTableDataSource < any > (this.ELEMENT_DATA);
  selection = new SelectionModel < any > (true, []);
 
   /** Selects all rows if they are not all selected; otherwise clear selection. */
   toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

 
  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }

  }

  @ViewChild(MatSort, {
    static: true
  })
  sort: MatSort;
  @ViewChild(MatPaginator, {
    static: true
  })
  paginator: MatPaginator; // Pagination


  //   dropdownSettings = {
  //   singleSelection: false,
  //   text:"Select",
  //   selectAllText:'Select All',
  //   unSelectAllText:'UnSelect All',
  //   enableSearchFilter: true,
  //   badgeShowLimit: 2,
  //   disabled: false
  // };
  multilanguageList: any[] = [];
  multiyseridList: any[] = [];
  multiselectyseridList: any[] = [];
  dropdownSelectLanguageID: any[] = [];
  dropdownlist() {

    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.cgsetup.language.url,
    ]).subscribe((results) => {
      this.multilanguageList = this.cas.foreachlist(results[0], this.cas.dropdownlist.cgsetup.language.key);
      this.multilanguageList.forEach((x: {
        key: string;value: string;
      }) => this.dropdownSelectLanguageID.push({
        value: x.key,
        label: x.value
      }))
      this.spin.hide();
      console.log(this.dropdownSelectLanguageID);
    }, (err) => {
      this.toastr.error(err, "");
      this.spin.hide();
    });
  }
  multicompanyList: any[] = [];
  multicountryList: any[] = [];
  multientityList:any[]=[];
 
  downloadexcel() {
    var res: any = [];
    this.dataSource.data.forEach(x => {
      res.push({
        // "Language ID": x.languageId,
        // "Company Name": x.companyIdAndDescription,
        "Co-Owner ID": x.clientId,
        "Co-Owner Name": x.clientName,
        "Entity Id":x.entityId,
        "Entity Name":x.entityName,
        "Status": (x.statusId) == 0 ? 'Active' : 'Inactive',
        'Created By': x.createdBy,
        'Created On': this.cs.excel_date(x.createdOn)
      });

    })
    this.excel.exportAsExcel(res, "Entity");
  }
 
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row ? : any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.adminCost + 1}`;
  }


  searhform = this.fb.group({
    languageId: [],
    companyId: [],
    clientId: [],
    entityId:[],
    createdBy: [],
    createdOn_to: [],
    endCreatedOn: [],
    startCreatedOn: [],
    endCreatedOnFE: [],
    startCreatedOnFE: [],
    createdOn_from: [],
    statusId: [
      [0],
    ]
  });

  search() {
    this.spin.show();
    this.searhform.controls.startCreatedOn.patchValue(
      this.cs.dateNewFormat1(this.searhform.controls.startCreatedOnFE.value)
    );
    this.searhform.controls.endCreatedOn.patchValue(
      this.cs.dateNewFormat1(this.searhform.controls.endCreatedOnFE.value)
    );
    this.sub.add(this.service.search(this.searhform.getRawValue()).subscribe((res: any[]) => {
      console.log(res)
     
      this.dataSource = new MatTableDataSource < any > (res);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  Cancel() {
    this.reset();
    this.dataSource = new MatTableDataSource < any > (this.ELEMENT_DATA);
    this.selection = new SelectionModel < any > (true, []);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  clearselection(row: any) {
    if (!this.selection.isSelected(row)) {
      this.selection.clear();
    }
    this.selection.toggle(row);
  }
  reset() {
    this.searhform.reset();
    this.searhform.controls.statusId.patchValue([0]);
  }
}

