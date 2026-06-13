import { SelectionModel } from "@angular/cdk/collections";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { CommonService, dropdownelement1 } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { OrdermanagementService } from "../../order-management/ordermanagement-main/ordermanagement.service";
import { AssignPickerComponent } from "./assign-picker/assign-picker.component";
import { Table } from "primeng/table";
import { DatePipe } from "@angular/common";

export interface ordermanagement {
  no: string;
  lineno: string;
  partner: string;
  product: string;
  description: string;
  refdocno: string;
  variant: string;
  order: string;
  type: string;
  preoutboundno: string;
  uom: string;
  req: string;
  allocated: string;
  status: string;
  reallocate: string;

}

const ELEMENT_DATA: ordermanagement[] = [
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocate: 'value', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },

];
@Component({
  selector: 'app-assignment-main',
  templateUrl: './assignment-main.component.html',
  styleUrls: ['./assignment-main.component.scss']
})
export class AssignmentMainComponent implements OnInit {
  @ViewChild('assignmentTag') assignmentTag: Table | undefined;
  screenid: 1061 | undefined;
  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  storecodeList: any;
  currentDate = new Date();
  fifteenDaysDate = new Date();
  ondeDaysBeforeDate = new Date();

  toggleFloat() {
    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;
    console.log('show:' + this.showFloatingButtons);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  assign(): void {

    if (this.selectedAssignment.length === 0) {
      this.toastr.error("Kindly select any row", "Norification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    const dialogRef = this.dialog.open(AssignPickerComponent, {
      disableClose: true,
      width: '100%',
      maxWidth: '55%',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        let data: any[] = [];
        debugger
        this.selectedAssignment.forEach((x: any) => {
          data.push({
            itemCode: x.itemCode,
            lineNumber: x.lineNumber,
            partnerCode: x.partnerCode,
            preOutboundNo: x.preOutboundNo,
            proposedPackCode: x.proposedPackBarCode,
            proposedStorageBin: x.proposedStorageBin,
            refDocNumber: x.refDocNumber,
            warehouseId: x.warehouseId,
          })
        });
        this.spin.show();
        this.sub.add(this.service.AssignPicker(data, result).subscribe(res => {
          this.spin.hide();
          this.toastr.success(result + " assign successfully.", "Notification", {
            timeOut: 2000,
            progressBar: false,
          })
          this.search();

        }, err => {

          this.cs.commonerror(err);
          this.spin.hide();

        }));
      }
    });
  }

  assignment: any;
  title1 = "Outbound";
  title2 = "Order Management";
  constructor(private service: OrdermanagementService,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    private auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,
    private datePipe: DatePipe,) { }
  sub = new Subscription();

  ngOnInit(): void {

    this.fifteenDaysDate.setDate(this.currentDate.getDate() + 7);
    this.ondeDaysBeforeDate.setDate(this.currentDate.getDate() - 1);

    // this.searhform.controls.endRequiredDeliveryDate.patchValue(this.fifteenDaysDate);
    // this.searhform.controls.startRequiredDeliveryDate.patchValue(this.ondeDaysBeforeDate);

    this.getstorename();
    this.search(true);
    this.callTableHeader();

  }
  cols: any[] = [];
  callTableHeader() {
    this.cols = [
      { field: 'proposedStorageBin', header: 'Bin Location' },
      { field: 'refDocNumber', header: 'Order No' },
      { field: 'referenceField1', header: 'Order Category' },
      { field: 'outboundOrderTypeId', header: 'Order Type', format: 'extra' },
      { field: 'partnerCode', header: 'Store' },
      { field: 'lineNumber', header: 'Line No' },
      { field: 'itemCode', header: 'Product Code' },
      { field: 'referenceField9', header: 'Storage Section' },
      { field: 'referenceField10', header: 'Level' },
      { field: 'orderQty', header: 'Order Qty' },
      { field: 'allocatedQty', header: 'Allocated Qty' },
      { field: 'requiredDeliveryDate', header: 'Required Date', format: 'date' },
      { field: 'description', header: 'Description' },
      { field: 'referenceField7', header: 'Status' },
    ];
  }


  warehouseId = this.auth.warehouseId
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator!: MatPaginator; // Pagination
  // Pagination

