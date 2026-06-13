import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { Table } from 'primeng/table';
import { catchError } from 'rxjs/operators';
import { Subscription, forkJoin, of } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService, dropdownelement} from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BasicdataService } from './basicdata.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';

@Component({
  selector: 'app-basicdata',
  templateUrl: './basicdata.component.html',
  styleUrls: ['./basicdata.component.scss']
})
export class BasicdataComponent implements OnInit {

  screenid=3020;
  advanceFilterShow: boolean;
  @ViewChild('setupitemtype') setupitemtype: Table | undefined;
  itemtype: any[] = [];
  selecteditemtype : any[] = [];
  sub = new Subscription();
  isShowDiv = false;
 
  public icon = 'expand_more';

  ELEMENT_DATA: any[] = [];
  
  constructor(public dialog: MatDialog,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private router: Router,
    public cs: CommonService,
   // private excel: ExcelService,
    private fb: FormBuilder,
    private auth: AuthService,
    private reportService: ReportsService,
    private service: BasicdataService ) { }
    showFloatingButtons: any;
    toggle = true;
    storecodeList: any;
    toggleFloat() {
      this.isShowDiv = !this.isShowDiv;
      this.toggle = !this.toggle;
  
      if (this.icon === 'expand_more') {
        this.icon = 'chevron_left';
      } else {
        this.icon = 'expand_more'
      }
      this.showFloatingButtons = !this.showFloatingButtons;
      console.log('show:' + this.showFloatingButtons);
    }
    showFiller = false;
    animal: string | undefined;
   
    applyFilterGlobal($event: any, stringVal: any) {
      this.setupitemtype!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
    }
    RA: any = {};
    ngOnInit(): void {
      this.RA = this.auth.getRoleAccess(this.screenid);
      this.getAll();
      //this.getDropdown();
    }
    multiselectItemCodeList: any[] = [];
    itemCodeList: any[] = [];
    onItemType(searchKey) {
      let searchVal = searchKey?.filter;
      if (searchVal !== '' && searchVal !== null) {
        forkJoin({
          itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
        })
          .subscribe(({ itemList }) => {
            if (itemList != null && itemList.length > 0) {
              this.multiselectItemCodeList = [];
              this.itemCodeList = itemList;
              this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description}))
            }
          });
      }
    }
    warehouseId = this.auth.warehouseId;
    /** Whether the number of selected elements matches the total number of rows. */
    searhform = this.fb.group({
      companyCodeId: [[this.auth.companyId]],
     createdBy: [],
     description: [],
      endCreatedOn: [],
     endUpdatedOn: [],
     itemCode: [],
  itemGroup: [],
  itemCodeFE:[],
  itemType: [],
  languageId: [[this.auth.languageId]],
  manufacturerPartNo: [],
  plantId: [[this.auth.plantId]],
  startCreatedOn: [],
  startUpdatedOn: [],
  subItemGroup: [],
  updatedBy: [],
  warehouseId: [[this.auth.warehouseId]],
    
    });
  openDialog(data: any = 'New',type?:any): void {
    this.selecteditemtype.push(type);
    if (data != 'New'){
      if (this.selecteditemtype.length == 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    }
    let paramdata = "";
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selecteditemtype[0].itemCode : null,warehouseId: data != 'New' ? this.selecteditemtype[0].warehouseId : null,languageId: data != 'New' ? this.selecteditemtype[0].languageId : null, plantId: data != 'New' ? this.selecteditemtype[0].plantId : null,companyCodeId: data != 'New' ? this.selecteditemtype[0].companyCodeId : null,uomId: data != 'New' ? this.selecteditemtype[0].uomId : null,itemCode: data != 'New' ? this.selecteditemtype[0].itemCode : null,manufacturerPartNo: data != 'New' ? this.selecteditemtype[0].manufacturerPartNo : null});
    this.router.navigate(['/main/masternew/basicdataNew/' + paramdata]);
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
  
  itemtypeListselected: any[] = [];
  itemtypeList: dropdownelement[] = [];
  
  itemgroupListselected: any[] = [];
  itemgroupList: dropdownelement[] = [];
  
  descriptionselected: any[] = [];
  descriptionList: dropdownelement[] = [];
  
  basicdataList:any[]=[];
  mfrList:any[]=[];
  itemcodeselected: any[] = [];
  itemocodeList: dropdownelement[] = [];
 
  search(ispageload = false) {
    if (!ispageload) {
  
      //dateconvertion
      this.searhform.controls.endCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endCreatedOn.value));
      this.searhform.controls.startCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startCreatedOn.value));
    
    }
    if(this.searhform.controls.itemCodeFE.value==null){
    this.searhform.controls.itemCode.patchValue(null);
    }
    else{
    this.searhform.controls.itemCode.patchValue([this.searhform.controls.itemCodeFE.value]);
    }
   this.service.search(this.searhform.value).subscribe(res => {
     this.spin.hide();
   
     this.itemtype = res;
    
   }, err => {
  
     this.cs.commonerrorNew(err);
     this.spin.hide();
  
   });   

  
  }
  getAll() {
   
      this.adminUser()
    
  }
  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
  this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
  this.searhform.controls.plantId.patchValue([this.auth.plantId]);
  this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    }
  adminUser(){
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    this.spin.show();
    this.sub.add(this.service.searchSpark(obj).subscribe((res: any[]) => {
      console.log(res)
if(res){
   this.itemtype = res;

}
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  deleteDialog() {
    if (this.selecteditemtype.length === 0) {
      this.toastr.error("Kindly select any row", "Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: this.selecteditemtype[0].code,
    });
  
    dialogRef.afterClosed().subscribe(result => {
  
      if (result) {
        this.deleterecord(this.selecteditemtype[0].warehouseId,this.selecteditemtype[0].languageId,this.selecteditemtype[0].plantId,this.selecteditemtype[0].uomId,this.selecteditemtype[0].companyCodeId,this.selecteditemtype[0].itemCode,this.selecteditemtype[0].manufacturerPartNo);
  
      }
    });
  }
  
  
  deleterecord(warehouseId:any,languageId:any,plantId:any,uomId:any,companyCodeId:any,itemCode:any,manufacturerPartNo:any) {
    this.spin.show();
    this.sub.add(this.service.Delete(warehouseId,languageId,plantId,uomId,companyCodeId,itemCode,manufacturerPartNo).subscribe((res) => {
      this.toastr.success(itemCode+ " Deleted successfully.","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      this.getAll();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  downloadexcel() {
    var res: any = [];
    this.itemtype.forEach(x => {
      res.push({
        "Company ":x.companyDescription,
        "Branch":x.plantDescription,
        "Warehouse":x.warehouseDescription,
        "Mfr Name":x.manufacturerPartNo,
      "Part No ":x.itemCode,
      "Description":x.description,
        "Base UOM":x.uomId,
        "Created By":x.createdBy,
       "Created On":this.cs.dateapi(x.createdOn),
      });
  
    })
    this.cs.exportAsExcel(res, "Basic Data 1");
  }
  onChange() {
    const choosen= this.selecteditemtype[this.selecteditemtype.length - 1];   
    this.selecteditemtype.length = 0;
    this.selecteditemtype.push(choosen);
  } 
  }
   
  
  
  



