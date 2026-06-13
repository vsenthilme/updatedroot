import { SelectionModel } from '@angular/cdk/collections';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ChecklistNewComponent } from 'src/app/main-module/setting/business/document-checklist/checklist-new/checklist-new.component';
import { documentTemplateService } from 'src/app/main-module/setting/business/document-template/document-template.service';


@Component({
  selector: 'app-custom-checklist',
  templateUrl: './custom-checklist.component.html',
  styleUrls: ['./custom-checklist.component.scss']
})
export class CustomChecklistComponent implements OnInit {



  constructor(
    public dialogRef1: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public fb: FormBuilder,
    private cas: CommonApiService,
    private auth: AuthService,
    public dialog: MatDialog,
    public servicde: documentTemplateService
  ) { }

  ngOnInit(){
 this.getChecklist();
  }
  displayedColumns: string[] = ['select', 'uploadDate', 'partner', 'documentUrl', 'statusId', 'download'];
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

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
  }

  getChecklist(){
    console.log(this.data)
    let obj: any = {};
    obj.classId = [this.data.classId];
    obj.caseCategoryId = [36];
    obj.caseSubCategoryId = [633];
    
    this.servicde.Search(obj).subscribe(res => {
      this.dataSource = new MatTableDataSource<any>(res);
    })
  }


  saveChecklist(){
    this.selection.selected.forEach(x => {
      x.caseCategoryId = this.data.caseCategory;
      x.caseSubCategoryId = this.data.caseSubCategory;
      x.checkListNo = this.data.checkListNo;
      x.statusId = 22;
    })
    this.dialogRef1.close(this.selection.selected);
  }


  createNewChecklist(){
    let obj: any = {};
    obj.classId = [this.data.classId];
    obj.caseCategoryId = [36];
    obj.caseSubCategoryId = [633];
    
    this.servicde.Search(obj).subscribe(res =>{
      const dialogRef = this.dialog.open(ChecklistNewComponent, {
        disableClose: true,
        width: '50%',
        maxWidth: '80%',
        position: { top: '6.5%' },
        data: { type: 'EditfromMatter', documentData: res[0] }
      });
  
      dialogRef.afterClosed().subscribe(result => {
        this.getChecklist();
      });
    })
  }
}