  searhform = this.fb.group({
    endRequiredDeliveryDate: [],
    endoOrderDate: [],
    itemCode: [],
    outboundOrderTypeId: [],
    partnerCode: [],
    preOutboundNo: [],
    refDocNumber: [],
    soType: [],
    startOrderDate: [],
    startRequiredDeliveryDate: [],
    statusId: [[42, 43]],
    warehouseId: [[this.auth.warehouseId]],

  });




  dropdownSettings = {
    singleSelection: false,
    text: "Select",
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2
  };

  itemCodeListselected: any[] = [];
  itemCodeList: any[] = [];

  outboundOrderTypeIdListselected: any[] = [];
  outboundOrderTypeIdList: any[] = [];

  partnerCodeListselected: any[] = [];
  partnerCodeList: any[] = [];

  preOutboundNoselected: any[] = [];
  preOutboundNoList: any[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: any[] = [];

  soTypeListselected: any[] = [];
  soTypeList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];

  getstorename() {
    //this.spin.show();
    this.sub.add(this.service.GetStoreCode().subscribe(res => {
      this.storecodeList = res;
      console.log(this.storecodeList)
      //this.spin.hide();
    },
      err => {
        this.cs.commonerror(err);
        //this.spin.hide();
      }));
  }

  search(ispageload = false) {
    if (!ispageload) {

      //dateconvertion
      this.searhform.controls.endRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endRequiredDeliveryDate.value));
      this.searhform.controls.startRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startRequiredDeliveryDate.value));

    }
    this.spin.show();
    this.service.searchLineNew(this.searhform.value).subscribe(res => {
      //let result = res.filter((x: any) => x.statusId == this.statusId);
      //  let result = res.filter((x: any) => x.statusId == 42 || 43);
      let result = res.filter((x: any) => x.statusId == 42 || x.statusId == 43);


      // result.forEach((x) => {
      //   // x.noteTypeId = this.noteTypeIdlist.find(y => y.key == x.noteTypeId)?.value;
      // x.partnerCode = this.storecodeList.find(y => y.partnerCode == x.partnerCode)?.partnerName;
      // })


      if (ispageload) {
        let tempitemCodeList: any[] = []
        const itemCode = [...new Set(result.map(item => item.itemCode))].filter(x => x != null)
        itemCode.forEach(x => tempitemCodeList.push({ value: x, label: x }));
        this.itemCodeList = tempitemCodeList;

        let tempoutboundOrderTypeIdList: any[] = []
        const outboundOrderTypeId = [...new Set(result.map(item => item.outboundOrderTypeId))].filter(x => x != null)
        outboundOrderTypeId.forEach(x => tempoutboundOrderTypeIdList.push({ value: x, label: this.cs.getoutboundOrderType_text(x) }));
        this.outboundOrderTypeIdList = tempoutboundOrderTypeIdList;

        let temppartnerCodeList: any[] = []
        const partnerCode = [...new Set(result.map(item => item.partnerCode))].filter(x => x != null)
        partnerCode.forEach(x => temppartnerCodeList.push({ value: x, label: x }));
        this.partnerCodeList = temppartnerCodeList;

        let temppreOutboundNoList: any[] = []
        const preOutboundNo = [...new Set(result.map(item => item.preOutboundNo))].filter(x => x != null)
        preOutboundNo.forEach(x => temppreOutboundNoList.push({ value: x, label: x }));
        this.preOutboundNoList = temppreOutboundNoList;


        let temprefDocNumberList: any[] = []
        const refDocNumber = [...new Set(result.map(item => item.refDocNumber))].filter(x => x != null)
        refDocNumber.forEach(x => temprefDocNumberList.push({ value: x, label: x }));
        this.refDocNumberList = temprefDocNumberList;

        let tempsoTypeList: any[] = []
        const soType = [...new Set(result.map(item => item.referenceField1))].filter(x => x != null)
        soType.forEach(x => tempsoTypeList.push({ value: x, label: x }));
        this.soTypeList = tempsoTypeList;

        // let tempstatusIdList: any[] = []
        // const statusId = [...new Set(result.map(item => item.statusId))].filter(x => x != null)
        // statusId.forEach(x => tempstatusIdList.push({ value: x, label: this.cs.getstatus_text(x) }));
        // this.statusIdList = tempstatusIdList;

        res.forEach(x => this.statusIdList.push({ value: x.statusId, label: x.referenceField7 }));
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList)
      }
      this.assignment = result;
      this.dataSource = new MatTableDataSource<any>(result);
      this.selection = new SelectionModel<any>(true, []);
      this.dataSource.filterPredicate = this.customFilterPredicate();
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.spin.hide();
    }, err => {

      this.cs.commonerror(err);
      this.spin.hide();

    });


  }
  reload() {
    this.searhform.reset();
  }


  displayedColumns: string[] = ['select', 'proposedStorageBin', 'refDocNumber', 'referenceField1', 'outboundOrderTypeId', 'partnerCode', 'lineNumber', 'itemCode', 'referenceField9', 'referenceField10', 'orderQty', 'allocatedQty', 'requiredDeliveryDate', 'description', 'referenceField7',];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);

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

    this.selection.select(...this.dataSource.filteredData);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.no + 1}`;
  }
  clearselection(row: any) {
    this.selection.clear();
    this.selection.toggle(row);
  }


  downloadexcel() {
    const exportData = this.assignment.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format !== 'date' && col.format !== 'extra') {
          exportItem[col.header] = item[col.field];
        } else {
          if (col.format == 'date') {
            exportItem[col.header] = this.datePipe.transform(item[col.field], 'dd-MM-yyyy');
          }
          if (col.field == 'statusId') {
            exportItem[col.header] = this.cs.getstatus_text(item[col.field]);
          }
          if (col.field == 'outboundOrderTypeId') {
            exportItem[col.header] = this.cs.getoutboundOrderType_text(item[col.field]);
          }
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, 'Assignment');
  }


  onItemSelect(item: any) {
    console.log(item);
  }

  OnItemDeSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onDeSelectAll(items: any) {
    console.log(items);
  }

  searchrefDocNumber: any;
  searchitemCode: any;
  searchpartnerCode: any;
  referenceField9: any;
  referenceField10: any;
  outboundOrderTypeId1: any;
  orderCategory: any;
  binLocation: any;

  filterValues = {
    refDocNumber: '',
    itemCode: '',
    partnerCode: '',
    referenceField9: '',
    referenceField10: '',
    outboundOrderTypeId: '',
    referenceField1: '',
    proposedStorageBin: '',
  };

  searchrefDocNumberMethod(event) {
    this.filterValues.refDocNumber = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchitemCodeMethod(event) {
    this.filterValues.itemCode = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchpartnerCodeMethod(event) {
    this.filterValues.partnerCode = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchStorageMethod(event) {
    this.filterValues.referenceField9 = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchLevelMethod(event) {
    this.filterValues.referenceField10 = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
    console.log(this.filterValues)
    console.log(this.dataSource.filter)
  }
  searchoutboundOrderTypeIdMethod(event) {
    this.filterValues.outboundOrderTypeId = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchorderCatMethod(event) {
    this.filterValues.referenceField1 = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }
  searchbinLocation(event) {
    this.filterValues.proposedStorageBin = event;
    this.dataSource.filter = JSON.stringify(this.filterValues);
  }

  customFilterPredicate() {
    const myFilterPredicate = function (data: any, filter: string): boolean {
      let searchString = JSON.parse(filter);
      return data.proposedStorageBin.toString().trim().toLowerCase().indexOf(searchString.proposedStorageBin.toLowerCase()) !== -1
        && data.refDocNumber.toString().trim().toLowerCase().indexOf(searchString.refDocNumber.toLowerCase()) !== -1
        && data.outboundOrderTypeId.toString().trim().toLowerCase().indexOf(searchString.outboundOrderTypeId.toLowerCase()) !== -1
        && data.itemCode.toString().trim().toLowerCase().indexOf(searchString.itemCode.toLowerCase()) !== -1
        && data.partnerCode.toString().trim().toLowerCase().indexOf(searchString.partnerCode.toLowerCase()) !== -1
        && data.referenceField1.toString().trim().toLowerCase().indexOf(searchString.referenceField1.toLowerCase()) !== -1;

    }
    return myFilterPredicate;
  }

  selectedAssignment: any[] = [];
  onChange() {
    const choosen = this.selectedAssignment[this.selectedAssignment.length - 1];
    this.selectedAssignment.length = 0;
    this.selectedAssignment.push(choosen);
  }

}
