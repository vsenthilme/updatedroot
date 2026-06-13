import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
import { Location } from "@angular/common";
import { DynamicPopupComponent } from 'src/app/common-field/dynamic-popup/dynamic-popup.component';
import { Customdialog2Component } from 'src/app/common-field/customdialog2/customdialog2.component';
import { tickStep } from 'd3';
import { debug } from 'console';

@Component({
  selector: 'app-production-order-confirm',
  templateUrl: './production-order-confirm.component.html',
  styleUrls: ['./production-order-confirm.component.scss']
})
export class ProductionOrderConfirmComponent implements OnInit {
  defaultStepIndex = 1;
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
    private location: Location
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




  productionLine = this.fb.group({
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
    operationNumber: [],
    productionOrderLineNo: [],
    productionOrderNo: [],
    productionOrderType: [],
    parentProductionOrderNo: [],
    receipeId: [],
    remarks: [],
    statusDescription: [],
    statusId: [],
    updatedBy: [],
    updatedOn: [],
    uom: [],
    yieldPercentage: [],
  });

  operationConsumption = this.fb.group({
    batchDate: [''],
    batchNumber: [''],
    beProcessConfirm: [''],
    bomItem: [''],
    bomQuantity: [''],
    companyCodeId: [''],
    companyDescription: [''],
    consumedQuantity: [''],
    createdBy: [''],
    createdOn: [''],
    deletionIndicator: [''],
    issuedBy: [''],
    issuedOn: [''],
    issuedQuantity: [''],
    itemCode: [''],
    itemDescription: [''],
    itemGroup: [''],
    itemType: [''],
    languageId: [''],
    operationDescription: [''],
    operationNumber: [''],
    orderConfirmedBy: [''],
    orderConfirmedOn: [''],
    phaseDescription: [''],
    phaseNumber: [''],
    plantDescription: [''],
    plantId: [''],
    productionOrderLineNo: [''],
    productionOrderNo: [''],
    receipeId: [''],
    receipeQuantity: [''],
    remarks: [''],
    statusDescription: [''],
    statusId: [''],
    uiProcessConfirm: [''],
    uom: [''],
    updatedBy: [''],
    updatedOn: [''],
    warehouseDescription: [''],
    warehouseId: ['']
  })

  form = this.fb.group({
    productionOrder: this.fb.group({
      companyCodeId: [''],
      companyDescription: [''],
      createdBy: [''],
      createdOn: [''],
      deletionIndicator: [''],
      languageId: [''],
      numberOfBatches: [''],
      orderConfirmedBy: [''],
      orderConfirmedOn: [''],
      orderEndDate: [''],
      orderStartDate: [''],
      plantDescription: [''],
      warehouseDescription: [''],
      plantId: [''],
      productionOrderNo: [''],
      receipePercentage: [''],
      referenceField1: [],
      remarks: [''],
      statusDescription: [''],
      statusId: [''],
      totalConfirmedQuantity: [''],
      totalOrderQuantity: [''],
      updatedBy: [''],
      updatedOn: [''],
      warehouseId: [''],
      productionLine: this.productionLine
    }),
    operationConsumption: this.fb.array([this.operationConsumption])
  });
  dynamicForm: FormGroup;

