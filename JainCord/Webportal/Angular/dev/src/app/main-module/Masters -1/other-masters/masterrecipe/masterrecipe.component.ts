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
import { BomTableComponent } from '../bom/bom-table/bom-table.component';
import { BOMElement, BOMService } from '../bom/bom.service';
import { MasterrecipeTableComponent } from './masterrecipe-table/masterrecipe-table.component';

@Component({
  selector: 'app-masterrecipe',
  templateUrl: './masterrecipe.component.html',
  styleUrls: ['./masterrecipe.component.scss']
})
export class MasterrecipeComponent implements OnInit {

  screenid = 3227;
  advanceFilterShow: boolean;
  @ViewChild('Setupcurrency') Setupcurrency: Table | undefined;
  sub = new Subscription();

  currrencys: any;
  currencyOg:any;
  selectedcurrency: any;
  ELEMENT_DATA: BOMElement[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  constructor(public dialog: MatDialog,
    private service: BOMService,
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
  }

  deleteDialog() {
    if (this.selectedcurrency.length === 0) {
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
    this.sub.add(this.service.Delete(id.parentItemCode, id.warehouseId, id.plantId, id.companyCode, id.languageId).subscribe((res) => {
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

  openDialog(data: any = 'New'): void {
    if (data != 'New') {
      if (this.selectedcurrency.length == 0) {
        this.toastr.warning("Kindly select any Row", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    }
    let paramdata = "";
    paramdata = this.cs.encrypt({ pageflow: data, code: data != 'New' ? this.selectedcurrency[0].parentItemCode : null, warehouseId: data != 'New' ? this.selectedcurrency[0].warehouseId : null, languageId: data != 'New' ? this.selectedcurrency[0].languageId : null, plantId: data != 'New' ? this.selectedcurrency[0].plantId : null, companyCodeId: data != 'New' ? this.selectedcurrency[0].companyCodeId : null, bomNumber: data != 'New' ? this.selectedcurrency[0].bomNumber : null });
    this.router.navigate(['/main/other-masters/receipeNew/' + paramdata]);
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
   
      this.adminUser()
    
  }

  adminUser() {
    let obj: any = {};
    obj.companyCode= [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any[]) => {
      if (res) {
        this.currrencys = res;
      }
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

 

  downloadexcel() {
    
    var filteredCurrrencys = this.currrencys.filter(x => x.bomLines && x.bomLines.length > 0);
    var res: any[] = [];
    filteredCurrrencys.forEach(x => {
        x.bomLines.forEach(bomLine => {
            res.push({
                "LanguageId": bomLine.languageId,
                "Company": bomLine.companyCode,
                "Plant": bomLine.plantId,
                "Warehouse": bomLine.warehouseId,
                "Ingredient": bomLine.referenceField1,
                "Child ItemCode": bomLine.childItemCode, 
                "Child Item Qty": bomLine.childItemQuantity,
                
                'Created By': bomLine.createdBy,
                "Created On": this.cs.dateapi(bomLine.createdOn)
            });
        });
    });

    // Export `res` as Excel
    this.cs.exportAsExcel(res, "Process BOM");
}



  onChange() {
    console.log(this.selectedcurrency.length)
    const choosen = this.selectedcurrency[this.selectedcurrency.length - 1];
    this.selectedcurrency.length = 0;
    this.selectedcurrency.push(choosen);
  }
}
