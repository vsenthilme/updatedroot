import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { PickingService } from '../picking.service';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';

@Component({
  selector: 'app-picking-dialog',
  templateUrl: './picking-dialog.component.html',
  styleUrls: ['./picking-dialog.component.scss']
})
export class PickingDialogComponent implements OnInit {

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
    private service:  PickingService) { }
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
     console.log(this.data)
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
          //this.deleterecord(result);
    
        }
      });
    }

//      deleterecord(result) {
   
//     let obj: any = {};
//    obj.companyCodeId = this.auth.companyId;
//    obj.plantId = this.auth.plantId;
//   obj.languageId = this.auth.languageId;
//   obj.warehouseId = this.auth.warehouseId;
//   obj.priceListId=this.selectedpricelist[0].priceListId;
//   obj.serviceTypeId=this.selectedpricelist[0].serviceTypeId;
//   obj.chargeRangeId=this.selectedpricelist[0].chargeRangeId;
//   obj.moduleId=this.selectedpricelist[0].moduleId;
//   this.spin.show();
//   this.sub.add(this.service.DeleteList([obj]).subscribe((res) => {
//     this.toastr.success(this.selectedpricelist[0].priceListId + " Deleted successfully.","Notification",{
//       timeOut: 2000,
//       progressBar: false,
//     });
//     this.spin.hide();
//     this.getAll();
//   }, err => {
//     this.cs.commonerrorNew(err);
//     this.spin.hide();
//   }));
// }
 
  
  getAll() {
   
      this.adminUser()
    
  }
  
  openConfirm(data: any) {

    if(data.statusId != 48){
      this.toastr.error("Picking Process of this Particular Order is Completed", "Norification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    let paramdata = this.cs.encrypt({ code: data, pageflow: 'Edit' });
    this.router.navigate(['/main/outbound/pickup-confirm/' + paramdata]);
    this.dialogRef.close();
  }
  adminUser(){
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
  obj.languageId = [this.auth.languageId];
   obj.warehouseId = [this.auth.warehouseId];
   obj.pickupNumber=[this.data.code.pickupNumber];
   obj.statusId= [48],
    this.spin.show();
    this.sub.add(this.service.searchv2(obj).subscribe((res: any[]) => {

      
if(res){
 
   this.pricelist = res;

}
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  getToQtyQty() {
    let total = 0;
    this.pricelist.forEach(x => {
      total = total + (x.pickToQty != null ? x.pickToQty : 0)
    })
    return Math.round(total * 100 / 100)
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
   
  
  
  




