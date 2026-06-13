import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, FormArray } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';
import { ChilditemcodeComponent } from '../../bom/bom-new/childitemcode/childitemcode.component';
import { BOMService } from '../../bom/bom.service';
import { MasterchildComponent } from './masterchild/masterchild.component';
import { BasicdataService } from '../../../masternew/basicdata/basicdata.service';

@Component({
  selector: 'app-masterrecipe-new',
  templateUrl: './masterrecipe-new.component.html',
  styleUrls: ['./masterrecipe-new.component.scss']
})
export class MasterrecipeNewComponent implements OnInit {

  constructor(private fb: FormBuilder,
    public auth: AuthService,
    public dialog: MatDialog,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private cs: CommonService,
    private cas: CommonApiService,
    private reportService: ReportsService,
    private itemService: BasicdataService,
    private service: BOMService,
    private masterService: MasterService
  ) { }

  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

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

  disabled = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  panelOpenState = false;
  submitted = false;

  referenceField8: any;
  statusId: any;
  operationNumber:any;
  description: any;
  itemTypeList: any[] = [];
  js: any = {}
routingList:any[]=[];
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);

    this.parentItemQuantity = 1;

    let obj1: any = {};
    obj1.companyCodeId = this.auth.companyId;
    obj1.plantId = this.auth.plantId;
    obj1.languageId = [this.auth.languageId];
    obj1.warehouseId = this.auth.warehouseId;

    this.spin.show();
    this.masterService.searchitemtype(obj1).subscribe((res: any[]) => {
      res.forEach(element => {
        this.itemTypeList.push({ value: (element.itemTypeId).toString(), label: element.itemTypeId + '-' + element.itemType });
      });
      this.spin.hide();
    })
    // this.service.searchMasterOperation(obj1).subscribe((res: any[]) => {
    //   res.forEach(element => {
    //     this.routingList.push({ value: element.operationNumber, label: element.operationNumber + '-' + element.operationDescription });
    //   });
    //   this.spin.hide();
    // })
    if (this.js.pageflow != 'New') {
        this.fill();
    }
  }

  resultTable2: any;
  sub = new Subscription();
  
  fill() {
    this.spin.show();
    let obj: any = {};
    obj.companyCode = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.bomNumber = [this.js.bomNumber];

    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any) => {
      this.statusId = (res.statusId != null ? res.statusId.toString() : '');
      this.resultTable2 = res[0];
      this.parentItemCode = res[0].parentItemCode;
      this.parentItemQuantity = res[0].parentItemQuantity;
      this.referenceField8=res[0].referenceField8;
      this.referenceField5=res[0].referenceField5;
      this.resultTable = res[0].bomLines;
      this.statusId = res[0].statusId.toString();
      let obj1: any = {};
      obj1.companyCode = [this.auth.companyId];
      obj1.plantId = [this.auth.plantId];
      obj1.languageId = [this.auth.languageId];
      obj1.warehouseId = [this.auth.warehouseId];
      obj1.itemCode = [this.parentItemCode];
      this.sub.add(this.itemService.search(obj1).subscribe((res: any) => {
        this.referenceField8 = (res[0].itemType).toString();
        this.referenceField5=res[0].description;
        this.spin.hide();
  
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      })); 
      this.spin.hide();

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
    
    //this.dropdownlist();
    this.spin.hide();
  }
  openDialog(data: any = 'New', rowIndex: any): void {
    const dialogRef = this.dialog.open(MasterchildComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: { pageflow: data, code: this.resultTable[rowIndex] },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.resultTable.splice(rowIndex, 0);
        this.resultTable.splice(rowIndex, 1, result);
        
        //this.form.patchValue(result);
        this.resultTable = [...this.resultTable]

      }
    });

  }

  pricelist: any;
  parentItemCode: any;
  parentItemQuantity: any;
  referenceField5:any;


  add() {
    const dialogRef = this.dialog.open(MasterchildComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data: this.resultTable.length + 1
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result.length);
      if (result) {
        this.resultTable
        if (result.length > 0) {
          this.resultTable.push(result);
        }
        this.resultTable.push(result);
      }
      this.resultTable = [...this.resultTable];
    });
  }

  delete(i) {
    this.resultTable.splice(i, 1);
  }


  resultTable: any[] = [];
  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];

  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, manufacturingName: x.manufacturerName, description: x.description, uomId: x.uomId }))
          }
        });
    }
  }
  itemCodeChange(event){
    let obj: any = {};
    obj.companyCode = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.itemCode = [event.value];
    this.sub.add(this.itemService.search(obj).subscribe((res: any) => {
      this.referenceField8 = (res[0].itemType).toString();
      this.referenceField5=res[0].description;
      this.spin.hide();

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })); 
  }
  itemCode: any;
  submit() {
    console.log(this.resultTable)
    if (this.resultTable == null) {
      this.toastr.error(
        "Please fill required fields to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.cs.notifyOther(true);
      return;
    }

    this.resultTable.forEach(x => {
      x.referenceField1 = this.parentItemCode;
      x.referenceField3 = this.parentItemQuantity;
      x.sequenceNo = x.referenceField2;
    });

    if (this.js.pageFlow == "Edit") {
      this.resultTable2.bomLines = this.resultTable;

      this.resultTable.forEach(x => {
        x.sequenceNo = x.referenceField2;

      });
    }
console.log(this.resultTable)
    this.submitted = true;

    this.cs.notifyOther(false);
    this.spin.show();
   
    if (this.js.code) {
      this.resultTable2.referenceField5=this.referenceField5;
      this.sub.add(this.service.Update(this.resultTable2, this.parentItemCode, this.auth.warehouseId, this.auth.plantId, this.auth.companyId, this.auth.languageId).subscribe(res => {
        this.toastr.success(res.bomNumber + " updated successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.router.navigate(['/main/other-masters/receipe']);
        this.spin.hide();
      }, err => {

        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
    }
    else {
      this.resultTable.forEach(x => {
        x.referenceField1 = this.parentItemCode.toString();
        x.referenceField3 = this.parentItemQuantity.toString();
      });

      let obj: any = {};
      obj.companyCode = this.auth.companyId;
      obj.plantId = this.auth.plantId;
      obj.warehouseId = this.auth.warehouseId;
      obj.parentItemCode = this.parentItemCode;
      obj.referenceField5=this.referenceField5;
      obj.parentItemQuantity = this.parentItemQuantity;
      obj.languageId = this.auth.languageId;
      obj.referenceField8 = this.referenceField8;
      obj.statusId = 1;
      obj.bomLines = this.resultTable;

      this.sub.add(this.service.Create(obj).subscribe(res => {
        this.toastr.success(res.bomNumber + " Saved Successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.router.navigate(['/main/other-masters/receipe']);
        this.spin.hide();

      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
    }
  }
}



