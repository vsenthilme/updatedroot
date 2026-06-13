import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService, dropdownelement } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BomNewComponent } from '../bom/bom-new/bom-new.component';
import { BOMElement, BOMService } from '../bom/bom.service';
import { MasterrecipeTableComponent } from '../masterrecipe/masterrecipe-table/masterrecipe-table.component';
import { RecipeChildComponent } from './recipe-new/recipe-child/recipe-child.component';

@Component({
  selector: 'app-receipe',
  templateUrl: './receipe.component.html',
  styleUrls: ['./receipe.component.scss']
})
export class ReceipeComponent implements OnInit {
  screenid = 3227;
  displayedColumns: string[] = ['select', 'parentItemCode','childItemCode', 'childItemQuantity','referenceField2','statusId', 'createdBy', 'createdOn', ];
  sub = new Subscription();
  advanceFilterShow: boolean;
@ViewChild('Setupcurrency') Setupcurrency: Table | undefined;
currrencys: any;
selectedcurrency : any;
  ELEMENT_DATA: BOMElement[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor(public dialog: MatDialog,
    private service: BOMService,
   // private cas: CommonApiService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
   // private excel: ExcelService,
   private router: Router,
    private fb: FormBuilder,
    private auth: AuthService) { }
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
  RA: any = {};
  ngOnInit(): void {
   // this.auth.isuserdata();

    this.getAll();
   //this.search(true);
   this.RA = this.auth.getRoleAccess(this.screenid);
  }

  dataSource = new MatTableDataSource<BOMElement>(this.ELEMENT_DATA);
  selection = new SelectionModel<BOMElement>(true, []);




  deleteDialog() {
    if (this.selectedcurrency.length === 0) {
      this.toastr.error("Kindly select any row", "",{
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: this.selectedcurrency[0],
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result) {
        this.deleterecord(this.selectedcurrency[0]);

      }
    });
  }
  deleterecord(id: any) {

    this.spin.show();

    this.sub.add(this.service.DeleteMasterreceipe(id.receipeId,id.warehouseId,id.plantId,id.companyCodeId,id.languageId,id.operationNumber).subscribe((res) => {
      
      console.log(id);
      ;
      this.toastr.success(id.receipeId + " Deleted successfully.","",{
        timeOut: 2000,
        progressBar: false,
      });

      this.spin.hide(); //this.getAll();
      window.location.reload();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  openDialog(data: any = 'New'): void {
    if (data != 'New'){
      if (this.selectedcurrency.length == 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    }
    let paramdata = "";
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selectedcurrency[0].receipeId : null,warehouseId: data != 'New' ? this.selectedcurrency[0].warehouseId : null,languageId: data != 'New' ? this.selectedcurrency[0].languageId : null, plantId: data != 'New' ? this.selectedcurrency[0].plantId : null,companyCodeId: data != 'New' ? this.selectedcurrency[0].companyCodeId : null});
    this.router.navigate(['/main/other-masters/masterReceipeNew/' + paramdata]);
  }
  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }

  }
  openprice(data:any ='Display'): void {
    console.log(data);
    const dialogRef = this.dialog.open(RecipeChildComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '90%',
      height:'60%',
      data:{code: data,pageflow:'Display'}
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.getAll();
    
    });
  }

 // warehouseId : any;
  warehouseId = this.auth.warehouseId;
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator; // Pagination
 // Pagination.

 
 searhform = this.fb.group({
  createdBy: [],
  endCreatedOn: [],
  endUpdatedOn: [],
  parentItemCode: [],
  startCreatedOn: [],
  startUpdatedOn: [],
  statusId: [],
  updatedBy: [],
  warehouseId: [[this.auth.warehouseId]],

});

openDialog2(element){
  const dialogRef = this.dialog.open(MasterrecipeTableComponent, {
    disableClose: true,
    width: '55%',
    maxWidth: '80%',
  
   data: element
  });

  dialogRef.afterClosed().subscribe(result => {
  //  this.getAll();
  });
}


dropdownSettings: IDropdownSettings = {
  idField: 'item_id',
  textField: 'item_text',
  selectAllText: 'Select All',
  unSelectAllText: 'UnSelect All',
  itemsShowLimit: 3,
  allowSearchFilter: true
};

// itemCodeListselected: any[] = [];
// itemCodeList: dropdownelement[] = [];

parentItemCodeListselected: any[] = [];
parentItemCodeList: dropdownelement[] = [];

partnerCodeListselected: any[] = [];
partnerCodeList: dropdownelement[] = [];

// preOutboundNoselected: any[] = [];
// preOutboundNoList: dropdownelement[] = [];


statusIdListselected: any[] = [];
statusIdList: dropdownelement[] = [];

search(ispageload = false) {
  if (!ispageload) {

    //dateconvertion
    this.searhform.controls.endCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endCreatedOn.value));
    this.searhform.controls.startCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startCreatedOn.value));
  
  
    //patching
    // const itemCode = [...new Set(this.itemCodeListselected.map(item => item.item_id))].filter(x => x != null);
    // this.searhform.controls.itemCode.patchValue(itemCode);
  
    const parentItemCode = [...new Set(this.parentItemCodeListselected.map(item => item.item_id))].filter(x => x != null);
    this.searhform.controls.parentItemCode.patchValue(parentItemCode);
  
    // const partnerCode = [...new Set(this.partnerCodeListselected.map(item => item.item_id))].filter(x => x != null);
    // this.searhform.controls.partnerCode.patchValue(partnerCode);
  
    // const preOutboundNo = [...new Set(this.preOutboundNoselected.map(item => item.item_id))].filter(x => x != null);
    // this.searhform.controls.preOutboundNo.patchValue(preOutboundNo);
  
  
  
    const statusId = [...new Set(this.statusIdListselected.map(item => item.item_id))].filter(x => x != null);
    this.searhform.controls.statusId.patchValue(statusId);
  }
 this.service.searchMasterReceipe(this.searhform.value).subscribe(res => {
   // let result = res.filter((x: any) => x.warehouseId == this.warehouseId);
   this.spin.hide();
   
   if (ispageload) {
    //  let tempitemCodeList: dropdownelement[] = []
    //  const itemCode = [...new Set(res.map(item => item.itemCode))].filter(x => x != null)
    //  itemCode.forEach(x => tempitemCodeList.push({ item_id: x, item_text: x }));
    //  this.itemCodeList = tempitemCodeList;

     let tempparentItemCodeList: dropdownelement[] = []
     const parentItemCode = [...new Set(res.map(item => item.parentItemCode))].filter(x => x != null)
     parentItemCode.forEach(x => tempparentItemCodeList.push({ item_id: x, item_text: x }));
     this.parentItemCodeList = tempparentItemCodeList;

    //  let temppartnerCodeList: dropdownelement[] = []
    //  const partnerCode = [...new Set(res.map(item => item.partnerCode))].filter(x => x != null)
    //  partnerCode.forEach(x => temppartnerCodeList.push({ item_id: x, item_text: x }));
    //  this.partnerCodeList = temppartnerCodeList;

    //  let temppreOutboundNoList: dropdownelement[] = []
    //  const preOutboundNo = [...new Set(res.map(item => item.preOutboundNo))].filter(x => x != null)
    //  preOutboundNo.forEach(x => temppreOutboundNoList.push({ item_id: x, item_text: x }));
    //  this.preOutboundNoList = temppreOutboundNoList;


     let tempstatusIdList: dropdownelement[] = []
     const statusId = [...new Set(res.map(item => item.statusId))].filter(x => x != null)
     statusId.forEach(x => tempstatusIdList.push({ item_id: x, item_text: x }));
     this.statusIdList = tempstatusIdList;
   }
   let result = res.filter((x: any) =>   x.deletionIndicator == 0);

   let finalData : any[] = [];

   result.forEach(bomheaderdata => {
    bomheaderdata.bomLines.forEach(bomlinedata => {
      bomlinedata['parentItemCode'] = bomheaderdata.parentItemCode;
      bomlinedata['createdBy'] = bomheaderdata.createdBy;
      bomlinedata['createdOn'] = bomheaderdata.createdOn;
      bomlinedata['statusId'] = bomheaderdata.statusId;
      finalData.push(bomlinedata)
    });
   });

   this.dataSource = new MatTableDataSource<any>(finalData);
   this.selection = new SelectionModel<BOMElement>(true, []);
   this.dataSource.sort = this.sort;
  this.dataSource.paginator = this.paginator;
 }, err => {

   this.cs.commonerrorNew(err);
   this.spin.hide();

 });   


}
reload(){
 window.location.reload();
}


