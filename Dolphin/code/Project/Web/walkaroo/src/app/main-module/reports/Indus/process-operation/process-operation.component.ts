import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { ReportsService } from '../../reports.service';
import { ProductionService } from 'src/app/main-module/manufacturing/indusFoods/production.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { ProcessService } from './process.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-process-operation',
  templateUrl: './process-operation.component.html',
  styleUrls: ['./process-operation.component.scss']
})
export class ProcessOperationComponent implements OnInit {
  screenid = 3225;
  @ViewChild('binnerTag') binnerTag: Table | any;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  selectedCompany: any[] = [];


  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];

  inboundOrderType: any[] = [];
  constructor(public dialog: MatDialog,
    private cas: CommonApiService,
    private http: HttpClient,
    private fb: FormBuilder,
    public toastr: ToastrService,
    private router: Router,
    private spin: NgxSpinnerService,
    public cs: CommonService,
    public auth: AuthService,
    private processService: ProcessService,
    private BOMService: BOMService,
    private reportService: ReportsService,
    private datePipe: DatePipe,
    private productionService: ProductionService) {

    this.multistatusList = [
      {label: 'Created',value: 97,},
      {label: 'Confirmed',value: 98},
    ];
    this.processList = [
      {label: 'Cooking',value: 'cooking',},
      {label: 'Chopping',value: 'diceSliceChop'},
      {label: 'Paste',value: 'paste'},
      {label: 'Peeling',value: 'peeling'},
      {label: 'Powder',value: 'powder'},
      {label: 'Soaking',value: 'soaking'},
      {label: 'Sorting',value: 'sorting'}
    ];

  }

  selectedplant: any;
  selectedwarehouse: any;
  ngOnInit(): void {
    this.getDropdown();
    this.selectedCompany = this.auth.companyIdAndDescription;
    this.selectedplant = this.auth.plantIdAndDescription;
    this.selectedwarehouse = this.auth.warehouseIdAndDescription;
    this.cooking();
  }

  cols: any[] = [];
  tableStyle:any;
  tableValue: any;
  cooking() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'cookingStartTime', header: 'Cooking Start Time', format: 'date' },
      { field: 'cookingEndTime', header: 'Cooking End Time', format: 'date' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '100rem'};
  }
  diceSliceChop() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'startTime', header: 'Start Time', format: 'date' },
      { field: 'endTime', header: 'End Time', format: 'date' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '100rem'};
  }
  paste() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'issuedQuantity', header: 'Issued Qty' },
      { field: 'pasteStartTime', header: 'Paste Start Time', format: 'date' },
      { field: 'pasteEndTime', header: 'Paste End Time', format: 'date' },
      { field: 'waterQuantity', header: 'Water Qty' },
      { field: 'outputQty', header: 'Output Qty' },
      { field: 'noOfWorkers', header: 'No of Workers' },
      { field: 'supervisorName', header: 'Supervisor Name' },
      { field: 'storageLocation', header: 'Storage Location' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '150rem'};
  }
  peeling() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'pasteStartTime', header: 'Peeling Start Time', format: 'date' },
      { field: 'pasteEndTime', header: 'Peeling End Time', format: 'date' },
      { field: 'washingStartTime', header: 'Washing Start Time', format: 'date' },
      { field: 'washingEndTime', header: 'Washing End Time', format: 'date' },
      { field: 'boilingStartTime', header: 'Boling Start Time', format: 'date' },
      { field: 'boilingEndTime', header: 'Boling End Time', format: 'date' },
      { field: 'postBoilingWeight', header: 'Post Boiling Wt' },
      { field: 'brixOfBoiledDal', header: 'Brix of Boiled Dhal' },
      { field: 'outputQty', header: 'Output Qty' },
      { field: 'noOfWorkers', header: 'No of Workers' },
      { field: 'supervisorName', header: 'Supervisor Name' },
      { field: 'storageLocation', header: 'Storage Location' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '180rem'};
  }  
  powder() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'roastingStartTime', header: 'Roasting Start Time', format: 'date' },
      { field: 'roastingEndTime', header: 'Roasting End Time', format: 'date' },
      { field: 'postRoastingWeight', header: 'Post Roasting Wt' },
      { field: 'powderPreparationStartTime', header: 'Powder Start Time', format: 'date' },
      { field: 'powderPreparationEndTime', header: 'Powder End Time', format: 'date' },
      { field: 'outputQty', header: 'Output Qty' },
      { field: 'noOfWorkers', header: 'No of Workers' },
      { field: 'supervisorName', header: 'Supervisor Name' },
      { field: 'storageLocation', header: 'Storage Location' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '150rem'};
  }
  soaking() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'kettleBatchNumber', header: 'Kettle Batch No' },
      { field: 'soakingQuantity', header: 'Soaking Qty' },
      { field: 'waterQuantity', header: 'Water Qty' },
      { field: 'roomTemperature', header: 'Room Temperature' },
      { field: 'waterTemperature', header: 'Water Temperature' },
      { field: 'supervisorName', header: 'Supervisor Name' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '150rem'};
  }
  sorting() {
    this.cols = [
      { field: 'productionOrderNo', header: 'Production Order No' },
      { field: 'batchNumber', header: 'Batch No' },
      { field: 'itemCode', header: 'Ingredient Code' },
      { field: 'itemDescription', header: 'Ingredient' },
      { field: 'phaseNumber', header: 'Process ID' },
      { field: 'phaseDescription', header: 'Process Name' },
      { field: 'processQty', header: 'Qty Process' },
      { field: 'Qty', header: 'Output Qty' },
      { field: 'startTime', header: ' Start Time', format: 'date' },
      { field: 'endTime', header: ' End Time', format: 'date' },
      { field: 'impurities', header: 'Impurities' },
      { field: 'supervisorName', header: 'Supervisor Name' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'createdBy', header: 'Confirmed By' },
    ];
    this.tableStyle = {'width': '150rem'};
  }
  processList: any[] = [];
  multiselectBatchNumberList: any[] = [];
  multistatusList: any[] = [];
  
  multiselectProductionOrderNoList: any[] = [];
  multiamscycleList: any[] = [];
  getDropdown() {
    // this.sub.add(this.BOMService.searchMasterOperation({ warehouseId: [this.auth.warehouseId], companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], languageId: [this.auth.languageId] }).subscribe(res => {
    //   res.forEach((x: any) => this.processList.push({ value: x.phaseNumber, label: x.phaseNumber + ' - ' + x.phaseDescription }));
    //   this.processList = this.cs.removeDuplicatesFromArrayList(this.processList, 'label');
    // }))
    this.sub.add(this.productionService.search({ warehouseId: [this.auth.warehouseId], companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], languageId: [this.auth.languageId] }).subscribe(res => {
      res.forEach((x: any) => this.multiselectProductionOrderNoList.push({ value: x.productionOrderNo, label: x.productionOrderNo }));
    }))

  }



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
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description })); //+x.manufacturerName
          }
        });
    }
  }


  searhform = this.fb.group({
    companyCodeId: [[this.auth.companyId]],
    languageId: [[this.auth.languageId]],
    plantId: [[this.auth.plantId]],
    warehouseId: [[this.auth.warehouseId]],
    productionOrderNo: [],
    batchNumber: [],
    statusId: [],
    itemCode: [],
    endCreatedOn: [],
    operationNumber: [],
    productionOrderLineNo: [],
    receipeId: [],
    startCreatedOn: [],
    process: [, Validators.required],
    phaseNumber: [, ],
  });


  storageselection: any[] = [];
  filtersearch() {

    if ((this.searhform.invalid)) {
      this.spin.show();
      this.toastr.error("Please fill the required fields to continue", "", {
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      return;
    }

    this.spin.show();

    const currentValue = this.searhform.controls.itemCode.value;

    if (this.searhform.controls.itemCode.value == null || this.searhform.controls.itemCode.value == "") {
      this.searhform.controls.itemCode.patchValue(null);
    }
    else {
      if (!Array.isArray(currentValue)) {
        this.searhform.controls.itemCode.patchValue([currentValue]);
      }
    }

    this.sub.add(this.reportService.processReport(this.searhform.getRawValue()).subscribe(res => {
      this[this.searhform.controls.process.value]();
      this.tableValue = res[this.searhform.controls.process.value];
      this.table = true;
      this.search = false;
      this.fullscreen = false;
      this.back = true;
      this.spin.hide();
    },
      err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
  }

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

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }



  totalRecords = 0;
  downloadexcel() {
    const exportData = this.tableValue.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'data') {
          exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });

    // Excel service
    this.cs.exportAsExcel(exportData, 'UserRole');
  }


  togglesearch() {
    this.search = false;
    this.table = true;
    this.fullscreen = false;
    this.back = true;
  }
  backsearch() {
    this.table = true;
    this.search = true;
    this.fullscreen = true;
    this.back = false;
  }

  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
  }
}
