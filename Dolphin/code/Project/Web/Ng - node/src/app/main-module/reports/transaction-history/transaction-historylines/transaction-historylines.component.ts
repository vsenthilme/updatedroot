import { SelectionModel } from '@angular/cdk/collections';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { variant } from '../../new-stock-movement/new-stock-movement.component';
import { ReportsService } from '../../reports.service';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { PutawayService } from 'src/app/main-module/Inbound/putaway/putaway.service';
import { PickingService } from 'src/app/main-module/Outbound/picking/picking.service';
import { PickingDetailsComponent } from '../../new-stock-movement/picking-details/picking-details.component';
import { PutawayDeatilsComponent } from '../../new-stock-movement/putaway-deatils/putaway-deatils.component';

@Component({
  selector: 'app-transaction-historylines',
  templateUrl: './transaction-historylines.component.html',
  styleUrls: ['./transaction-historylines.component.scss']
})
export class TransactionHistorylinesComponent implements OnInit {


  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;

  warehouseList: any[] = [];
  multiselectWarehouseList: any[] = [];
  multiWarehouseList: any[] = [];

  selectedItems: any[] = [];

  itemCode: any
  selectedWarehouse = '';
  fromCreatedOn: any;
  toCreatedOn = new Date();
  sendingFromCreatedOn = '';
  sendingToCreatedOn = '';

