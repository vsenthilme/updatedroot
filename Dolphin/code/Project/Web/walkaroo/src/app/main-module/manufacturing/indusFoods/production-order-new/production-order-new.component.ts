import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { ProductionService } from '../production.service';
import { get } from 'http';
import { MatDialog } from '@angular/material/dialog';
import { DynamicPopupComponent } from 'src/app/common-field/dynamic-popup/dynamic-popup.component';

@Component({
  selector: 'app-production-order-new',
  templateUrl: './production-order-new.component.html',
  styleUrls: ['./production-order-new.component.scss']
})
export class ProductionOrderNewComponent implements OnInit {

  isLinear = false;
  constructor(private fb: FormBuilder,
    public auth: AuthService,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private cas: CommonApiService,
    private ReportsService: ReportsService,
    private itemService: BasicdataService,
    private bom: BOMService,
    private production: ProductionService,
    public dialog: MatDialog,
  ) { }




  ProductionLine = this.fb.group({
    companyCodeId: [this.auth.companyId,],
    companyDescription: [this.auth.companyIdAndDescription,],
    plantDescription: [this.auth.plantIdAndDescription,],
    plantId: [this.auth.plantId,],
    warehouseDescription: [this.auth.warehouseIdAndDescription,],
    warehouseId: [this.auth.warehouseId,],
    actualQuantity: [],
    batchDate: [],
    batchNumber: [],
    batchQuantity: [, Validators.required],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    expectedQuantity: [],
    itemCode: [],
    itemGroup: [],
    itemGroupDescription: [],
    itemTypeDescription: [],
    itemDescription: [],
    itemType: [],
    languageId: [this.auth.languageId,],
    lossPercentage: [],
    lossQuantity: [],
    orderConfirmedBy: [],
    orderConfirmedOn: [],
    orderQuantity: [],
    productionOrderLineNo: [],
    productionOrderNo: [],
    receipeId: [],
    remarks: [],
    statusDescription: [],
    statusId: [],
    updatedBy: [],
    updatedOn: [],
    uom: [],
    yieldPercentage: [],
  });

  form = this.fb.group({
    companyCodeId: [this.auth.companyId,],
    companyDescription: [this.auth.companyIdAndDescription,],
    plantDescription: [this.auth.plantIdAndDescription,],
    plantId: [this.auth.plantId,],
    warehouseDescription: [this.auth.warehouseIdAndDescription,],
    warehouseId: [this.auth.warehouseId,],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    languageId: [this.auth.languageId,],
    numberOfBatches: [, Validators.required],
    orderConfirmedBy: [],
    orderConfirmedOn: [],
    orderEndDate: [],
    moq: [],
    orderStartDate: [],
    productionLine: this.ProductionLine,
    productionOrderNo: [],
    receipePercentage: [100,],
    receipId: [],
    remarks: [],
    statusDescription: [],
    statusId: [],
    totalConfirmedQuantity: [],
    totalOrderQuantity: [],
    updatedBy: [],
    updatedOn: [],
  });


