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
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService, dropdownelement } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ContainerReceiptService } from '../../Container-receipt/container-receipt.service';
import { PreinboundNewComponent } from '../preinbound-new/preinbound-new.component';
import { PreinboundUploadComponent } from '../preinbound-upload/preinbound-upload.component';
import { PreinboundService } from '../preinbound.service';
import { Table } from 'primeng/table';

import { CancelSupplierInvoiceComponent } from '../cancel-supplier-invoice/cancel-supplier-invoice.component';
import { CustomdialogComponent } from 'src/app/common-field/customdialog/customdialog.component';
import { RequestpopupComponent } from 'src/app/common-field/requestpopup/requestpopup.component';
import { PreinboundPopupeditComponent } from '../preinbound-main/preinbound-popupedit/preinbound-popupedit.component';

@Component({
  selector: 'app-failedorder',
  templateUrl: './failedorder.component.html',
  styleUrls: ['./failedorder.component.scss']
})
export class FailedorderComponent implements OnInit {
  screenid=3044;
  Update(arg0: any, code: any) {
    throw new Error('Method not implemented.');
  }
  Create(arg0: any) {
    throw new Error('Method not implemented.');
  }
  displayedColumns: string[] = ['select', 'statusId', 'referenceDocumentType', 'refDocNumber', 'containerNo', 'preInboundNo', 'createdBy', 'refDocDate',];
  constructor(private service: PreinboundService,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();


  preInbound: any[] = [];
  selectedPreinbound : any[] = [];
  @ViewChild('preInboundTag') preInboundTag: Table | any;
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search();
    //this.searchfilter(true);
  }
 

  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
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




  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.preInbound.forEach(x => {
      res.push({
        "Branch":x.branchCode,
        "Warehouse":x.warehouseID,
        "Status  ": x.processedStatusId,
        "Order Type ": x.refDocumentType,
        "Order No ": x.refDocumentNo,
        "Transfer Order No": x.transferOrderNumber,
        'Purchase Order No': x.purchaseOrderNumber,
        'Order Date':this.cs.dateapiwithTime(x.orderReceivedOn),
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Inbound Failed Orders");
  }

  deleteDialog() {
    if (this.selectedPreinbound.length === 0) {
      this.toastr.error("Kindly select any row", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    if (this.selectedPreinbound[0].statusId != 6) {
      this.toastr.error("Order can't delete.", "Notification", {
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
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result) {
        this.deleterecord(this.selectedPreinbound[0]);

      }
    });
  }
  deleterecord(id: any) {
    this.spin.show();
    this.sub.add(this.service.Delete(id.preInboundNo, id.warehouseId).subscribe((res) => {
      this.toastr.success(id.preInboundNo + " Deleted successfully.", "", {
        timeOut: 2000,
        progressBar: false,
      });

      this.spin.hide(); //this.getAllListData();
      this.search();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  openDialogHeader(data: any = 'New'): void {
    console.log(this.selectedPreinbound)
      if (data != 'New')
      if (this.selectedPreinbound.length === 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
      const dialogRef = this.dialog.open(PreinboundPopupeditComponent, {
        disableClose: true,
        width: '55%',
        maxWidth: '80%',
        data: { pageflow: data, code: data != 'New' ? this.selectedPreinbound[0].preInboundNo : null,warehouseId: data != 'New' ? this.selectedPreinbound[0].warehouseId : null,companyCode: data != 'New' ? this.selectedPreinbound[0].companyCode : null,plantId: data != 'New' ? this.selectedPreinbound[0].plantId : null,languageId: data != 'New' ? this.selectedPreinbound[0].languageId : null}
      });
    
      dialogRef.afterClosed().subscribe(result => {
        this.search();
      });
   }
  openDialog(data: any = 'new',type ?: any): void {
    if(type && type != undefined){
      this.selectedPreinbound = [];
      this.selectedPreinbound.push(type);
    }
    if (data != 'New')
      if (this.selectedPreinbound.length === 0) {
        this.toastr.error("Kindly select any row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }


    if (this.selectedPreinbound[0].statusId === 24) {
      this.toastr.error("Order can't be proccessed as it's already confirmed.", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    let paramdata = "";

    if (this.selectedPreinbound.length > 0) {
      paramdata = this.cs.encrypt({ code: this.selectedPreinbound[0], pageflow: data });
      this.router.navigate(['/main/inbound/preinbound-create/' + paramdata]);
    }
    else {
      paramdata = this.cs.encrypt({ pageflow: data });
      this.router.navigate(['/main/inbound/preinbound-create/' + paramdata]);
    }

  }

  openDialogView(data) {
    let obj: any = {};
    obj.languageId=[this.auth.languageId];
    obj.companyCode=[this.auth.companyId];
   obj.branchCode=[this.auth.plantId];
    obj.warehouseID=[this.auth.warehouseId];
   obj.refDocumentNo=[data.refDocNumber];
   obj.refDocumentType=[data.referenceDocumentType];
    this.spin.show();
    this.sub.add(this.service.searchInboundHeader(obj).subscribe((res: any[]) => {
      const lineReferences = res[0]?.line?.map(line => line.lineReference) || []; 
      const maxLineRef = lineReferences.length; 
      console.log(maxLineRef);
    const dialogRef2 = this.dialog.open(RequestpopupComponent, {
      width: '45%',
      maxWidth: '45%',
      position: {
        top: '1%',
      },
      data: { title: "Order Details", body: "Order Details", value: res,lines:maxLineRef,},
    });
    
    dialogRef2.afterClosed().subscribe(result => {
      // Handle the result if needed
    });
  }))
   this.spin.hide();
  }
  


  filtersearch() {
    this.spin.show();
    this.sub.add(this.service.search(this.searhform.getRawValue()).subscribe((res: any[]) => {
      this.spin.hide();
      this.preInbound = res;
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));

  }
  warehouseId = this.auth.warehouseId
  list:any[]=[];
 







  searhform = this.fb.group({
    containerNo: [],
    endConfirmedOn: [],
    endCreatedOn: [],
    inboundOrderTypeId: [],
    refDocNumber: [],
    startConfirmedOn: [],
    startCreatedOn: [],
    statusId: [],
    processedStatusId:[],
    warehouseId: [[this.auth.warehouseId]],
  });

  dropdownSettings: IDropdownSettings = {
    idField: 'item_id',
    textField: 'item_text',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };


  containerNoListselected: any[] = [];
  containerNoList: dropdownelement[] = [];

  inboundOrderTypeListselected: any[] = [];
  inboundOrderTypeList: dropdownelement[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: dropdownelement[] = [];

  statusIdListselected: any[] = [];
  statusIdList: dropdownelement[] = [];


  search(ispageload = false) {
    if (!ispageload) {

      //dateconvertion
      this.searhform.controls.endRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endRequiredDeliveryDate.value));
      this.searhform.controls.startRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startRequiredDeliveryDate.value));
      this.searhform.controls.endOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endOrderDate.value));
      this.searhform.controls.startOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startOrderDate.value));



    }
    let obj: any = {};
    //obj.companyCode = [this.auth.companyId];
   // obj.languageId = [this.auth.languageId];
    //obj.branchCode = [this.auth.plantId];
   // obj.warehouseId = [this.auth.warehouseId];
    obj.processedStatusId=[100,900];
    this.spin.show();
    this.service.searchInboundHeader(obj).subscribe(res => {
      //  let result = res.filter((x: any) => x.statusId == 39);
      let result = res



      if (ispageload) {

        let tempoutboundOrderTypeIdList: any[] = []
       // const outboundOrderTypeId = [...new Set(result.map(item => item.outboundOrderTypeId))].filter(x => x != null)
        res.forEach(x => tempoutboundOrderTypeIdList.push({
          value: x.outboundOrderTypeId,
          label:x.referenceDocumentType,
        }));
        this.inboundOrderTypeList = tempoutboundOrderTypeIdList;
        this.inboundOrderTypeList=this.cs.removeDuplicatesFromArrayNew(this.inboundOrderTypeList);
 


        //  let temprefDocNumberList: any[] = []
        //  const refDocNumber = [...new Set(result.map(item => item.refDocNumber))].filter(x => x != null)
        //  refDocNumber.forEach(x => temprefDocNumberList.push({ id: x, itemName: x }));
        //  this.refDocNumberList = temprefDocNumberList;


        let tempstatusIdList: any[] = []
        // const statusId = [...new Set(result.map(item => item.statusId))].filter(x => x != null)
        res.forEach(x => tempstatusIdList.push({
          value: x.processedStatusId,
          label: x.processedStatusId
        }));
        this.statusIdList = tempstatusIdList;
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList);


      }
      this.preInboundTag = result;

      this.spin.hide();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    });


  }
  reload() {
    window.location.reload();
  }
  showSuccess() {
    this.toastr.success('ASN Processed successfully',);
  }
  preInboundUpload(){
  //   const dialogRef = this.dialog.open(PreinboundUploadComponent, {
  //     disableClose: true,
  //     width: '80%',
  //     maxWidth: '80%',
  //     position: { top: '9%', },
  //   });
    
  //   dialogRef.afterClosed().subscribe(result => {
  // })
  this.router.navigate(['/main/inbound/preInboundCreate'])
  
  }


  onChange() {
    const choosen= this.selectedPreinbound[this.selectedPreinbound.length - 1];   
    this.selectedPreinbound.length = 0;
    this.selectedPreinbound.push(choosen);
  }


  cancel(data: any): void {
    const dialogRef = this.dialog.open(CustomdialogComponent, {
      // width: '60%', height: '70%',
      width: '50%',
      maxWidth: '80%',
      position: {
        top: '6.7%',
      },

      data: { title: "Confirm", body: "Do you want to replace the Order No?" },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'Yes') {
      const dialogRef = this.dialog.open(CancelSupplierInvoiceComponent, {
        disableClose: true,
        width: '55%',
        maxWidth: '80%',
        data: { code: data,}
      });
    }
    else{
      dialogRef.afterClosed().subscribe(result => {
        this.search();
      });
    }
    }
    )
  }
}