  dropdownSettings: IDropdownSettings = {
    singleSelection: true,
    idField: 'item_id',
    textField: 'item_text',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

  div1Function() {
    this.table = !this.table;
  }
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
  showFiller = false;
  displayedColumns: string[] = ['warehouseno', 'countno', 'by', 'preinboundno', 'damage', 'hold', 'opening', 'moveQty', 'balance', 'available', 'stype', 'time'];
  sub = new Subscription();
  ELEMENT_DATA: variant[] = [];
  constructor(
    private router: Router, public toastr: ToastrService, private spin: NgxSpinnerService, private masterService: MasterService, public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private reportService: ReportsService, public datepipe: DatePipe, private auth: AuthService, public cs: CommonService, private putservice: PutawayService, public dialog: MatDialog,
    private pickingservice: PickingService,) { }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  ngOnInit(): void {
    // this.auth.isuserdata();
   // this.dropdownfill();
   this.getStockMovementData();
  }

  dataSource = new MatTableDataSource<any>();
  selection = new SelectionModel<any>(true, []);


  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }

  }

  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.selectedItems[0],this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.description }))
          }
        });
    }
  }
  
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator; // Pagination
  // Pagination






  filtersearch() {
    console.log(this.auth.userTypeId)
    if ((this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0) &&
      (this.itemCode != null && this.itemCode != undefined && this.itemCode.trim() != '')) {
      this.spin.show();
      this.getStockMovementData();
      this.table = true;
      this.search = true;
      this.fullscreen = true;
      this.back = false;
    }
    else {
      this.toastr.error("Please fill the required fields. Required fields are Warehouse No, Item Code", "", {
        timeOut: 2000,
        progressBar: false,
      });
    }
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







  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: variant): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.warehouseno + 1}`;
  }



  clearselection(row: any) {

    this.selection.clear();
    this.selection.toggle(row);
  }

  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err)))

    })
      .subscribe(({ warehouse }) => {
    if(this.auth.userTypeId != 3){
          this.warehouseList = warehouse.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        }else{
          this.warehouseList = warehouse
        }
        this.warehouseList.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId + (x.description == null ? '' : '- ' + x.description) }));
        this.multiselectWarehouseList = this.multiWarehouseList;
        console.log(this.multiselectWarehouseList)
        this.multiselectWarehouseList.forEach((warehouse: any) => {
          if (warehouse.value == this.auth.warehouseId)
            this.selectedItems = [warehouse.value];
        })
      }, (err) => {
        this.toastr.error(
          err,
          ""
        );
      });
    this.spin.hide();

  }
  reset(){
    this.itemCode=null
   
    this.fromCreatedOn=null
    
  }
  getStockMovementData() {
    // if (this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0)
    //   this.selectedWarehouse = this.selectedItems;

   
    let obj: any = {};
    console.log(this.selectedItems)
    obj.warehouseId = [this.auth.warehouseId];
    obj.itemCode = [this.data.itemCode];
  
    this.reportService.getStockMovementDetailsNew(obj).subscribe(
      result => {
        this.spin.hide();
        let obj: any = {};
        obj.itemCode = [this.data.itemCode];
        obj.warehouseId = [this.auth.warehouseId];
        obj.binClassId = [1];
        let inventoryQuantity = 0;
        this.reportService.findInventory(obj).subscribe(
          inventoryResult => {
            inventoryResult.forEach(element => {
              inventoryQuantity = inventoryQuantity + (element.inventoryQuantity != null ? element.inventoryQuantity : 0) + (element.allocatedQuantity != null ? element.allocatedQuantity : 0);
            });
            if (result != null && result.length > 0) {
              //last row data bind 
              let i = result.length;
              result.forEach(element => {
                if (i >= 0) {
                  if (i == result.length) {
                    result[i - 1]['balanceOHQty'] = inventoryQuantity;
                  } else {
                    result[i - 1]['balanceOHQty'] = result[i]['openingStock'];
                  }
                  if (result[i - 1]['documentType'] == 'OutBound') {
                    result[i - 1]['openingStock'] = result[i - 1]['balanceOHQty'] + result[i - 1]['movementQty'];
                  }
                  else {
                    result[i - 1]['openingStock'] = result[i - 1]['balanceOHQty'] - result[i - 1]['movementQty'];
                  }
                  i--;
                }
              });
            } else {
              if (inventoryResult != null && inventoryResult.length > 0) {
                let obj: any = {};
                obj.warehouseId = inventoryResult[0].warehouseId;
                obj.manufacturerSKU = inventoryResult[0].referenceField9;
                obj.itemCode = inventoryResult[0].itemCode;
                obj.itemText = inventoryResult[0].referenceField8;
                obj.documentType = null;
                obj.documentNumber = null;
                obj.openingStock = inventoryQuantity;
                obj.newmMovementQty = 0;
                obj.balanceOHQty = inventoryQuantity;
                obj.customerCode = null;
                obj.confirmedOn = null;

                result.push(obj);
              }
            }
            this.dataSource = new MatTableDataSource(result);

            this.dataSource.data.forEach((x) => {
              if (x.documentType == "OutBound") {
                x['newmMovementQty'] = '-' + (x.movementQty)
              }
              if (x.documentType == "InBound") {
                x['newmMovementQty'] = '+' + (x.movementQty)
              }
            })
            this.dataSource.paginator = this.paginator;
          });
      },
      error => {
        console.log(error);
        this.spin.hide();
      }
    );
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }


  putaway(data): void {


    this.sub.add(this.putservice.searchLine({ itemCode: [data.itemCode], refDocNumber: [data.documentNumber] }).subscribe(res => {
      this.spin.hide();
      let putAwayLineArray: any[] = [];
      let result = res.filter((x: any) => x.deletionIndicator == 0);
      // if (result) {
      //   result.forEach(element => {
      //     if (element.putawayConfirmedQty != null && element.putawayConfirmedQty > 0) {
      //       putAwayLineArray.push(element);
      //     }
      //   });
      // }
      putAwayLineArray.push(result);
      const dialogRef = this.dialog.open(PutawayDeatilsComponent, {
        disableClose: true,
        width: '55%',
        maxWidth: '80%',
        position: { top: '9%', },
        data: putAwayLineArray
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });

      // this.getclient_class(this.form.controls.classId.value);
    }, err => {
      this.cs.commonerror(err);
      this.spin.hide();
    }));

  }
  picking(data): void {


    this.sub.add(this.pickingservice.getpickingline({ itemCode: [data.itemCode], refDocNumber: [data.documentNumber] }).subscribe(res => {
      this.spin.hide();
      let pickingLineArray: any[] = [];
      let result = res.filter((x: any) => x.deletionIndicator == 0);
      // if (result) {
      //   result.forEach(element => {
      //     if (element.putawayConfirmedQty != null && element.putawayConfirmedQty > 0) {
      //       putAwayLineArray.push(element);
      //     }
      //   });
      // }
      pickingLineArray.push(result);
      const dialogRef = this.dialog.open(PickingDetailsComponent, {
        disableClose: true,
        width: '55%',
        maxWidth: '80%',
        position: { top: '9%', },
        data: pickingLineArray
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });

      // this.getclient_class(this.form.controls.classId.value);
    }, err => {
      this.cs.commonerror(err);
      this.spin.hide();
    }));

  }
  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.dataSource.data.forEach(x => {
      res.push({

        "Warehouse": x.warehouseId,
        'MFR Part No': x.manufacturerSKU,
        'Item Code': x.itemCode,
        "Item Description": x.itemText,
        "Document Type": x.documentType,
        "Document Number": x.documentNumber,
        "Opening Qty": x.openingStock,
        'Movement Quantity': x.newmMovementQty,
        'Balance OH Qty': x.balanceOHQty,
        'Customer Code': x.customerCode,
        'Date': this.cs.dateMMYY(x.confirmedOn),
        'Time': this.cs.dateHHSS(x.confirmedOn),
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Stock Movement Report");
  }

}