  operationConsumptionControl() {
    // A getter for easy access to the operationConsumption FormArray
    return this.form.get('operationConsumption') as FormArray;
  }

  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);
    // this.form.controls.updatedBy.disable();
    // this.form.controls.createdBy.disable();
    this.fill(this.js.code);
    this.callTableHeader();

    // this.form.controls.companyDescription.disable();
    // this.form.controls.LanguageId.disable();
    // this.form.controls.warehouseDescription.disable();
    // this.form.controls.plantDescription.disable();



    this.operationDetails = [{ processNo: '2' }]
  }

  cols: any[] = [];
  cols2: any[] = [];
  operationDetails: any[] = [];
  bomDetails: any[] = [];

  callTableHeader() {
    this.cols = [
      { field: 'operationNumber', header: 'Operation ID' },
      { field: 'operationDescription', header: 'Operation Description' },
      { field: 'phaseNumber', header: 'Process No', },
      { field: 'phaseDescription', header: 'Process Description' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'actions', header: 'Actions', format: 'extra' },
    ];
    this.cols2 = [
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'referenceField1', header: 'Name' },
      { field: 'itemTypeDescription', header: 'Item Type' },
      { field: 'receipeQuantity', header: 'Required Qty' },
      { field: 'consumedQuantity', header: 'Consumption Qty' },
      { field: 'issuedQuantity', header: 'Issued Qty' },
      { field: 'uom', header: 'UOM' },
    ];
  }

  sub = new Subscription();

  fill(line) {
    this.production.getConfirm(line).subscribe(res => {
      ;
      this.form.patchValue(res);

      const operationConsumptionArray = this.form.get('operationConsumption') as FormArray;
      while (operationConsumptionArray.length !== 0) {
        operationConsumptionArray.removeAt(0);
      }
      // Add new values to operationConsumption array
      res.operationConsumption.forEach(item => {
        operationConsumptionArray.push(this.fb.group(item));
      });

      this.form.get('productionOrder')?.get('companyDescription')?.disable();
      this.form.get('productionOrder')?.get('languageId')?.disable();
      this.form.get('productionOrder')?.get('plantDescription')?.disable();
      this.form.get('productionOrder')?.get('warehouseDescription')?.disable();

      const removeduplicates = this.cs.removeDuplicatesFromArrayList(res.operationConsumption, 'phaseNumber');
      this.operationDetails = removeduplicates;
      this.bomDetails = res.operationConsumption;
    });
  }

  save() {
    this.spin.show();
    this.production.confirmProdcution(this.form.getRawValue()).subscribe(
      result => {
        this.spin.hide();
        this.toastr.success(
          "Confirmed Successfully",
          "Notification"
        );
        this.fill(this.js.code);
      },
      error => {
        this.spin.hide();
        this.cs.commonerrorNew(error);
      }
    );
  }

  openDialog2(data, index) {
    const dialogRef = this.dialog.open(OperationFieldsComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: { lines: data, bomDetails: this.bomDetails }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const operationConsumptionArray = this.form.get('operationConsumption') as FormArray;
        while (operationConsumptionArray.length !== 0) {
          operationConsumptionArray.removeAt(0);
        }
        // Add new values to operationConsumption array
        result.bomDetails.forEach(item => {
          operationConsumptionArray.push(this.fb.group(item));
        });
        this.form.get('productionOrder')?.get('referenceField1')?.patchValue(data.phaseNumber)

        if ((result.groupName != undefined) && (result.form.length != 0)) {
          this.form.removeControl(result.groupName);
          this.dynamicForm = result.form;
          this.form.addControl(result.groupName, this.dynamicForm);
        }

        if (index == this.operationDetails.length) {
          this.callActualQty();
        } else {
          this.save();
        }
      }
    });
  }


  callActualQty() {
    const dialogRef = this.dialog.open(Customdialog2Component, {
      width: '25%',
      maxWidth: '30%',
      position: { top: '6.7%', },
      data: { title: "Quantity", body: "Actual Qty" },
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.form.get('productionOrder')?.get('productionLine')?.get('actualQuantity')?.patchValue(result.remarks);
       // this.form.get('productionOrder')?.get('productionLine')?.get('orderQuantity')?.patchValue(result.remarks);
        const orderQty =  this.form.get('productionOrder')?.get('totalOrderQuantity')?.value;
        const confirmQty = result.remarks;
        const yieldPercentage = ((Number(confirmQty) / Number(orderQty)) * 100).toFixed(2);
        const lossQuantity = Number(orderQty) - Number(confirmQty);
        
        this.form.get('productionOrder')?.get('productionLine')?.get('yieldPercentage')?.patchValue(yieldPercentage);
        this.form.get('productionOrder')?.get('productionLine')?.get('lossQuantity')?.patchValue(lossQuantity);
        this.defaultStepIndex = 0;
       this.save();
      }
    });
  }

  back(){
    this.location.back();
  }
}








