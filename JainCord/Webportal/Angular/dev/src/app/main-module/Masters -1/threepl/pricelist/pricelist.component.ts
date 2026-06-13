import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Router } from '@angular/router';
import { PricelistNewComponent } from './pricelist-new/pricelist-new.component';
import { PricelistService } from './pricelist.service';
import { PricelistListComponent } from './pricelist-list/pricelist-list.component';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.scss']
})
export class PricelistComponent implements OnInit {

 
  screenid=3201;
  advanceFilterShow: boolean;
  @ViewChild('Setuppricelist') Setuppricelist: Table | undefined;
  pricelist: any[] = [];
  selectedpricelist : any[] = [];
  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
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
    private service: PricelistService ) { }
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
   
    applyFilterGlobal($event: any, stringVal: any) {
      this.Setuppricelist!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
    }
    RA: any = {};
    ngOnInit(): void {
      this.RA = this.auth.getRoleAccess(this.screenid);
      this.getAll();
    }
    openprice(data): void {
      console.log(data);
      const dialogRef = this.dialog.open(PricelistListComponent, {
        disableClose: true,
        width: '90%',
        maxWidth: '90%',
        height:'70%',
        data: data
      });
    
      dialogRef.afterClosed().subscribe(result => {
        this.getAll();
      });
    }
  
    /** Whether the number of selected elements matches the total number of rows. */
   
  openDialog(data: any = 'New'): void {
    if (data != 'New'){
      if (this.selectedpricelist.length == 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    }
    let paramdata = "";
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selectedpricelist[0].priceListId : null,warehouseId: data != 'New' ? this.selectedpricelist[0].warehouseId : null,languageId: data != 'New' ? this.selectedpricelist[0].languageId : null, plantId: data != 'New' ? this.selectedpricelist[0].plantId : null,companyCodeId: data != 'New' ? this.selectedpricelist[0].companyCodeId : null,moduleId: data != 'New' ? this.selectedpricelist[0].moduleId : null,chargeRangeId: data!= 'New' ? this.selectedpricelist[0].chargeRangeId : null,serviceTypeId: data!= 'New' ? this.selectedpricelist[0].serviceTypeId : null,});
    this.router.navigate(['/main/threePLmaster/pricelistNew/' + paramdata]);
  }
  
  getAll() {
   
      this.adminUser()
    
  }
  
  adminUser(){
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
  obj.languageId = [this.auth.languageId];
   obj.warehouseId = this.auth.warehouseId;
    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any[]) => {

      
if(res){
  res = this.cs.removeDuplicatesFromArrayList(res , 'priceListId')
   this.pricelist = res;

}
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  deleteDialog() {
    if (this.pricelist.length === 0) {
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
      data: this.selectedpricelist[0].code,
    });
  
    dialogRef.afterClosed().subscribe(result => {
  
      if (result) {
        this.deleterecord(result);
  
      }
    });
  }
  
  
  deleterecord(result) {
   
    let obj: any = {};
   obj.companyCodeId = this.auth.companyId;
   obj.plantId = this.auth.plantId;
  obj.languageId = this.auth.languageId;
  obj.warehouseId = this.auth.warehouseId;
  obj.priceListId=this.selectedpricelist[0].priceListId;
  this.spin.show();
  this.sub.add(this.service.DeleteList([obj]).subscribe((res) => {
    this.toastr.success(this.selectedpricelist[0].priceListId + " Deleted successfully.","Notification",{
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
    this.pricelist.forEach(x => {
      res.push({
        "Language":x.languageId,
       "Company":x.companyCodeId,
        "Plant":x.plantId,
        "Warehouse":x.warehouseId,
         "Module":x.moduleIdAndDescription,
         "Price List":x.priceListId,
         "Service Type ":x.serviceTypeIdAndDescription,
         "Charge Unit":x.chargeUnit,
        "Created By":x.createdBy,
       "Created One":this.cs.dateapi(x.createdOn),
      });
  
    })
    this.cs.exportAsExcel(res, "Price List");
  }
  onChange() {
    const choosen= this.selectedpricelist[this.selectedpricelist.length - 1];   
    this.selectedpricelist.length = 0;
    this.selectedpricelist.push(choosen);
  } 
  }
   
  
  
  




