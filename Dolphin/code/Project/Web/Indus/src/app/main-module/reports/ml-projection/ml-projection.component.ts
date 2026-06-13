import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { InventoryLinesComponent } from '../inventory-report/inventory-lines/inventory-lines.component';
import { InventoryPutwayComponent } from '../inventory-report/inventory-putway/inventory-putway.component';
import { ReportsService } from '../reports.service';
import { stockElement, StocksampleService } from '../stocksamplereport/stocksample.service';
import { isConstructorDeclaration } from 'typescript';

@Component({
  selector: 'app-ml-projection',
  templateUrl: './ml-projection.component.html',
  styleUrls: ['./ml-projection.component.scss']
})
export class MlProjectionComponent implements OnInit {
  screenid = 3164;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  selectedCompany: any[] = [];
  warehouseList: any[] = [];
  itemCodeList: any[] = [];
  selectedplant: any[] = [];
  selectedWarehouseList: any[] = [];
  selectedItemCode = null;

  sendingWarehouseList: any[] = [];

  selectedItems: any[] = [];

  multiselectWarehouseList: any[] = [];
  multiselectItemCodeList: any[] = [];

  multiWarehouseList: any[] = [];

  dropdownSettings: IDropdownSettings = {
    singleSelection: false,
    idField: 'item_id',
    textField: 'item_text',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };
  inventory: any[] = [];
  inventorynew: any[] = [];
  selectedbinner: any[] = [];
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;
  displayedColumns: string[] = ['plantDescription', 'itemCode', 'referenceField8', 'manufacturerName', 'storageBin', 'referenceField10', 'barcodeId', 'inventoryQuantity', 'allocatedQuantity', 'total', 'stockTypeId'];
  sub = new Subscription();
  ELEMENT_DATA: stockElement[] = [];
  multibinclassId: any[] = [];
  pageSize = 500;
  pageNumber = 0;
  totalRecords = 0;
  storageBinCode: any = null;
  barcodeId: any = null;
  itemCode: any = null;

  nextFourWeeks: any;
  currentWeek: any;
  selectedValue: any;

