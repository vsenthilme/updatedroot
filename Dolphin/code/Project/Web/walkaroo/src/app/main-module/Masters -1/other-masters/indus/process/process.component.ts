import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BOMService } from '../../bom/bom.service';
import { ProcessNewComponent } from './process-new/process-new.component';

@Component({
  selector: 'app-process',
  templateUrl: './process.component.html',
  styleUrls: ['./process.component.scss']
})
export class ProcessComponent implements OnInit {

  screenid=3097;
  advanceFilterShow: boolean;
  @ViewChild('Setuplan') Setuplan: Table | undefined;
  masterPhase: any;
  selectedmasterPhase : any;

  displayedColumns: string[] = ['select','phaseNumber','phaseDescription', 'createdBy','createdOn'];
  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  ELEMENT_DATA: any[] = [];
  dataSource = new MatTableDataSource<any>(this.ELEMENT_DATA);
  selection = new SelectionModel<any>(true, []);
  
  constructor(public dialog: MatDialog,
   // private cas: CommonApiService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
   // private excel: ExcelService,
    private fb: FormBuilder,
    private cas: CommonApiService,
    private auth: AuthService,
    private service: BOMService) { }
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
    animal: string | undefined;
    applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;

  
      this.dataSource.filter = filterValue.trim().toLowerCase();
  
      if (this.dataSource.paginator) {
        this.dataSource.paginator.firstPage();
      }
    }
    applyFilterGlobal($event: any, stringVal: any) {
      this.Setuplan!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
    }
    RA: any = {};
    ngOnInit(): void {
      this.RA = this.auth.getRoleAccess(this.screenid);
      this.getAll(

      );
      //this.getallfilter();
    }

    searhform = this.fb.group({
      phaseNumber: [],
      startCreatedOn:[],
      endCreatedOn:[],
    });
  
  
    reload() {
      window.location.reload();
    }
  
    @ViewChild(MatSort, { static: true })
    sort!: MatSort;
    @ViewChild(MatPaginator, { static: true })
    paginator!: MatPaginator; // Pagination
   // Pagination
    warehouseId = this.auth.warehouseId;
  
  
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
      return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.handlingUnit + 1}`;
    }
  
    search() {
      this.spin.show();
      this.sub.add(this.service.searchProcess(this.searhform.getRawValue()).subscribe((res: any[]) => {
        console.log(res);
        this.dataSource = new MatTableDataSource < any > (res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        
        this.spin.hide();
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
    }
  
    reset() {
      this.searhform.reset();
    }
  
  
    clearselection(row: any) {
  
      this.selection.clear();
      this.selection.toggle(row);
    }
  
  openDialog(data: any = 'New'): void {
    console.log(this.selectedmasterPhase)
    if (data != 'New' && this.selectedmasterPhase.length === 0){
      this.toastr.warning("Kindly select any Row", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    const dialogRef = this.dialog.open(ProcessNewComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: { pageflow: data, code: data != 'New' ? this.selectedmasterPhase[0].phaseNumber : null}
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.getAll();
    });
  }
  getAll() {
    if(this.auth.userTypeId == 1){
      this.superAdmin()
    }else{
      this.adminUser()
    }
  }
  
  adminUser(){
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    this.spin.show();
    this.sub.add(this.service.searchProcess(obj).subscribe((res: any[]) => {
      console.log(res)
if(res){
   this.masterPhase = res;

}
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  superAdmin(){
    this.spin.show();
    this.sub.add(this.service.GetallProcess().subscribe((res: any[]) => {
      if(res){
        this.masterPhase=res;
       } this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  deleteDialog() {
    if (this.selectedmasterPhase.length === 0) {
      this.toastr.error("Kindly select any row", "Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: this.selectedmasterPhase[0].phaseNumber,
    });
  
    dialogRef.afterClosed().subscribe(result => {
  
      if (result) {
        this.deleterecord(this.selectedmasterPhase[0].phaseNumber);
  
      }
    });
  }
  multilanguageList: any[] = [];
  multiyseridList: any[] = [];
  multiselectyseridList: any[] = [];

  getallfilter() {
    this.multilanguageList = [];
    let obj: any = {};
    obj.languageId=[this.auth.languageId];
    this.spin.show();
    this.sub.add(this.service.searchProcess(obj).subscribe((res: any[]) => {
         this.dataSource = new MatTableDataSource < any >(res);
        this.spin.hide();
        res.forEach((x: { languageId: string; languageDescription: string, }) => {
            this.multilanguageList.push({
              value: x.languageId,
              label: x.languageId + '-' + x.languageDescription,
            });
        });
        this.multilanguageList = this.cas.removeDuplicatesFromArrayNew(this.multilanguageList);
        res.forEach((x: {
          createdBy: string;
        }) => this.multiyseridList.push({
          value: x.createdBy,
          label: x.createdBy
        }))
        this.multiselectyseridList = this.multiyseridList;
        this.multiselectyseridList = this.cas.removeDuplicatesFromArrayNew(this.multiyseridList);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
      , err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
  } 
  
  deleterecord(id: any) {
    this.spin.show();
    this.sub.add(this.service.DeleteProcess(id).subscribe((res) => {
      this.toastr.success(id + " Deleted successfully.","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      this.getAll();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  downloadexcel() {
    var res: any = [];
    this.masterPhase.forEach(x => {
      res.push({
       "Phase No":x.phaseNumber,
       "Description":x.phaseDescription,
       "Created By":x.createdBy,
       "Created On":this.cs.dateapi(x.createdOn),
      
      });
  
    })
    this.cs.exportAsExcel(res, "Master Phase");
  }
  Cancel() {
    this.reset();
    this.dataSource = new MatTableDataSource < any > (this.ELEMENT_DATA);
    this.selection = new SelectionModel < any > (true, []);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  
  onChange() {
    console.log(this.selectedmasterPhase.length)
    const choosen= this.selectedmasterPhase[this.selectedmasterPhase.length - 1];   
    this.selectedmasterPhase.length = 0;
    this.selectedmasterPhase.push(choosen);
  } 
  }
   
  


