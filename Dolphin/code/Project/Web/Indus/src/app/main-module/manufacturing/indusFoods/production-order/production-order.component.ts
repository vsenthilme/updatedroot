import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BOMElement, BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { ProductionService } from '../production.service';
import { MasterrecipeTableComponent } from 'src/app/main-module/Masters -1/other-masters/masterrecipe/masterrecipe-table/masterrecipe-table.component';
import { RecipeChildComponent } from 'src/app/main-module/Masters -1/other-masters/receipe/recipe-new/recipe-child/recipe-child.component';
import { format } from 'path';


@Component({
  selector: 'app-production-order',
  templateUrl: './production-order.component.html',
  styleUrls: ['./production-order.component.scss']
})
export class ProductionOrderComponent implements OnInit {

  screenid = 3228;
  advanceFilterShow: boolean;
  @ViewChild('Setupcurrency') Setupcurrency: Table | undefined;
  sub = new Subscription();

  currrencys: any;
  ELEMENT_DATA: BOMElement[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  constructor(public dialog: MatDialog,
    private service: ProductionService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
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
  RA: any = {};

  ngOnInit(): void {
    this.getAll();
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.callTableHeader()
  }


  cols: any[] = [];
  cols2: any[] = [];
  bomDetails: any[] = [];
  operationDetails: any[] = [];

  callTableHeader() {
    this.cols = [
      { field: 'companyDescription', header: 'Company' },
      { field: 'productionOrderNo', header: 'Production Order No', format: 'extra' },
      { field: 'itemDescription', header: 'Item' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'batchQuantity', header: 'Batch Qty' },
      { field: 'receipePercentage', header: 'Recipe %' },
      { field: 'bomNumber', header: 'BOM',format:'extra' },
      { field: 'operationNumber', header: 'Operation' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Created By' },
      { field: 'createdOn', header: 'Created On', format: 'date' },
      { field: 'actions', header: 'Actions', format: 'extra' },
    ];
  }

  deleteDialog() {
    if (this.selectedProduction.length === 0) {
      this.toastr.error("Kindly select any row", "", {
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
      data: this.selectedProduction[0],
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleterecord(this.selectedProduction[0]);
      }
    });
  }
  
  deleterecord(id: any) {
    this.spin.show();
    this.sub.add(this.service.Delete(id.productionOrderNo, id.warehouseId, id.plantId, id.companyCodeId, id.languageId).subscribe((res) => {
      this.toastr.success(id.parentItemCode + " Deleted successfully.", "", {
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
  openprice(data:any ='Display'): void {
    console.log(data);
    const dialogRef = this.dialog.open(MasterrecipeTableComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '90%',
      height:'60%',
      data:{code: data,pageflow:'Edit'}
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.getAll();
    
    });
  }
  openDialog(data: any = 'New'): void {
    if (data != 'New') {
      if (this.selectedProduction.length == 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    }

    var  productionOrder
    const link = (this.router.url).split('/')
    if(link[3] == 'productionOrderSfg'){
     productionOrder = 'SFG';
    }
    if(link[3] == 'productionOrderFg'){
      productionOrder = 'FG';
    }
    

    let paramdata = "";
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selectedProduction[0] : null, productionOrder: productionOrder});
    this.router.navigate(['/main/manufacturing/productionOrder-New/' + paramdata]);
  }

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }

  openDialog2(element) {
    const dialogRef = this.dialog.open(MasterrecipeTableComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: element
    });
    dialogRef.afterClosed().subscribe(result => {
        this.getAll();
    });
  }
  
  getAll() {
  
    if (this.auth.userTypeId == 1) {
      this.superAdmin()
    } else {
      this.adminUser()
    }
  }

  adminUser() {
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];

    const link = (this.router.url).split('/')
    if(link[3] == 'productionOrderSfg'){
      obj.productionOrderType = ['SFG'];
    }
    if(link[3] == 'productionOrderFg'){
      obj.productionOrderType = ['FG'];
    }
    
    this.spin.show();
    this.sub.add(this.service.searchLine(obj).subscribe((res: any[]) => {
      if (res) {
        this.currrencys = res;
      }
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  superAdmin() {
    const link = (this.router.url).split('/')
    let obj: any = {};
    if(link[3] == 'productionOrderSfg'){
      obj.productionOrderType = ['SFG'];
    }
    if(link[3] == 'productionOrderFg'){
      obj.productionOrderType = ['FG'];
    }

    this.spin.show();
    this.sub.add(this.service.searchLine(obj).subscribe((res: any[]) => {
      if (res) {
        this.currrencys = res;
      } this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

  downloadexcel() {
    var res: any = [];
    this.currrencys.forEach(x => {
      res.push({
        // "LanguageId": x.languageId,
        "Company": x.companyCode,
        "OrderNo": x.orderNumber,
        // "Plant": x.plantId,
        // "Warehouse": x.warehouseId,
        // " Parent Part No": x.parentItemCode,
        // "Child ItemCode ": x.childItemCode,
        // "Child Item Qty": x.childItemQuantity,
        // 'Seq No': x.referenceField2,
        'Status': this.cs.getstatus_text(x.statusId),
        'Created By': x.createdby,
        "Created On": this.cs.dateapi(x.createdOn)
      });

    })
    this.cs.exportAsExcel(res, "Process BOM");
  }


  confirm(data){
    let paramdata = "";
    paramdata = this.cs.encrypt({code: data});
    this.router.navigate(['/main/manufacturing/productionOrder-confirm/' + paramdata]);
  }

  selectedProduction:any[] = [];
  onChange() {
    const choosen = this.selectedProduction[this.selectedProduction.length - 1];
    this.selectedProduction.length = 0;
    this.selectedProduction.push(choosen);
  }
}