  filterpartnercodeList: any[] = [];
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


  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  public errorHandling = (control: string, error: string = "required") => {
    return this.form.controls[control].hasError(error) && this.submitted;
  }

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  js: any = {}

  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  itemCodeList1: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.ReportsService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, description: x.description })); //+x.manufacturerName
          }
        });
    }
  }

  itemCodeChanged(event?: any) {
    console.log(event)
    this.spin.show();

    let obj: any = {};

    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.itemCode = [this.form.get('productionLine')?.get('itemCode')?.value];
    obj.parentItemCode = [this.form.get('productionLine')?.get('itemCode')?.value];

    if (this.js.pageflow == "New") {
      this.itemService.imfBasicData(obj).subscribe(res => {
        if (res.length > 0) {
          this.form.get('productionLine')?.get('uom')?.patchValue(res[0].uomId);
          this.form.get('productionLine')?.get('itemDescription')?.patchValue(event.description);
          this.form.get('productionLine')?.get('itemType')?.patchValue(res[0].itemType);
          this.form.get('productionLine')?.get('itemGroup')?.patchValue(res[0].itemGroup);
          this.form.get('productionLine')?.get('itemTypeDescription')?.patchValue(res[0].itemTypeDescription);
          this.form.get('productionLine')?.get('itemGroupDescription')?.patchValue(res[0].itemGroupDescription);
          this.form.get('productionLine')?.get('batchQuantity')?.patchValue(res[0].batchQuantity);
          this.form.get('productionLine')?.get('moq')?.patchValue(res[0].moq);
          this.spin.hide();
        }
      }, error => {
        this.cs.commonerrorNew(error);
        this.spin.hide();
      })
    }

    this.ReportsService.findInventory2(obj).subscribe(res => {
      if (res.length > 0) {
        this.form.get('productionLine')?.get('actualQuantity')?.patchValue(res[0].inventoryQuantity);
        this.spin.hide();
      } else {
        this.form.get('productionLine')?.get('actualQuantity')?.patchValue(0);
      }
    }, error => {
      this.cs.commonerrorNew(error);
      this.spin.hide();
    })

    this.bom.search(obj).subscribe(res => {
      if (res.length > 0) {
        res[0].bomLines.forEach(element => {
          element['requiredQty'] = element.childItemQuantity * this.form.controls.totalOrderQuantity.value * (this.form.controls.receipePercentage.value / 100)
        });
        this.bomDetails = res[0].bomLines;

        // to patch the bom details
        const childItemCode = this.bomDetails.map(item => item.childItemCode);
        this.ReportsService.findInventory2({ itemCode: childItemCode }).subscribe(childItemResult => {
          if (childItemResult.length > 0) {
            this.bomDetails.forEach((x) => {
              x['stockAvailable'] = childItemResult.find(y => y.itemCode == x.childItemCode)?.inventoryQuantity || 0;
              x['requiredQty'] = (x.childItemQuantity * this.form.controls.totalOrderQuantity.value * (this.form.controls.receipePercentage.value / 100)).toFixed(2);
              x['remainingQuantity'] = (((x.requiredQty ?? 0)) - (x.stockAvailable ?? 0) > 0 ? ((x.requiredQty ?? 0)) - (x.stockAvailable ?? 0) : 0);
            })
          } else {
            this.bomDetails.forEach((x) => {
              x['stockAvailable'] = 0;
              x['requiredQty'] = (x.childItemQuantity * this.form.controls.totalOrderQuantity.value * (this.form.controls.receipePercentage.value / 100)).toFixed(2);
              x['remainingQuantity'] = (((x.requiredQty ?? 0)) - (x.stockAvailable ?? 0) > 0 ? ((x.requiredQty ?? 0)) - (x.stockAvailable ?? 0) : 0);
            })
          }
          this.spin.hide();
        }, error => {
          this.cs.commonerrorNew(error);
          this.spin.hide();
        })

        this.ReportsService.operationConsumption({ itemCode: childItemCode }).subscribe(operationConsumptionResult => {
          if (operationConsumptionResult.length > 0) {
            this.bomDetails.forEach((x) => {
              x['issuedQty'] = operationConsumptionResult.find(y => y.itemCode == x.childItemCode && y.productionOrderNo == this.form.controls.productionOrderNo.value)?.issuedQuantity || 0;
            })
          }else {
            this.bomDetails.forEach((x) => {
              x['issuedQty'] = 0;
            })
          }
          this.spin.hide();
        }, error => {
          this.cs.commonerrorNew(error);
          this.spin.hide();
        })
        ////

        this.spin.hide();
      }
    }, error => {
      this.cs.commonerrorNew(error);
      this.spin.hide();
    })

    this.bom.searchMasterReceipe(obj).subscribe(res => {
      if (res.length > 0) {
        this.form.controls.receipId.patchValue(res[0].receipeId);
        res.forEach(x => {
          if(x.requiredQuantity == null){
           x.requiredQuantity = 0;
          }
        })
        this.operationDetails = res;
        this.spin.hide();
      }
    }, error => {
      this.cs.commonerrorNew(error);
      this.spin.hide();
    })

  }

  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);

    this.form.controls.companyDescription.disable();
    this.form.controls.languageId.disable();
    this.form.controls.warehouseDescription.disable();
    this.form.controls.plantDescription.disable();
    this.form.get('productionLine')?.get('uom')?.disable();
    this.form.get('productionLine')?.get('itemTypeDescription')?.disable();
    this.form.get('productionLine')?.get('itemGroupDescription')?.disable();
    this.form.get('productionLine')?.get('actualQuantity')?.disable();
    this.form.controls.receipId.disable();

   
    if (this.js.pageflow == "Edit") {
      this.fill(this.js.code);
    }
    this.callTableHeader();
  }

  cols: any[] = [];
  cols2: any[] = [];
  bomDetails: any[] = [];
  operationDetails: any[] = [];

  callTableHeader() {
    this.cols = [
      { field: 'childItemCode', header: 'Ingredient Code' },
      { field: 'referenceField6', header: ' Name' },
      { field: 'referenceField10', header: 'Item Type' },
      { field: 'childItemQuantity', header: 'BOM Qty' },
      { field: 'requiredQty', header: 'Required Qty' },
      { field: 'stockAvailable', header: 'Stock Availablity' },
      { field: 'remainingQuantity', header: 'Remaining Req Qty' },
      { field: 'issuedQty', header: 'Issued Qty' },
      { field: 'referenceField7', header: 'UOM' }
    ];
    this.cols2 = [
      { field: 'phaseNumber', header: 'Process No' },
      { field: 'phaseDescription', header: 'Process Description' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'referenceField6', header: 'Name' },
      { field: 'referenceField5', header: 'BOM Qty' },
      { field: 'requiredQuantity', header: 'Req Qty' },
    ];
  }

  sub = new Subscription();
  fill(line) {
    this.production.Get(line).subscribe(res => {
      this.form.patchValue(res);
      this.itemCodeChanged();
      this.calculateBatchQty()
    });
  }

  save() {
    if (this.form.invalid) {
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

    this.spin.show();
    if (this.js.pageflow == "Edit") {
      this.production.Update([this.form.getRawValue()]).subscribe(
        result => {
          this.spin.hide();
          this.toastr.success(
            "Updated Successfully",
            "Notification"
          );
          this.router.navigate(['/main/manufacturing/productionOrderSfg'])
        },
        error => {
          this.spin.hide();
          this.cs.commonerrorNew(error);
        }
      );
    } else if(this.js.productionOrder == "SFG"){
      this.production.createSFG([this.form.getRawValue()]).subscribe(
        result => {
          this.spin.hide();
          this.toastr.success(
            "Created Successfully",
            "Notification"
          );
          this.router.navigate(['/main/manufacturing/productionOrderSfg'])
        },
        error => {
          this.spin.hide();
          this.cs.commonerrorNew(error);
        }
      );
    }
    else{
      this.production.Create([this.form.getRawValue()]).subscribe(
        result => {
          this.spin.hide();
          this.toastr.success(
            "Created Successfully",
            "Notification"
          );
          this.router.navigate(['/main/manufacturing/productionOrderSfg'])
        },
        error => {
          this.spin.hide();
          this.cs.commonerrorNew(error);
        }
      );
    }
  }

  calculateBatchQty() {
    const qty = (this.form.get('productionLine')?.get('batchQuantity')?.value ?? 0) * (this.form.controls.numberOfBatches.value ?? 0);
    this.form.controls.totalOrderQuantity.patchValue(qty);
    this.calculateBom();
  }

  calculateBom() {
    this.bomDetails.forEach(element => {
      element['requiredQty'] = (element.childItemQuantity *  this.form.controls.totalOrderQuantity.value * (this.form.controls.receipePercentage.value / 100)).toFixed(2);
      element['remainingQuantity'] = (((element.requiredQty ?? 0)) - (element.stockAvailable ?? 0) > 0 ? ((element.requiredQty ?? 0)) - (element.stockAvailable ?? 0) : 0);
    });

  }


  callProcessDetails(line) {
    const cols = [
      { field: 'phaseNumber', header: 'Process No' },
      { field: 'phaseDescription', header: 'Process Description' },
      { field: 'referenceField6', header: 'Ingredient' },
      { field: 'referenceField5', header: 'Bom Qty' },
      { field: 'requiredQuantity', header: 'Req Qty' },
    ];
    const dialogRef = this.dialog.open(DynamicPopupComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: { cols: cols, lines: line, title: 'Process', pageFrom: 'masterReceipe' },
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
      }
    });
  }
}








