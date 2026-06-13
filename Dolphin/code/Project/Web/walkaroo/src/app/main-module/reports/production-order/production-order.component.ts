import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
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
import { ContainerReceiptService } from '../../Inbound/Container-receipt/container-receipt.service';
import { PickingService } from '../../Outbound/picking/picking.service';
import { ReportsService } from '../reports.service';
import { StocksampleService } from '../stocksamplereport/stocksample.service';
import { ProductionService } from '../../manufacturing/indusFoods/production.service';
import { LinesComponent } from './lines/lines.component';
import { PreparationDetailsComponent } from './preparation-details/preparation-details.component';

@Component({
  selector: 'app-production-order',
  templateUrl: './production-order.component.html',
  styleUrls: ['./production-order.component.scss']
})
export class ProductionOrderComponent implements OnInit {

  screenid = 3225;
  storageselection: any[] = [];
  selectedbinner: any[] = [];
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

  // displayedColumns: string[] = [ 'statusId','itemCode','proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy','confirmedOn','createdOn', 'leadTime'];
  //wms demo
  displayedColumns: string[] = ['statusId', 'itemCode', 'proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy', 'confirmedOn',];
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];

  inboundOrderType: any[] = [];
  constructor(public dialog: MatDialog,
    private cas: CommonApiService,
    private service: StocksampleService,
    private http: HttpClient,
    // private cas: CommonApiService,
    private fb: FormBuilder,
    public toastr: ToastrService,
    private router: Router,
    private spin: NgxSpinnerService,
    public cs: CommonService, private reportService: ReportsService,
    private productionService: ProductionService,
    private pickingService: PickingService,
    public auth: AuthService,
    private containerservice: ContainerReceiptService,
    private masterService: MasterService,) {
    this.multistatusList = [
      {
        label: 'Empty',
        value: 0,
      },
      {
        label: 'Occupied',
        value: 1
      },

    ];

  }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  selectedplant: any[] = [];
  selectedwarehouse: any[] = [];
  ngOnInit(): void {
    this.getDropdown();
    // this.getDropdownLINE();
    this.selectedCompany = this.auth.companyIdAndDescription;
    this.selectedplant = this.auth.plantIdAndDescription;
    this.selectedwarehouse = this.auth.warehouseIdAndDescription;

  }

  multiselectProductionOrderNoList: any[] = [];
  multiselectBatchNumberList: any[] = [];
  multistatusList: any[] = [];
  multiamscycleList: any[] = [];
  getDropdown() {
    this.sub.add(this.productionService.search({ warehouseId: [this.auth.warehouseId], companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], languageId: [this.auth.languageId] }).subscribe(res => {
      res.forEach((x: any) => this.multiselectProductionOrderNoList.push({ value: x.productionOrderNo, label: x.productionOrderNo }));
      res.forEach((x: any) => this.multistatusList.push({ value: x.statusId, label: x.statusDescription }));
      res.forEach((order: any) => {order.productionLines.forEach((line: any) => {
            this.multiselectBatchNumberList.push({ value: line.batchNumber, label: line.batchNumber });
        });
    });
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
  itemCodeChanged(e) {
    console.log(e);
    let selectedArray: any;
    let SelectedArray2: any;
    let splittedValue = e.value.split('/')
    selectedArray = (splittedValue[0])
    SelectedArray2 = (splittedValue[1])
    console.log(selectedArray);
    console.log(SelectedArray2);
    this.searhform.controls.itemCode.patchValue([selectedArray]);
    this.searhform.controls.manufacturerName.patchValue([SelectedArray2]);
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
    endConfirmedOn: [],
    startConfirmedOn: [],
  });


  filtersearch() {
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

    this.sub.add(this.reportService.productionOrderReport(this.searhform.getRawValue()).subscribe(res => {
      this.storageselection = res;

      this.table = true;
      this.search = false;
      this.fullscreen = false;
      this.back = true;
      this.spin.hide();
      //this.totalRecords = this.dataSource.data.length
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

  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator; // Pagination
  // Pagination




  totalRecords = 0;
  downloadexcel() {
    var res: any = [];
    this.storageselection.forEach(x => {
      res.push({
        "Language": x.languageId,
        "Company": x.companyCodeId,
        "Branch": x.plantId,
        "Warehouse": x.warehouseId,
        "Order No": x.productionOrderNo,
        "Item Code": x.itemCode,
        "Batch No": x.batchNumber,
        "Date": this.cs.dateapi(x.Date),
        "Status": x.statusId == 0 ? 'Empty' : 'Occupied',
      });

    })
    this.cs.exportAsExcel(res, "Bin Location Status Report");
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


  onItemSelect(item: any) {
  }

  onSelectAll(items: any) {
  }








  // getBillableAmount() {
  //   let total = 0;
  //   this.dataSource.data.forEach(element => {
  //     total = total + (element.s != null ? element.s : 0);
  //   })
  //   return (Math.round(total * 100) / 100);
  // }

  getToQtyQty() {
    let total = 0;
    this.storageselection.forEach(x => {
      total = total + (x.putAwayQuantity != null ? x.putAwayQuantity : 0)
    })
    return Math.round(total * 100 / 100)
  }
  getBinnerQty() {
    let total = 0;
    this.storageselection.forEach(x => {
      total = total + (x.putawayConfirmedQty != null ? x.putawayConfirmedQty : 0)
    })
    return Math.round(total * 100 / 100)
  }

  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
  }


  lines(res, process) {
    const dialogRef = this.dialog.open(LinesComponent, {
      //width: '55%',
      maxWidth: '90%',
      position: { top: '8.5%' },
      data: { operationLine: res.operationConsumptionReports, processLine: res.process, process: process }
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }


}
