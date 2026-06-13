import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { ExcelService } from 'src/app/common-service/excel.service';
import { AuthService } from 'src/app/core/core';
import { MatterRateService } from '../rate-list/matter-rate.service';
import { Subscription } from 'rxjs';
import { GeneralMatterService } from '../General/general-matter.service';
import { TimeNewComponent } from '../time-tickets/time-new/time-new.component';

@Component({
  selector: 'app-shortcuts',
  templateUrl: './shortcuts.component.html',
  styleUrls: ['./shortcuts.component.scss']
})
export class ShortcutsComponent implements OnInit {

  screenid = 1108;
  public icon = 'expand_more';
  searhform = this.fb.group({
    clientId: [],
    clientId1:[],
    classId1:[],
    caseCategoryId: [],
    caseInformationNo: [],
    fromDate: [],
    endDate: [],
    matterDescription: [],
    matterNumber: [],
    statusId: [,],
    fromDateString: [,],
    endDateString: [,],
    classId: [],
    fromDateFE: [,],
    endDateFE: [,],
  });
  displayedColumns: string[] = ['select', 'timeKeeperCode', 'name', 'defaultRatePerHour', 'assignedRatePerHour', 'createdBy', 'createdOn', 'statusIddes',];

  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  ClientFilter: any;
  matterdesc: any;
  timeKeeperCodelist1: any;
  code: any;
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
  id: string | undefined;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
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
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.timeKeeperCode + 1}`;
  }
    clearselection(row: any) {
    if (!this.selection.isSelected(row)) {
      this.selection.clear();
    }
    this.selection.toggle(row);
  }
  ELEMENT_DATA: any[] = [];
  // displayedColumns: string[] = ['select', 'taskno', 'type', 'creation', 'deadline', 'remainder', 'originatting', 'responsible', 'legal', 'status',];
  sub = new Subscription();
  dataSource = new MatTableDataSource<any>(this.ELEMENT_DATA);
  selection = new SelectionModel<any>(true, []);

  constructor(public dialog: MatDialog,
    private service: MatterRateService, private router: Router,
    public toastr: ToastrService, private route: ActivatedRoute,
    private spin: NgxSpinnerService,
    private cs: CommonService,
    private cas: CommonApiService,
    private excel: ExcelService,
    private matterRate: MatterRateService,
    private fb: FormBuilder,
    private matterservice:GeneralMatterService,
    private auth: AuthService) { }
  matterno: any = "";
  RA: any = {};
  ngOnInit(): void {
    this.search();
  }
  shortcutsList:any[]=[];
  search() {
    let obj: any = {};
    //obj.classId=[this.auth.classId];
    obj.createdBy=[this.auth.userId];
    obj.favourites=false;
    obj.number=20;
    this.spin.show();
    this.sub.add(this.service.SearchRecentMatter(obj).subscribe((res: any[]) => {
      this.shortcutsList=res;
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }
  openDialog(data: any = 'new'): void {

    this.sub.add(this.matterRate.getMatterRateByMatterNumber(data.matterNumber).subscribe((res) => {
      console.log(res);
      this.searhform.controls.clientId1.patchValue(res[0].clientId);
      this.searhform.controls.classId1.patchValue(res[0].classId);
      let timeKeeperCodeList: any[] = []
      res.forEach(element => {
        timeKeeperCodeList.push(element.timeKeeperCode)
      });
      let unMmatchedList: any[] = [];
      timeKeeperCodeList.forEach(x => {
        if (x != this.auth.userId && this.auth.userTypeId != 7) {
          unMmatchedList.push(x)
        }
      })
      
    
   //  if(timeKeeperCodeList.length == unMmatchedList.length){

     if(2 > 4){

      this.toastr.error("You are not authorized to edit others matter", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.cs.notifyOther(true);
      return;
      
     }
     else{
      let paramdata = "";
        sessionStorage.removeItem('matter');
        paramdata = this.cs.encrypt({ code: data.matterNumber, code1: data.matterDescription, pageflow: data });
        sessionStorage.setItem('matter', paramdata);
        console.log(this.selection.selected[0])
        let obj: any = {};
        obj.classId=this.searhform.controls.classId1.value;
        obj.favourites=false;
        obj.matterNumber=data.matterNumber;
        obj.clientId=this.searhform.controls.clientId1.value;
        obj.classIdDescription=data.classId;
        obj.clientName=data.clientId;
        obj.languageId=this.auth.languageId;
        obj.createdBy=this.auth.userId;
        obj.updatedBy=this.auth.userId;
        obj.statusDesc=data.statusDesc;
        obj.matterDescription=data.matterDescription;
        this.spin.show();
        this.sub.add(this.matterservice.CreateFavorite(obj).subscribe(res => {
          this.spin.hide();
        }, err => {
          this.cs.commonerror(err);
          this.spin.hide();
        }));
        //this.router.navigate(['/main/matters/case-management/matter/' + paramdata])
        const url = this.router.serializeUrl(
          this.router.createUrlTree(['/main/matters/case-management/matter/' + paramdata])
          
        );
        
        window.open('#' + url, '_blank');
      
    
        
     
      // const dialogRef = this.dialog.open(CasecategoryDisplayComponent, {
      //   disableClose: true,
      //   width: '50%',
      //   maxWidth: '80%',
      //   position: { top: '6.7%', },
      //   data: { pageflow: data, code: data != 'New' ? this.selection.selected[0].clientId : null }
      // });
  
      // dialogRef.afterClosed().subscribe(result => {
  
      //   this.getAllListData();
      // });
     }
    }));


  }

  createTimeTicket(element): void {
    const dialogRef = this.dialog.open(TimeNewComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      //position: { top: '6.5%' },
      data: { pageflow: 'New', matter: element.matterNumber, matterdesc: element.matterDescription, code: null }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}