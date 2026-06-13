import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { iwExpressLogo } from '../../../../../assets/font/iwExpress';
import { Subscription } from 'rxjs';
import { walkaroo } from '../../../../../assets/font/walkaroo.js';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import {
  CommonService,
  dropdownelement1,
} from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PutawayService } from '../putaway.service';
import { Table } from 'primeng/table';
import { stat } from 'fs';
import { PutawayheaderComponent } from '../putawayheader/putawayheader.component';
import { AssignPickerComponent } from 'src/app/main-module/Outbound/assignment/assignment-main/assign-picker/assign-picker.component';
import { PutawayProposedComponent } from './putaway-proposed/putaway-proposed.component';
import { DatePipe } from '@angular/common';
import pdfMake from 'pdfmake/build/pdfmake';
import JsBarcode from 'jsbarcode';

@Component({
  selector: 'app-putaway-main',
  templateUrl: './putaway-main.component.html',
  styleUrls: ['./putaway-main.component.scss'],
})
export class PutawayMainComponent implements OnInit {
  screenid = 3051;
  putaway: any[] = [];
  selectedputaway: any[] = [];
  @ViewChild('putawayTag') putawayTag: Table | any;

  selectedStatusIdList: any[] = [];
  constructor(
    public datePipe: DatePipe,
    private service: PutawayService,
    public toastr: ToastrService,
    public dialog: MatDialog,
    private spin: NgxSpinnerService,
    private router: Router,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService
  ) {
    this.selectedStatusIdList = [
      { value: 19, label: 'Putaway Created' },
      { value: 20, label: 'Putaway Confirmed' },
    ];
  }
  sub = new Subscription();
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.search(true);
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
    this.putaway.forEach((x) => {
      res.push({
        Branch: x.plantDescription,
        Warehouse: x.warehouseDescription,
        'Status ': x.statusDescription,
        'Order Type ': x.referenceDocumentType,
        'Part No': x.referenceField5,
        'Order No': x.refDocNumber,
        'Putaway No': x.putAwayNumber,
        'Barcode': x.barcodeId,
        'Storage Bin': x.proposedStorageBin,
        'Assign User': x.assignedUserId,
        'Level ': x.levelId,
        'To Qty': x.putAwayQuantity,
        'Created By': x.createdBy,
        'Created On': this.cs.dateapiwithTime(x.createdOn),
        Remark: x.remark,
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });
    });
    this.cs.exportAsExcel(res, 'Putaway');
  }

  warehouseId = this.auth.warehouseId;

  searhform = this.fb.group({
    createdBy: [],
    endCreatedOn: [],
    packBarcodes: [],
    barcodeId: [],
    proposedHandlingEquipment: [],
    proposedStorageBin: [],
    putAwayNumber: [],
    refDocNumber: [],
    itemCode: [],
    startCreatedOn: [],
    statusId: [[19]], //19
    warehouseId: [[this.auth.warehouseId]],
    companyCodeId: [[this.auth.companyId]],
    languageId: [[this.auth.languageId]],
    plantId: [[this.auth.plantId]],
  });

  dropdownSettings = {
    singleSelection: false,
    text: 'Select',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2,
  };

  packBarcodesListselected: any[] = [];
  packBarcodesList: any[] = [];

  proposedHandlingEquipmentListselected: any[] = [];
  proposedHandlingEquipmentList: any[] = [];

  proposedStorageBinListselected: any[] = [];
  proposedStorageBinList: any[] = [];

  putAwayNumberListselected: any[] = [];
  putAwayNumberList: any[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];

  putawayResult: any[] = [];
  search(ispageload = false) {
    if (!ispageload) {
    }
    this.spin.show();
    this.service.searchSpark(this.searhform.value).subscribe(
      (res) => {
        this.putawayResult = res;
        let result = res.filter((x: any) => x.statusId != 22);
        // let result = res.filter((x: any) => x.warehouseId == this.warehouseId);

        if (ispageload) {
          let temppackBarcodesList: any[] = [];
          const packBarcodes = [
            ...new Set(result.map((item) => item.barcodeId)),
          ].filter((x) => x != null);
          packBarcodes.forEach((x) =>
            temppackBarcodesList.push({ value: x, label: x })
          );
          this.packBarcodesList = temppackBarcodesList;

          let tempproposedHandlingEquipmentList: any[] = [];
          const proposedHandlingEquipment = [
            ...new Set(result.map((item) => item.referenceField5)),
          ].filter((x) => x != null);
          proposedHandlingEquipment.forEach((x) =>
            tempproposedHandlingEquipmentList.push({ value: x, label: x })
          );
          this.proposedHandlingEquipmentList =
            tempproposedHandlingEquipmentList;

          let tempproposedStorageBinList: any[] = [];
          const proposedStorageBin = [
            ...new Set(result.map((item) => item.proposedStorageBin)),
          ].filter((x) => x != null);
          proposedStorageBin.forEach((x) =>
            tempproposedStorageBinList.push({ value: x, label: x })
          );
          this.proposedStorageBinList = tempproposedStorageBinList;

          let tempputAwayNumberList: any[] = [];
          const putAwayNumber = [
            ...new Set(result.map((item) => item.putAwayNumber)),
          ].filter((x) => x != null);
          putAwayNumber.forEach((x) =>
            tempputAwayNumberList.push({ value: x, label: x })
          );
          this.putAwayNumberList = tempputAwayNumberList;

          let temprefDocNumberList: any[] = [];
          const refDocNumber = [
            ...new Set(result.map((item) => item.refDocNumber)),
          ].filter((x) => x != null);
          refDocNumber.forEach((x) =>
            temprefDocNumberList.push({ value: x, label: x })
          );
          this.refDocNumberList = temprefDocNumberList;

          let tempstatusIdList: any[] = [];
          // const statusId = [...new Set(result.map(item => item.statusId))].filter(x => x != null)
          res.forEach((x) =>
            tempstatusIdList.push({
              value: x.statusId,
              label: x.statusDescription,
            })
          );
          this.statusIdList = tempstatusIdList;
          this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(
            this.statusIdList
          );
        }
        this.spin.hide();
        res = this.cs.removeDuplicatesFromArrayList(res, 'packBarcodes');
        this.putaway = res;
      },
      (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    );
  }

  deleteDialog() {
    if (this.selectedputaway.length === 0) {
      this.toastr.error('Kindly select any row', 'Norification');
      return;
    }
    if (this.selectedputaway[0].statusId != 19) {
      this.toastr.error("Confirmed items can't be deleted", 'Norification');
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '40%',
      maxWidth: '80%',
      position: { top: '9%' },
      // data: this.selection.selected[0],
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedputaway[0]);
      }
    });
  }
  deleterecord(obj: any) {
    this.spin.show();
    this.sub.add(
      this.service.deletePutawayHeader(obj).subscribe(
        (ress) => {
          this.toastr.success(
            obj.putAwayNumber + ' Deleted successfully.',
            'Notification',
            {
              timeOut: 2000,
              progressBar: false,
            }
          );
          this.search(true);
          this.spin.hide();
        },
        (err) => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }
      )
    );
  }

  reload() {
    this.searhform.reset();
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
  }
  openDialog(data: any = 'new', type?: any): void {
    if (type && type != undefined) {
      this.selectedputaway = [];
      this.selectedputaway.push(type);
    }

    if (data != 'New')
      if (this.selectedputaway.length === 0) {
        this.toastr.error('Kindly select any row', 'Notification', {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }

    if (data != 'Display')
      if (![19, 21].includes(this.selectedputaway[0].statusId)) {
        this.toastr.error(
          "Order can't be edited as it's already proccessed.",
          'Notification',
          {
            timeOut: 2000,
            progressBar: false,
          }
        );
        return;
      }
    let paramdata = '';

    if (this.selectedputaway.length > 0) {
      paramdata = this.cs.encrypt({
        code: this.selectedputaway[0],
        result: this.putawayResult.filter(x => x.packBarcodes == this.selectedputaway[0].packBarcodes),
        pageflow: data,
      });
      this.router.navigate(['/main/inbound/putaway-create/' + paramdata]);
    } else {
      paramdata = this.cs.encrypt({ pageflow: data });
      this.router.navigate(['/main/inbound/putaway-create/' + paramdata]);
    }
  }
  openDialogHeader(data: any = 'New'): void {
    console.log(this.selectedputaway);
    if (data != 'New')
      if (this.selectedputaway.length === 0) {
        this.toastr.warning('Kindly select any Row', 'Notification', {
          timeOut: 2000,
          progressBar: false,
        });
        return;
      }
    const dialogRef = this.dialog.open(PutawayheaderComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: { pageflow: data, code: this.selectedputaway[0] },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.search(true);
    });
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

  onChange() {
    const choosen = this.selectedputaway[this.selectedputaway.length - 1];
    this.selectedputaway.length = 0;
    this.selectedputaway.push(choosen);
  }

  assignUserID(): void {
    if (this.selectedputaway.length === 0) {
      this.toastr.error('Kindly select one row', '', {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    const dialogRef = this.dialog.open(AssignPickerComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      position: { top: '9%' },
    });

    dialogRef.afterClosed().subscribe((result2) => {
      console.log(result2)
      if (result2) {
        this.selectedputaway.forEach((x: any) => {
          x.assignedUserId = result2;
        });
        this.updateHHtUser();
        //this.search();
        this.selectedputaway=[];
      }
    
    });
  }

  updateHHtUser() {
    this.spin.show();
    this.service.UpdateBulk(this.selectedputaway).subscribe(
      (res) => {
        this.toastr.success('HHT Assigned successfully', 'Notification', {
          timeOut: 2000,
          progressBar: false,
        });
        this.spin.hide();
        this.search();
      },
      (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    );
  }

  iotBinLocation(line): void {
    this.spin.show();
    setTimeout(() => {
      this.spin.hide();
      const dialogRef = this.dialog.open(PutawayProposedComponent, {
        disableClose: true,
        width: '70%',
        maxWidth: '80%',
        position: { top: '9%' },
        data: { line },
      });

      dialogRef.afterClosed().subscribe((result) => {
        console.log('The dialog was closed');
      });
    }, 2000);
  }

  generateBarcode(text: string) {
    const canvas = document.createElement('canvas');
    JsBarcode(canvas, text, { height: 80 });
    return canvas.toDataURL('image/png');
  }

  putList(ready: any) {
    let dd: any = {
      pageSize: 'A4',
      pageOrientation: 'landscape',
      alignment: 'center',
      pageMargins: [20, 10, 20, 10],
      styles: {
        anotherStyle: {
          bordercolor: '#6102D3',
        },
      },
      footer(currentPage: number, pageCount: number, pageSize: any): any {
        return [{
          text: 'Page ' + currentPage + ' of ' + pageCount,
          style: 'header',
          alignment: 'center',
          bold: true,
          fontSize: 6
        }]
      },
      content: ['\n'],
     
    };

    
    let createdOn = this.datePipe.transform(new Date(), 'dd-MM-yyyy');
 
  
    let obj: any = {};
    obj.packBarcodes = [ready.packBarcodes];
    obj.companyCodeId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.plantId = [this.auth.plantId];
    this.sub.add(
      this.service.searchv2(obj).subscribe((res: any) => {
      let  result = res.sort((a, b) => (a.proposedStorageBin > b.proposedStorageBin) ? 1 : -1)

        if (res.length > 0) {
          let confirmedOn = this.datePipe.transform(
            result[0].confirmedOn,
            'dd-MMM-yyyy HH:mm',
            'GMT'
          );
    
          // Creating the layout with equal width columns
          dd.content.push(
            {
              columns: [
                {
                  // Left column with logo
                  width: '35%',
                  image: walkaroo.headerLogo, 
                  fit: [150, 150],  // Adjust the size of the logo
                  alignment: 'left',
                  margin: [0, -15, 0, 0]
                },
                {
                  // Center column for spacing (empty)
                  width: '45%',
                  stack: [
                    {
                      text: 'Walkaroo International Pvt Ltd',
                      alignment: 'center',
                      bold: true,
                      fontSize: 14,
                      margin: [-140,-10, 0, 0]
                    },
                    {
                      text: 'Plot No 10-12 SIDCO Industrial Estate,',
                      alignment: 'center',
                      bold: true,
                      fontSize: 9,
                      margin: [-140, 0, 0, 5]
                    },
                    {
                      text: 'Malumichampatti Coimbatore 641050',
                      alignment: 'center',
                      bold: true,
                      fontSize: 9,
                      margin: [-140, -3, 0, 5]
                    },
                    {
                      text: 'Tamil Nadu, India',
                      alignment: 'center',
                      bold: true,
                      fontSize: 9,
                      margin: [-140, -3, 0, 5]
                    },
                    {
                      text: 'Putaway List',
                      alignment: 'center',
                      bold: true,
                      fontSize: 14,
                      margin: [-140, 2, 0, 5]
                    }
                  ]
                },
                {
                  // Right column with PutList No., Date, and Barcode
                  width: '20%',
                  stack: [
                    {
                      text: 'PutList No:  '+'' + result[0].putAwayNumber,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -10, 0, 0]
                    },
                    {
                      text: 'User:'+'' + result[0].confirmedBy,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, 0, 0, 5]
                    },
                    {
                      text: 'Date: '+'' + confirmedOn,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -4, 0, 5]
                    },
                    {
                      // Barcode image
                      image: this.generateBarcode(result[0].packBarcodes),
                      fit: [100, 100],  // Adjust the size of the barcode
                      alignment: 'left',
                      margin: [0, 0, 0, 0]
                    }
                  ]
                }
              ]
            },
          )
      //       // Adding the horizontal line before the table
      // dd.content.push({
      //   canvas: [
      //     {
      //       type: 'line',
      //       x1: 0, y1: 0,
      //       x2: 799, y2: 0, 
      //       lineWidth: 1.5,
      //       lineColor: '#808080', 
      //     },
      //   ],
      //   margin: [-10, -50, 0, 10], // Adjust the margin if needed
      // });

          let bodyArray68: any[] = [];
          bodyArray68.push([
            {
              text: 'SNo ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Storage Bin ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Pallet No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Inbound No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'SKUCode ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Material ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'HU Serial No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Price Segement ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Article Number ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Gender ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Color ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Size ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'No of Pairs ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Qty ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
          ]);
          dd.content.push({
            table: {
              headerRows: 1,
              widths: [30, 50, 52, 52, 73, 72, 72, 40, 40, 40, 40, 40, 40, 30],
              body: bodyArray68,
            },
            margin: [0, 0, 0, 14],
          });
          for (let i = 0; i < result.length; i++) {
            let bodyArray69: any[] = [];
            bodyArray69.push([
              {
                text: i + 1,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].proposedStorageBin,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].packBarcodes,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].refDocNumber,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].referenceField5,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].materialNo,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
                margin: [0, -3, 0, 0],
                padding: [0, 0, 0, 0],
              },
              {
                text: result[i].barcodeId,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].priceSegment,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].articleNo,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
                margin: [0, -3, 0, 0],
                padding: [0, 0, 0, 0],
              },
              {
                text: result[i].gender,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
                margin: [0, -3, 0, 0],
                padding: [0, 0, 0, 0],
              },
              {
                text: result[i].color,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].size,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
                margin: [0, -3, 0, 0],
                padding: [0, 0, 0, 0],
              },
              {
                text: result[i].noPairs,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
              },
              {
                text: result[i].orderQty,
                bold: true,
                fontSize: 8,
                alignment: 'left',
              border: [true, true, true, true],
                borderColor: ['#808080', '#808080', '#808080', '#808080'],
                margin: [0, -3, 0, 0],
                padding: [0, 0, 0, 0],
              },
            ]);
            dd.content.push(
              {
                table: {
                  headerRows: 1,
                  widths: [30, 50, 52, 52, 73, 72, 72, 40, 40, 40, 40, 40, 40, 30],
                  body: bodyArray69,
                },
                margin: [0, -14, 0, 0],
              },
              '\n'
            );
          }
        } else {
          dd.content.push(
            {
              columns: [
                {
                  // Left column with logo
                  width: '35%',
                  image: walkaroo.headerLogo, 
                  fit: [150, 150],  // Adjust the size of the logo
                  alignment: 'left',
                  margin: [0, -15, 0, 0]
                },
                {
                  // Center column for spacing (empty)
                  width: '45%',
                  stack: [
                    {
                      text: 'Walkaroo International Pvt Ltd',
                      alignment: 'center',
                      bold: true,
                      fontSize: 14,
                      margin: [-140,-10, 0, 0]
                    },
                    {
                      text: 'Plot No 10-12 SIDCO Industrial Estate,',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-140, 0, 0, 5]
                    },
                    {
                      text: 'Malumichampatti Coimbatore 641050',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-140, -3, 0, 5]
                    },
                    {
                      text: 'Tamil Nadu India',
                      alignment: 'center',
                      bold: true,
                      fontSize: 8,
                      margin: [-140, -3, 0, 5]
                    },
                    {
                      text: 'Putaway List',
                      alignment: 'center',
                      bold: true,
                      fontSize: 25,
                      margin: [-140, 2, 0, 5]
                    }
                  ]
                },
                {
                  // Right column with PutList No., Date, and Barcode
                  width: '20%',
                  stack: [
                    {
                      text: 'PutList No:  '+'' ,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -10, 0, 0]
                    },
                    {
                      text: 'User:'+'' ,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, 0, 0, 5]
                    },
                    {
                      text: 'Date: '+'' ,
                      alignment: 'left',
                      bold: true,
                      fontSize: 9,
                      margin: [0, -4, 0, 5]
                    },
                    {
                      // Barcode image
                      image: this.generateBarcode(result[0].packBarcodes),
                      fit: [100, 100],  // Adjust the size of the barcode
                      alignment: 'left',
                      margin: [0, 0, 0, 0]
                    }
                  ]
                }
              ]
            },
          )
            // Adding the horizontal line before the table
      // dd.content.push({
      //   canvas: [
      //     {
      //       type: 'line',
      //       x1: 0, y1: 0,
      //       x2: 799, y2: 0, 
      //       lineWidth: 1.5,
      //       lineColor: '#808080', 
      //     },
      //   ],
      //   margin: [-10, -35, 0, 10], // Adjust the margin if needed
      // });

          let bodyArray68: any[] = [];
          bodyArray68.push([
            {
              text: 'SNo',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Storage Bin ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Pallet No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Inbound No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'SKUCode ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Material ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'HU Serial No ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Price Segement ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Article Number ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Gender: ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Color ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Size ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'No of Pairs ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: 'Qty ',
              bold: true,
              fontSize: 8,
              alignment: 'left',
              border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
          ]);
          dd.content.push({
            table: {
              headerRows: 1,
              widths: [30, 50, 52, 52, 73, 72, 72, 40, 40, 40, 40, 40, 40, 30],
              body: bodyArray68,
            },
            margin: [0, 0, 0, 14],
          });

          let bodyArray69: any[] = [];
          bodyArray69.push([
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
            {
              text: '',
              bold: true,
              fontSize: 8,
              alignment: 'left',
            border: [true, true, true, true],
              borderColor: ['#808080', '#808080', '#808080', '#808080'],
            },
          ]);
          dd.content.push(
            {
              table: {
                headerRows: 1,
                widths: [30, 50, 52, 52, 73, 72, 72, 40, 40, 40, 40, 40, 40, 30],
                body: bodyArray69,
              },
              margin: [0, -14, 0, 0],
            },
            '\n'
          );
        }
        pdfMake
          .createPdf(dd)
          .download('Putaway Report : ' + ready.refDocNumber);
        pdfMake.createPdf(dd).open();
      })
    );
  }
}
