import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { catchError } from 'rxjs/operators';
import { Subscription, forkJoin, of } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PartnerService } from './partner.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';

@Component({
  selector: 'app-partner',
  templateUrl: './partner.component.html',
  styleUrls: ['./partner.component.scss']
})
export class PartnerComponent implements OnInit {

  screenid=3025;
  advanceFilterShow: boolean;
  @ViewChild('setupitemtype') setupitemtype: Table | undefined;
  itemtype: any[] = [];
  selecteditemtype : any[] = [];
  sub = new Subscription();
  isShowDiv = false;
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
  public icon = 'expand_more';

  ELEMENT_DATA: any[] = [];
  
  constructor(public dialog: MatDialog,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private router: Router,
    public cs: CommonService,
   // private excel: ExcelService,
    private fb: FormBuilder,
    private reportService: ReportsService,
    private auth: AuthService,
    private service:  PartnerService) { }
    
    showFiller = false;
    animal: string | undefined;
   
    applyFilterGlobal($event: any, stringVal: any) {
      this.setupitemtype!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
    }
    RA: any = {};
    ngOnInit(): void {
      this.RA = this.auth.getRoleAccess(this.screenid);
      this.getAll();
      this.getDropdown();
    }
    businessPartnerTypeList:any[]=[];
    statusIdList:any[]=[];
    barcodeList:any[]=[];
    warehouseId = this.auth.warehouseId;
    /** Whether the number of selected elements matches the total number of rows. */
    getDropdown() {
      this.sub.add(this.service.search({ warehouseId: [this.auth.warehouseId],companyCodeId:[this.auth.companyId],plantId:[this.auth.plantId],languageId:[this.auth.languageId] }).subscribe(res => {
        res.forEach((x: any) => this.businessPartnerTypeList.push({ value: x.businessPartnerCode, label:x.businessPartnerCode }));
        res.forEach((x: any) => this.statusIdList.push({ value: x.statusId, label: (x.statusId== 1?'Active':'Inactive')}));
        res.forEach((x: any) => this.barcodeList.push({ value: x.partnerItemBarcode, label: x.partnerItemBarcode}));
        
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList);
        this.businessPartnerTypeList=this.cs.removeDuplicatesFromArrayNewstatus(this.businessPartnerTypeList);
        this.barcodeList=this.cs.removeDuplicatesFromArrayNewstatus(this.barcodeList);
      }))
      
    }
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
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selecteditemtype[0].itemCode : null,warehouseId: data != 'New' ? this.selecteditemtype[0].warehouseId : null,plantId: data != 'New' ? this.selecteditemtype[0].plantId : null,languageId: data != 'New' ? this.selecteditemtype[0].languageId : null,companyCodeId: data != 'New' ? this.selecteditemtype[0].companyCodeId : null,businessPartnerType: data != 'New' ? this.selecteditemtype[0].businessPartnerType : null,partnerItemBarcode: data != 'New' ? this.selecteditemtype[0].partnerItemBarcode : null});
    this.router.navigate(['/main/masternew/partnerNew/' + paramdata]);
  }

  /** Whether the number of selected elements matches the total number of rows. */
  searhform = this.fb.group({
    businessPartnerCode: [],
    businessPartnerType: [],
    companyCodeId: [[this.auth.companyId]],
    itemCode: [],
    itemCodeFE:[],
    languageId: [[this.auth.languageId]],
    manufacturerName: [],
    partnerItemBarcode: [],
    plantId: [[this.auth.plantId]],
    warehouseId: [[this.auth.warehouseId]],
  
  });
  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
  this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
  this.searhform.controls.plantId.patchValue([this.auth.plantId]);
  this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    }
  getAll() {
   
      this.adminUser()
    
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
  search(ispageload = false) {
    if (!ispageload) {
  
     
    
    }
    if(this.searhform.controls.itemCodeFE.value==null){
    this.searhform.controls.itemCode.patchValue(null);
    }
    else{
    this.searhform.controls.itemCode.patchValue([this.searhform.controls.itemCodeFE.value]);
    }
     //dateconvertion
     this.searhform.controls.endCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endCreatedOn.value));
     this.searhform.controls.startCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startCreatedOn.value));
   this.service.search(this.searhform.value).subscribe(res => {
     this.spin.hide();
   
     this.itemtype = res;
    
   }, err => {
  
     this.cs.commonerrorNew(err);
     this.spin.hide();
  
   });   

  
  }
  adminUser(){
    let obj: any = {};
     obj.companyCodeId = [this.auth.companyId];
     obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any[]) => {
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
        this.deleterecord(this.selecteditemtype[0].itemCode,this.selecteditemtype[0].warehouseId,this.selecteditemtype[0].plantId,this.selecteditemtype[0].languageId,this.selecteditemtype[0].companyCodeId,this.selecteditemtype[0].manufacturerName,this.selecteditemtype[0].partnerItemBarcode);
  
      }
    });
  }
  
  
  deleterecord(id: any,warehouseId:any,plantId:any,languageId:any,companyCodeId:any, manufacturerName: any,partnerItemBarcode:any) {
    this.spin.show();
    this.sub.add(this.service.Delete(id,warehouseId,plantId,languageId,companyCodeId, manufacturerName,partnerItemBarcode).subscribe((res) => {
      this.toastr.success(id + " Deleted successfully.","Notification",{
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
      "Language":x.languageId,
      "Company ":x.companyCodeId,
      "Branch":x.plantId,
      "Warehouse":x.warehouseId,
        "Partner":x.businessPartnerCode,
        "Mfr Name":x.manufacturerName,
        "Part No":x.itemCode,
        "Barcode":x.partnerItemBarcode,
        "Mfr Barcode":x.mfrBarcode,
        "Created By":x.createdBy,
       "Created On":this.cs.dateapi(x.createdOn),
      });
  
    })
    this.cs.exportAsExcel(res, "Partner");
  }
  onChange() {
    const choosen= this.selecteditemtype[this.selecteditemtype.length - 1];   
    this.selecteditemtype.length = 0;
    this.selecteditemtype.push(choosen);
  } 
  }
   
  
  
  


