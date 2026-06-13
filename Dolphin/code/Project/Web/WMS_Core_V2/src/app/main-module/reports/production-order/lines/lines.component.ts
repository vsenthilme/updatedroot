import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from '../../reports.service';
import { PreparationDetailsComponent } from '../preparation-details/preparation-details.component';

@Component({
  selector: 'app-lines',
  templateUrl: './lines.component.html',
  styleUrls: ['./lines.component.scss']
})
export class LinesComponent implements OnInit {

  tableValue: any[] = [];
  // reportService: any;
  searhform: any;
  constructor(public dialog: MatDialog,
    public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    // private service: InboundConfirmationService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private auth: AuthService,
    private reportService: ReportsService,
    private fb: FormBuilder,
    public cs: CommonService,) { }

  ngOnInit(): void {
    this.callTableHeader();
  }

  cols: any[] = []
  callTableHeader() {
    if (this.data.process == 'operationConsumption') {
      this.tableValue = this.data.operationLine;
      this.cols = [
        { field: 'itemCode', header: 'Ingredient' },
        { field: 'itemDescription', header: 'Name' },
        { field: 'receipeQuantity', header: 'Required Qty' },
        { field: 'issuedQuantity', header: 'Issued Qty' },
        { field: 'consumedQuantity', header: 'Consumption Qty' },
        { field: 'loss', header: 'Loss' },
      ];
    } else {
      this.tableValue = this.data.processLine;
      this.cols = [
        { field: 'phaseNumber', header: 'Process ID' },
        { field: 'phaseDescription', header: 'Process Name' },
        { field: 'statusDescription', header: 'Status' },
        { field: 'updatedBy', header: 'Confirmed By' },
        { field: 'updatedOn', header: 'Confirmed On', format: 'date' },
        { field: 'actions', header: 'Details', format: 'extra' },
      ];
    }


  }

  preparationDetails(res) {
    let result;
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.batchNumber = [res.batchNumber];
    obj.productionOrderNo = [res.productionOrderNo];
    obj.phaseNumber = [res.phaseNumber];
    obj.bomItem = [res.bomItem];
    obj.operationNumber = [res.operationNumber];
    
    this.reportService.processReport(obj).subscribe(processResult => {
      if(res){
        if (res.phaseDescription == "Cooking") { result = processResult.cooking[0] };
        if (res.phaseDescription == "Chopping") { result = processResult.diceSliceChop[0]};
        if (res.phaseDescription == "Making paste with water") { result = processResult.paste[0]};
        if (res.phaseDescription == "Peeling") { result = processResult.peeling[0]};
        if (res.phaseDescription == "Powder") { result = processResult.powder[0]};
        if (res.phaseDescription == "Soaking") { result = processResult.soakin[0]};
        if (res.phaseDescription == "Sorting") { result = processResult.sorting[0]};
        if (res.phaseDescription == "Grinding into powder") { result = processResult.powder[0]};

        const dialogRef = this.dialog.open(PreparationDetailsComponent, {
          maxWidth: '73%',
          position: { top: '8.5%' },
          data: {code: result, lines: res}
        });
    
        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
        });
      }
    })

  }
}
