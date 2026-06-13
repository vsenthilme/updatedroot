import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PricelistService } from '../../pricelist/pricelist.service';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';

@Component({
  selector: 'app-pricelistassigementlist',
  templateUrl: './pricelistassigementlist.component.html',
  styleUrls: ['./pricelistassigementlist.component.scss']
})
export class PricelistassigementlistComponent implements OnInit {

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
  
  constructor( public dialogRef: MatDialogRef < any > ,
    public dialog: MatDialog,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    @Inject(MAT_DIALOG_DATA) public data: any,
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
     ;
      this.getAll();
    }
   
    openDialog(data: any = 'New',type): void {
    
      if(type && type != undefined){
        this.selectedpricelist = [];
        this.selectedpricelist.push(type);
      }

      if (data != 'New'){
        if (this.selectedpricelist.length == 0) {
          this.toastr.warning("Kindly select any Row", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
          return;
        }
      }
      this.dialogRef.close();
      let paramdata = "";
      paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selectedpricelist[0].priceListId : null,warehouseId: data != 'New' ? this.selectedpricelist[0].warehouseId : null,languageId: data != 'New' ? this.selectedpricelist[0].languageId : null, plantId: data != 'New' ? this.selectedpricelist[0].plantId : null,companyCodeId: data != 'New' ? this.selectedpricelist[0].companyCodeId : null,moduleId: data != 'New' ? this.selectedpricelist[0].moduleId : null,chargeRangeId: data!= 'New' ? this.selectedpricelist[0].chargeRangeId : null,serviceTypeId: data!= 'New' ? this.selectedpricelist[0].serviceTypeId : null,});
      this.router.navigate(['/main/threePLmaster/pricelistNew/' + paramdata]);
     
    };
     
   
 
  
  getAll() {
   
      this.adminUser()
    
  }
  
  adminUser(){
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
  obj.languageId = [this.auth.languageId];
   obj.warehouseId = this.auth.warehouseId;
   obj.priceListId=[this.data.priceListId];
   
    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any[]) => {

      
if(res){
 
   this.pricelist = res;

}
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  delete(result) {
    console.log(result
    )
    this.selectedpricelist[0]=result;
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: result,
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
  obj.serviceTypeId=this.selectedpricelist[0].serviceTypeId;
  obj.chargeRangeId=this.selectedpricelist[0].chargeRangeId;
  obj.moduleId=this.selectedpricelist[0].moduleId;
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
   
  
  
  