  constructor(public dialog: MatDialog,
    private service: StocksampleService,
    // private cas: CommonApiService,
    public toastr: ToastrService,
    private router: Router,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
    private cs: CommonService,
    private masterService: MasterService,
    private reportService: ReportsService,
    public auth: AuthService) {
    this.multibinclassId = [{
      label: '1-LIVELOCATION',
      value: 1
    },
    {
      label: '2-RESERVE',
      value: 2
    },
    {
      label: '3-RECEVING STAGING',
      value: 3
    },
    {
      label: '7-RETURNBIN',
      value: 7
    },

    ];
    const currentDate = new Date();
    const currentMonth = currentDate.toLocaleString('default', { month: 'long' });
    const currentDayOfMonth = currentDate.getDate();
    const currentWeekNumber = Math.ceil(currentDayOfMonth / 7);
    this.currentWeek = `${currentMonth} ${this.getOrdinal(currentWeekNumber)} week`;

    this.nextFourWeeks = [];
    for (let i = 0; i < 4; i++) {
      const nextWeekDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDayOfMonth + (i * 7) + 7);
      const nextWeekNumber = Math.ceil(nextWeekDate.getDate() / 7);
      const nextWeekMonth = nextWeekDate.toLocaleString('default', { month: 'long' });
      //this.nextFourWeeks.push({ value: (i + 1), label: `${nextWeekMonth} ${this.getOrdinal(nextWeekNumber)} week` });
      this.nextFourWeeks.push(`${nextWeekMonth} ${this.getOrdinal(nextWeekNumber)} week`);
    }

  }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  loading: boolean;
  sourceImage: any;
  getOrdinal(n: number) {
    const suffixes = ["rd", "st", "nd",];
    const v = n % 100;
    return n + (suffixes[(v - 20) % 10] || suffixes[v] || suffixes[0]);
  }

  ngOnInit(): void {

    this.loading = false;
    this.sourceImage = './assets/img/icons/' + this.searhform.controls.selectedDateRange.value + '.jpg';
    this.searhform.controls.selectedDescription.patchValue(this.nextFourWeeks[0]);

    this.dropdownfill();
    this.selectedCompany = this.auth.companyId;
    this.selectedplant = this.auth.plantId;

    this.searhform.controls.selectedDateRange.valueChanges.subscribe(x => {
        this.searhform.controls.selectedDescription.patchValue(this.nextFourWeeks[x-1 ]);
       })
  }
  div1Function() {
    this.table = !this.table;
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
  putaway(res) {
    if (res.storageBin == "RECEIPTSTAGING") {
      
      const dialogRef = this.dialog.open(InventoryPutwayComponent, {
        width: '72%',
        maxWidth: '90%',
        height: '55%',
        position: { top: '3.5%' },
        data: res
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        // this.animal = result;
      });
    }
    else {
      console.log('Not a value')
    }
  }
  lines(res) {
    
    const dialogRef = this.dialog.open(InventoryLinesComponent, {
      //width: '55%',
      maxWidth: '90%',
      position: { top: '8.5%' },
      data: res
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator; // Pagination
  // Pagination

  totalQty = 0;

  nonZeroTotal: any[] = [];
  nonZeroExcel: any[] = [];


  storageSectionId: any[] = [];
  filtersearch(excel = false) {

    this.searhform.controls.storageBin.patchValue([this.searhform.controls.storageBinFE.value]);
    if (this.searhform.controls.barcodeIdFE.value != null) {
      this.searhform.controls.barcodeId.patchValue([this.searhform.controls.barcodeIdFE.value]);
    }
    this.searhform.controls.storageSectionId.patchValue(this.searhform.controls.storageSectionIdFE.value);



    if (this.searhform.controls.storageBinFE.value == null) {
      this.searhform.controls.storageBin.patchValue(this.searhform.controls.storageBinFE.value);
    }
    if (this.searhform.controls.barcodeIdFE.value == null) {
      this.searhform.controls.barcodeId.patchValue(this.searhform.controls.barcodeIdFE.value);
    }
    if (this.searhform.controls.storageSectionIdFE.value == null) {
      this.searhform.controls.storageSectionId.patchValue(this.searhform.controls.storageSectionIdFE.value);
    }
    this.totalQty = 0;

    if (!excel) {
      this.inventory = [];
      this.nonZeroTotal = []
      this.sub.add(this.service.searchNew(this.searhform.getRawValue()).subscribe((res: any) => {
        res.forEach(element => {
          this.nonZeroTotal = res;
          this.totalQty += element.inventoryQuantity;
          if(this.searhform.controls.selectedDateRange.value == 1){
            element.projectedQty = element.inventoryQuantity + 20;
          }
          if(this.searhform.controls.selectedDateRange.value == 2){
            element.projectedQty = element.inventoryQuantity + 40;
          }
          if(this.searhform.controls.selectedDateRange.value == 3){
            element.projectedQty = element.inventoryQuantity + 80;
          }
          if(this.searhform.controls.selectedDateRange.value == 4){
            element.projectedQty = element.inventoryQuantity + 120;
          }
        });

        this.spin.show();
        setTimeout(() => {
          this.spin.hide();
          this.search = false;
          this.loading = true;

          setTimeout(() => {
            this.sourceImage = './assets/img/icons/' + this.searhform.controls.selectedDateRange.value + '.jpg';
            this.loading = false;
  
            this.spin.show();
            setTimeout(() => {
              this.callTable();
            }, 2000);
            
          }, 6000);

          
        }, 2000);


   

      }, err => {
        this.cs.commonerrorNew(err);
      }));
    } else {
      this.downloadexcel(this.searhform.getRawValue());
    }
  }

  callTable() {
    this.inventory = this.nonZeroTotal;
    this.inventorynew = this.inventory
    this.calculateTotals(this.inventory);
    this.table = true;
    this.search = false;
    this.fullscreen = false;
    this.back = true;    
    this.spin.hide();
  }

  searhform = this.fb.group({
    companyCodeId: [[this.auth.companyId],],
    plantId: [[this.auth.plantId],],
    languageId: [[this.auth.languageId],],
    warehouseId: [[this.auth.warehouseId],],
    itemCode: [],
    itemCodeFE: [],
    binClassId: [],
    storageBin: [],
    selectedDescription: [],
    storageBinFE: [],
    selectedDateRange: [1,],
    manufacturerName: [],
    storageSectionId: [],
    storageSectionIdFE: [],
    barcodeId: [],
    barcodeIdFE: [],
  });



  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    this.searhform.controls.barcodeId.patchValue([""]);
    this.totalQty = 0;
  }
  downloadexcel(obj: any) {
    this.nonZeroExcel = [];
    this.spin.show();
    this.sub.add(this.service.searchNew(obj).subscribe((data: any) => {


      data.forEach(element => {
        this.totalQty += element.inventoryQuantity;
      });
      data.forEach(element => {
        if (element.inventoryQuantity < 0) {
          element.inventoryQuantity = 0;
        }
      });

      //total
      let total = 0;
      data.forEach(element => {
        total = element.inventoryQuantity + element.allocatedQuantity;

        element.total = total

        if (element.stockTypeId == 1) {
          element.stockTypeId1 = "On Hand"
        }
        if (element.stockTypeId == 7) {
          element.stockTypeId1 = "Hold"
        }
      });
      data.forEach(element => {
        if (element.total > 0) {
          this.nonZeroExcel.push(element)
        }
      });

      this.spin.hide();
      if (this.nonZeroExcel.length > 0) {
        this.nonZeroExcel.forEach(x => {
          res.push({

            "Plant": x.plantDescription,
            "Warehouse": x.warehouseDescription,
            'Mfr Name': x.manufacturerName,
            'Part No': x.itemCode,
            "Description": x.referenceField8,
            "Level Id": x.levelId,
            "Storage Bin": x.storageBin,
            'Barcode': x.barcodeId,
            'Inv Qty': x.inventoryQuantity,
            'Alloc Qty': x.allocatedQuantity,
            'Total': x.total,
            "Stock Type": x.stockTypeDescription,
            // 'Status': x.statusId,
            'Date': this.cs.dateapiwithTime(x.createdOn),
            'Storage Section': x.referenceField10,
            "Company": x.companyDescription,
          });
        })
        res.push({
          "Company": '',
          "Plant": '',
          "Warehouse": '',
          'Mfr Name': '',
          'Part No': '',
          "Description": '',
          'Mfr Code': '',
          "Level Id": '',
          "Storage Bin": '',
          'Storage Section': '',
          'Barcode': '',
          'Inv Qty': this.getInvQty(),
          'Alloc Qty': this.getAllocQty(),
          'Total': this.getTotal(),
          "Stock Type": '',
          // 'Status': x.statusId,
          'Date': '',
        });
        this.cs.exportAsExcel(res, "ML Based Inventory Projection Report");
      } else {
        this.toastr.error(
          "No data present",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
      }

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
    var res: any = [];

  }

  pageHandler($event: PageEvent) {
    this.pageNumber = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.filtersearch(false);
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
  multiSelectStorageSectionID1: any[] = [];
  multiSelectStorageSectionID: any[] = [];
  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
      storageSection: this.masterService.getStorageSectionMasterDetails().pipe(catchError(err => of(err)))
    })
      .subscribe(({ warehouse, storageSection }) => {
        //   .subscribe(({ warehouse, itemCode, storageSection }) => {
        if (this.auth.userTypeId != 3) {
          this.warehouseList = warehouse.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        } else {
          this.warehouseList = warehouse
        }
        this.warehouseList.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId + (x.description == null ? '' : '- ' + x.description) }));
        this.multiselectWarehouseList = this.multiWarehouseList;
        this.multiselectWarehouseList.forEach((warehouse: any) => {
          if (warehouse.value == this.auth.warehouseId)
            this.selectedItems = [warehouse.value];
        })
        this.multiSelectStorageSectionID1 = storageSection.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        this.multiSelectStorageSectionID1.forEach(x => this.multiSelectStorageSectionID.push({ value: x.storageSectionId, label: x.storageSectionId }));
        this.multiSelectStorageSectionID = this.cs.removeDuplicatesFromArrayNewstatus(this.multiSelectStorageSectionID)
        this.multiselectWarehouseList = this.cs.removeDuplicatesFromArrayNewstatus(this.multiselectWarehouseList)
      }, (err) => {
        this.toastr.error(
          err,
          ""
        );
      });
    this.spin.hide();

  }

  onItemType(searchKey) {
    console.log(2)
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode + '/' + x.manufacturerName, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description })); //+x.manufacturerName
          }
        });
    }
  }

  selectItemCode1(e) {
    let selectedArray: any = [];
    let SelectedArray1: any = [];
    e.value.forEach(x => {
      let splittedValue = x.split('/')
      selectedArray.push(splittedValue[0])
      SelectedArray1.push(splittedValue[1]);
    })
    this.searhform.controls.itemCode.patchValue(selectedArray);
    this.searhform.controls.manufacturerName.patchValue(SelectedArray1);
  }

  multiselectStorageList: any[] = [];
  storageBinList1: any[] = [];
  selectedStorageBin: any[] = [];
  onStorageType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        storageList: this.reportService.getStorageDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ storageList }) => {
          if (storageList != null && storageList.length > 0) {
            this.multiselectStorageList = [];
            this.storageBinList1 = storageList;
            this.storageBinList1.forEach(x => this.multiselectStorageList.push({ value: x.storageBin, label: x.storageBin }))
          }
        });
    }
  }

  onSelectAll(items: any) {
  }

  /** Whether the number of selected elements matches the total number of rows. */







  // getBillableAmount() {
  //   let total = 0;
  //   this.dataSource.data.forEach(element => {
  //     total = total + (element.s != null ? element.s : 0);
  //   })
  //   return (Math.round(total * 100) / 100);
  // }


  async downloadAll() {
    const blob = await this.reportService.getInventoryFile()
      .catch((err: HttpErrorResponse) => {
        this.cs.commonerrorNew(err);
      });
    if (blob) {

      var downloadURL = window.URL.createObjectURL(blob);
      var link = document.createElement('a');
      link.href = downloadURL;
      link.download = "Inventory Report.xlsx";
      link.click();
    }
  }

  getTotal(data: any[] = this.inventory) {
    let total = 0;
    data.forEach(x => {
      total = total + (x.total != null ? x.total : 0)
    })
    return Math.round(total * 100 / 100)
  }

  getAllocQty() {
    let total = 0;
    this.inventory.forEach(x => {
      total = total + (x.allocatedQuantity != null ? x.allocatedQuantity : 0)
    })
    return Math.round(total * 100 / 100)
  }
  getInvQty() {
    let total = 0;
    this.inventory.forEach(x => {
      total = total + (x.inventoryQuantity != null ? x.inventoryQuantity : 0)
    })
    return Math.round(total * 100 / 100)
  }
  @ViewChild('binnerTag') binnerTag: Table | any;


  // handleSearch(filterValue: any, field: any): void {
  //   if (filterValue == null) {
  //     this.inventory = this.inventory;
  //     this.calculateTotals();
  //   }
  //   else {
  //     this.filteredInventory = this.inventorynew.filter(item => item[field].includes(filterValue));
  //     this.calculateTotals(this.filteredInventory);
  //     this.inventory = this.filteredInventory;
  //   }
  //   this.inventory = this.inventory;
  // }

  handleSearch1(filterInput) {
    if (filterInput) {
      this.calculateTotals(this.binnerTag.filteredValue);
    }
    else {
      this.calculateTotals(this.inventory);

    }
  }

  filteredInventory: any[] = []; // Filtered inventory data array, if applicable
  totalInventoryQuantity: number = 0; // Total inventory quantity
  totalAllocatedQuantity: number = 0; // Total allocated quantity
  totalQty1: number = 0;
  calculateTotals(data?: any[]): void {
    this.totalInventoryQuantity = 0;
    this.totalAllocatedQuantity = 0;
    this.totalQty1 = 0;
    if (data && data.length > 0) {
      this.totalInventoryQuantity = data.reduce((total, item) => total + (item.inventoryQuantity != null ? item.inventoryQuantity : 0), 0);
      this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.allocatedQuantity != null ? item.allocatedQuantity : 0), 0);
      this.totalQty1 = data.reduce((total, item) => total + (item.total != null ? item.total : 0), 0);
    } else {
      this.inventory.forEach(x => {
        this.totalInventoryQuantity = 0;
        this.totalAllocatedQuantity = 0;
        this.totalQty1 = 0;
      })

      // }

    }
  }
}
