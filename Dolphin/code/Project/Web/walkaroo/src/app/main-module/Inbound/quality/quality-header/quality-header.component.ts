import { Component, OnInit, ViewChild } from '@angular/core';
import { QualityService } from '../quality.service';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Subscription } from 'rxjs';
import { Table } from 'primeng/table';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';

@Component({
  selector: 'app-quality-header',
  templateUrl: './quality-header.component.html',
  styleUrls: ['./quality-header.component.scss']
})
export class QualityHeaderComponent implements OnInit {

  screenid = 3227;
  quality: any[] = [];
  selectedQuality: any[] = [];
  @ViewChild('qualityTag') qualityTag: Table | any;
  selectedStatusIdList: any[] = [];

  constructor(
    private qualityService: QualityService,
    public toastr: ToastrService,
    public dialog: MatDialog,
    private spin: NgxSpinnerService,
    private router: Router,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,
  ) {
    this.selectedStatusIdList = [
      { value: 19, label: 'Quality Created' },
      { value: 20, label: 'Quality Confirmed' },
    ];
  }

  sub = new Subscription();
  RA: any = {};

  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search();
  }

  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  toggleFloat() {
    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more';
    }
    this.showFloatingButtons = !this.showFloatingButtons;
  }

  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.quality.forEach((x) => {
      res.push({
        'Branch': x.plantDescription,
        'Warehouse': x.warehouseDescription,
        'Status': x.statusDescription,
        'Order Type': x.referenceDocumentType,
        'Part No': x.itemCode,
        'Order No': x.refDocNumber,
        'Quality No': x.qualityNumber,
        'Received Qty': x.receivedQuantity,
        // 'Sample Qty': x.sampleQuantity,
        'Created By': x.createdBy,
        'Created On': this.cs.dateapiwithTime(x.createdOn),
      });
    });
    this.cs.exportAsExcel(res, 'Quality');
  }

  searhform = this.fb.group({
    warehouseId: [[this.auth.warehouseId]],
    companyCodeId: [[this.auth.companyId]],
    languageId: [[this.auth.languageId]],
    plantId: [[this.auth.plantId]],
    statusId:[[17,16]]
  });

  search() {
    this.spin.show();
    this.qualityService.searchHeader(this.searhform.value).subscribe(
      (res: any) => {
        console.log(res)
        res = this.cs.removeDuplicatesFromArrayList(res , 'refDocNumber')
        this.quality = res;
        this.spin.hide();
      },
      (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    );
  }

  openDialog(data: any = 'new', type?: any): void {
    if (type && type != undefined) {
      this.selectedQuality = [];
      this.selectedQuality.push(type);
    }

    if (data != 'New')
      if (this.selectedQuality.length === 0) {
        this.toastr.error('Kindly select any row', 'Notification', {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }

    if (data != 'Display')
      if ([24].includes(this.selectedQuality[0].statusId)) {
        this.toastr.error(
          "Order can't be edited because it is already proccessed.",
          'Notification',
          {
            timeOut: 2000,
            progressBar: false,
          }
        );
        return;
      }

    let paramdata = '';

    if (this.selectedQuality.length > 0) {
      paramdata = this.cs.encrypt({
        code: this.selectedQuality[0],
        pageflow: data,
      });
      this.router.navigate(['/main/inbound/qualityLine/' + paramdata]);
    } else {
      paramdata = this.cs.encrypt({ pageflow: data });
      this.router.navigate(['/main/inbound/qualityLine/' + paramdata]);
    }
  }

  assignUserID(): void {
    if (this.selectedQuality.length === 0) {
      this.toastr.error('Kindly select one row', '', {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
  }

}
