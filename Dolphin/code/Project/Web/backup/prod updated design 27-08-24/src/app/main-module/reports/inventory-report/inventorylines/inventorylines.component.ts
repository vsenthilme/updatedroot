import { SelectionModel } from '@angular/cdk/collections';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { OrdermanagementService } from 'src/app/main-module/Outbound/order-management/ordermanagement-main/ordermanagement.service';

@Component({
  selector: 'app-inventorylines',
  templateUrl: './inventorylines.component.html',
  styleUrls: ['./inventorylines.component.scss']
})
export class InventorylinesComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private service: OrdermanagementService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,
  ) { }
  sub = new Subscription();
  ngOnInit(): void {
    
    console.log(this.data)
    let obj: any = {};
    obj.languageId=[this.auth.languageId];
    obj.companyCodeId=[this.auth.companyId];
    obj.warehouseId=[this.auth.warehouseId];
    obj.plantId=[this.auth.plantId];
    obj.itemCode=[this.data.itemCode];
    this.sub.add(this.service.searchLine(obj).subscribe(res => {
      this.dataSource = new MatTableDataSource<any>(res);

      this.selection = new SelectionModel<any>(true, []);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }))
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  
  ELEMENT_DATA: any[] = [];
  displayedColumns: string[] = [ 'referenceField7', 'refDocNumber', 'partnerCode', 'outboundOrderTypeId', 'lineNumber', 'itemCode', 'orderQty', 'inventoryQty', 'allocatedQty', 'requiredDeliveryDate', 'preOutboundNo', 'proposedPackBarCode', 'proposedStorageBin', 'description',];
  dataSource = new MatTableDataSource < any > (this.ELEMENT_DATA);
  selection = new SelectionModel < any > (true, []);


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row ? : any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.invoiceNumber + 1}`;
  }

  clearselection(row: any) {
    this.selection.clear();
    this.selection.toggle(row);
  }


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  submit(){
    if (this.selection.selected.length === 0) {
      this.toastr.warning("Kindly select any Row", "Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    this.dialogRef.close(this.selection.selected[0].customerCode);
  }
  

}