getAll() {
    this.adminUser()
  
}

adminUser(){
let obj: any = {};
  obj.companyCodeId = [this.auth.companyId];
   obj.plantId = [this.auth.plantId];
   obj.languageId = [this.auth.languageId];
  obj.warehouseId = [this.auth.warehouseId];
  this.spin.show();
  this.sub.add(this.service.searchMasterReceipe(obj).subscribe((res: any[]) => {
    
if(res){
  res = this.cs.removeDuplicatesFromArrayList(res , 'receipeId')
 this.currrencys = res;

}
    this.spin.hide();
  }, err => {
    this.cs.commonerrorNew(err);
   this.spin.hide();
  }));
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
  checkboxLabel(row?: BOMElement): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.bomNumber + 1}`;
  }






  clearselection(row: any) {

    this.selection.clear();
    this.selection.toggle(row);
  }

    downloadexcel() {
    // if (excel)
    var res: any = [];
    this.currrencys.forEach(x => {
      res.push({
        "LanguageId":x.languageId,
        "Company":x.companyCode,
       "Plant":x.plantId,
        "Warehouse":x.warehouseId,
        "Recipe No": x.receipeId,
        " Recipe  Name  ": x.remarks,
        "Operation Number": x.operationNumber,
        'Bom No': x.bomNumber,
        'Status': this.cs.getstatus_text(x.statusId),
        'Created By': x.createdby,
        "Created On": this.cs.dateapi(x.createdOn)
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Master Recipe");
  }
  onChange() {
    console.log(this.selectedcurrency.length)
    const choosen= this.selectedcurrency[this.selectedcurrency.length - 1];   
    this.selectedcurrency.length = 0;
    this.selectedcurrency.push(choosen);
  } 
}
