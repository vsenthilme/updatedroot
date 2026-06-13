import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { ProductionService } from '../production.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { FormService } from './form.service';
import { MatDialog } from '@angular/material/dialog';
import { OperationFieldsComponent } from './operation-fields/operation-fields.component';

@Component({
  selector: 'app-production-order-confirm',
  templateUrl: './production-order-confirm.component.html',
  styleUrls: ['./production-order-confirm.component.scss']
})
export class ProductionOrderConfirmComponent implements OnInit {

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
    private formService: FormService,
    public dialog: MatDialog,
  ) { }



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
    batchQuantity: [],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    expectedQuantity: [],
    itemCode: [],
    itemGroup: [],
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
    plantDescription: ['sss',],
    plantId: [this.auth.plantId,],
    warehouseDescription: [this.auth.warehouseIdAndDescription,],
    warehouseId: [this.auth.warehouseId,],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    languageId: [this.auth.languageId,],
    numberOfBatches: [],
    orderConfirmedBy: [],
    orderConfirmedOn: [],
    orderEndDate: [],
    moq: [],
    orderStartDate: [],
    productionLine: this.ProductionLine,
    productionOrderNo: [],
    receipePercentage: [],
    receipId: [],
    remarks: [],
    statusDescription: [],
    statusId: [],
    totalConfirmedQuantity: [],
    totalOrderQuantity: [],
    updatedBy: [],
    updatedOn: [],
  });
  dynamicForm: FormGroup;
  
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);
    // this.form.controls.updatedBy.disable();
    // this.form.controls.createdBy.disable();
      this.fill(this.js.code);
    this.callTableHeader();

    this.form.controls.companyDescription.disable();
    this.form.controls.LanguageId.disable();
    this.form.controls.warehouseDescription.disable();
    this.form.controls.plantDescription.disable();

   

    this.operationDetails = [{processNo: '2'}]
  }

  cols:any[] = [];
  cols2:any[] = [];
  operationDetails:any[] = [];
  bomDetails:any[] = [];
  
  callTableHeader() {
    this.cols = [
      { field: 'operationNumber', header: 'Operation ID' },
      { field: 'operationDescription', header: 'Operation Description' },
      { field: 'phaseNumber', header: 'Process No', format: 'extra' },
      { field: 'phaseDescription', header: 'Process Description' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'actions', header: 'Actions', format: 'extra' },
    ];
    this.cols2 = [
      { field: 'childItemCode', header: 'Incredient Code' },
      { field: 'referenceField6', header: 'Name' },
      { field: 'workCenterId', header: 'Item Type' },
      { field: 'requiredQty', header: 'Required Qty' },
      { field: 'consumptionQty', header: 'Consumption Qty' },
      { field: 'uom', header: 'UOM' },
    ];
  }

  sub = new Subscription();

  fill(line) {
    this.production.getConfirm(line).subscribe(res => {
      this.form.patchValue(res.productionOrder);
      this.operationDetails = res.operationConsumption;
    });
  }

  save() {
    this.spin.show();
    this.production.Create([this.form.getRawValue()]).subscribe(
      result => {
        this.spin.hide();
        this.toastr.success(
          "Created Successfully",
          "Notification"
        );
        this.router.navigate(['/main/manufacturing/productionOrder'])
      },
      error => {
        this.spin.hide();
        this.cs.commonerrorNew(error);
      }
    );
  }

  openDialog2(data){
    const dialogRef = this.dialog.open(OperationFieldsComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: { lines: data}
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.dynamicForm = result.form;
        this.form.addControl(result.groupName, this.dynamicForm);
      }
    });
  }

}








