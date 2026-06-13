import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { AxinterfaceService } from './axinterface.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-axinterface',
  templateUrl: './axinterface.component.html',
  styleUrls: ['./axinterface.component.scss']
})
export class AxinterfaceComponent implements OnInit {
  ELEMENT_DATA: any[] = [];
  sub = new Subscription();

  constructor(private http: HttpClient, private service: AxinterfaceService,private auth: AuthService, private fb: FormBuilder,
    private spin: NgxSpinnerService, private cs: CommonService) {}
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  displayedColumns: string[] = ['languageId', 'companyCodeId', 'plantId', 'warehouseId', 'axApiResponseId','refDocNumber','referenceDocumentType','message','statusCode','createdBy','createdOn'];
  dataSource = new MatTableDataSource < any > (this.ELEMENT_DATA);
  @ViewChild(MatSort, {
    static: true
  })
  sort!: MatSort;
  @ViewChild(MatPaginator, {
    static: true
  })
  paginator!: MatPaginator; // Pagination
  // Pagination
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
  ngOnInit(): void {
    this.getDropdown();
    let currentDate = new Date();
    let currentMonthStartDate = new Date();
    currentMonthStartDate.setDate(currentDate.getDate() - 31);
    this.searhform.controls.sendDateFE.patchValue(new Date());
    this.searhform.controls.sstartDateFE.patchValue(currentMonthStartDate);
  }
  multiOrderNo: any[] = [];
  multiapirespnseId:any[]=[];
  getDropdown() {
    this.sub.add(this.service.search({warehouseId:[this.auth.warehouseId]}).subscribe(res => {
      res.forEach((x: any) => this.multiOrderNo.push({
        value: x.refDocNumber,
        label: x.refDocNumber
      }));
      this.multiapirespnseId = this.cs.removeDuplicatesFromArrayNewstatus(this.multiapirespnseId);
      res.forEach((x: any) => this.multiapirespnseId.push({
        value: x.axApiResponseId,
        label: x.axApiResponseId
      }));
      this.multiOrderNo = this.cs.removeDuplicatesFromArrayNewstatus(this.multiOrderNo);
      this.multiOrderNo = this.cs.removeDuplicatesFromArrayNewstatus(this.multiOrderNo);
    }))
    
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  searhform = this.fb.group({
    axApiResponseId: [],
    endDate: [],
    message: [],
    outboundOrderTypeId: [],
    partnerCode: [],
    preOutboundNo: [],
    refDocNumber: [],
    sendDate: [],
    sendDateFE: [],
    sstartDate: [],
    sstartDateFE: [],
    startDate: [],
    statusCode: [],
    statusId: [],
    warehouseId: [[this.auth.warehouseId]],
  });
  sendDate: any;
  sstartDate: any;
  axApiResponseId: any
  filtersearch() {
    this.spin.show();
   
    this.searhform.controls.sstartDate.patchValue(this.cs.dateNewFormat1(this.searhform.controls.sstartDateFE.value));
    this.searhform.controls.sendDate.patchValue(this.cs.dateNewFormat1(this.searhform.controls.sendDateFE.value));
    this.sub.add(this.service.search(this.searhform.getRawValue()).subscribe(result => {
        this.dataSource = new MatTableDataSource < any > (result);
        this.table = true;
        this.search = false;
        this.fullscreen = false;
        this.back = true;
        this.spin.hide();
      },
      err => {
        this.cs.commonerror(err);
        this.spin.hide();
      }));
  }

  downloadexcel() {
    var res: any = [];
    this.dataSource.data.forEach(x => {
      res.push({
        "language ID": x.languageId,
        "Company ID": x.companyCodeId,
        "Plant ID": x.plantId,
        "Warehouse ID ": x.warehouseId,
        "Ax Api Response ID": x.axApiResponseId,
        "Order No": x.refDocNumber,
        "Order Type": x.referenceDocumentType,
        "Message":x.message,
        "Status":x.statusId,
         "Created By":x.createdBy,
         "Created On":x.createdOn,

      });

    })
    this.cs.exportAsExcel(res, "AX Log");
  }
  reset() {

   this.searhform.reset();
   this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
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
}
