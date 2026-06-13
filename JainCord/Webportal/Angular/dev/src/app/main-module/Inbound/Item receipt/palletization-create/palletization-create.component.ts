import { ChangeDetectorRef, Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DeleteComponent } from 'src/app/common-field/delete/delete.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { PartnerService } from 'src/app/main-module/Masters -1/masternew/partner/partner.service';
import { AssignPickerComponent } from 'src/app/main-module/Outbound/assignment/assignment-main/assign-picker/assign-picker.component';
import { GoodsReceiptService } from '../../Goods-receipt/goods-receipt.service';
import { EditpopupComponent } from '../item-create/editpopup/editpopup.component';
import { LabelGenerateComponent } from '../item-create/label-generate/label-generate.component';
import { ItemReceiptService } from '../item-receipt.service';
import { AltuomService } from 'src/app/main-module/Masters -1/masternew/altuom/altuom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { Location } from '@angular/common';
import { DynamicPopupComponent } from 'src/app/common-field/dynamic-popup/dynamic-popup.component';
import { CustomTableSelectionComponent } from 'src/app/common-field/custom-table-selection/custom-table-selection.component';

@Component({
  selector: 'app-palletization-create',
  templateUrl: './palletization-create.component.html',
  styleUrls: ['./palletization-create.component.scss']
})
export class PalletizationCreateComponent implements OnInit {


  grNew: any[] = [];
  grBackup: any[] = [];
  selectedgrNew: any[] = [];
  @ViewChild('grNewTag') grNewTag: Table | any;

  screenid = 1050;
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
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;
  }


  isShown: boolean = false; // hidden by default
  toggleShow() {
    this.isShown = !this.isShown;
  }
  animal: string | undefined;
  name: string | undefined;
  constructor(private fb: FormBuilder,
    public auth: AuthService,
    private service: ItemReceiptService, 
    public toastr: ToastrService, private dialog: MatDialog, private grservice: GoodsReceiptService, private partnerService: PartnerService,
    private ReportsService: ReportsService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private basicData: BasicdataService,
    private AltuomService: AltuomService,
    private route: ActivatedRoute, private router: Router,
    public cs: CommonService,
    private location: Location,
    private cdr: ChangeDetectorRef,) { }
  sub = new Subscription();




  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}")]);
  form = this.fb.group({
    barcodeId: [],
    companyCodeId: [],
    confirmedBy: [],
    confirmedOn: [],
    containerNo: [],
    containerReceiptNo: [],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    dockAllocationNo: [],
    partner_item_barcode: [],
    inboundOrderTypeId: [],
    languageId: [],
    plantId: [],
    preInboundNo: [],
    refDocNumber: [],
    referenceField1: [],
    referenceField10: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    stagingNo: [],
    statusId: [],
    updatedBy: [],
    updatedOn: [],
    vechicleNo: [],
    warehouseId: [],
    goodsReceiptNo: [],
  });

  submitted = false;
  public errorHandling = (control: string, error: string = "required") => {
    return this.form.controls[control].hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }



  isbtntext = true;

  code: any = {};
  js: any;

  ngOnInit(): void {
    sessionStorage.removeItem('barcode');
    this.form.disable();

    this.form.controls.containerNo.enable();
    let code = this.route.snapshot.params.code;
    if (code != 'new') {

      let js = this.cs.decrypt(code);
      this.fill(js);

      this.code = js.code;
      this.js = js;
      // console.log( this.code)
    }
    this.callTableHeader();
  }

  uomList:any[] = [];
  dropdownList(line){
    if(!line.itemCode){
      this.toastr.error("Kindly select item code", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
    }
    let obj: any = {};
    obj.languageId=[this.auth.languageId];
    obj.companyCodeId=[this.auth.companyId];
    obj.warehouseId=[this.auth.warehouseId];
    obj.plantId=[this.auth.plantId];
    obj.itemCode=[line.itemCode];
    this.spin.show();
    this.AltuomService.search(obj).subscribe(res =>{  
         res.forEach(x => this.uomList.push({ value: x.referenceField1, label:x.referenceField1,alternateUom:x.alternateUom }));
         this.uomList =  this.cs.removeDuplicateInArray(this.uomList);
      this.spin.hide();
    },err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    })
  }

  cols: any[] = [];
  closed: any[] = [];
  callTableHeader() {
    this.cols = [
      // { field: 'manufacturerName', header: 'Mfr Name' },
      { field: 'itemCode', header: 'Part No', format: 'editable', format1: true, },
      { field: 'itemDescription', header: 'Description' },
      { field: 'orderUom', header: 'UOM', format: 'editable', format1: true,  },
      { field: 'size', header: 'Size' },
      { field: 'brand', header: 'Brand' },
      { field: 'itemType', header: 'Item Type' },
      { field: 'itemGroup', header: 'Item Group' },
      { field: 'orderQty', header: 'Order Qty', format: 'calculation'},
      { field: 'acceptedQty', header: 'Release Qty', type: 'number' },
      { field: 'damageQty', header: 'Reject Qty', type: 'number' },
      { field: 'alternateUom', header: 'Alt UOM', format1: true,  },
      { field: 'noBags', header: 'No of Bags',  },
      { field: 'bagSize', header: 'Bag Size' },
      { field: 'varianceQty', header: 'Variance' },
      { field: 'partner_item_barcode', header: 'Barcode' },
      { field: 'rec_accept_qty', header: 'Total Release Qty' },
      { field: 'rec_damage_qty', header: 'Total Reject Qty' },
    ];

    this.closed = [
      { field: 'warehouseDescription', header: 'Warehouse' },
      { field: 'statusDescription', header: 'Status' },
      { field: 'referenceDocumentType', header: 'Order Type' },
      { field: 'plantDescription', header: 'Plant' },
      { field: 'refDocNumber', header: 'Order No' },
      { field: 'preInboundNo', header: 'Preinbound No' },
      { field: 'createdBy', header: 'Order Received By' },
      { field: 'createdOn', header: 'Order Received On' }
    ]
  }


  btntext = "Save";
  pageflow = "New";

  itemReceipt: any[] = [];
  fill(data: any) {

    if (data.pageflow != 'New') {
      // console.log(data);
      this.pageflow = "Edit";
      this.btntext = 'Update';
      this.form.controls.preInboundNo.disable();
      this.form.controls.warehouseId.disable();
      if (data.pageflow == 'Display') {

        this.isbtntext = false;
      }
      this.form.patchValue(data.code);
      this.form.disable();
      this.spin.show();
      this.sub.add(this.grservice.searchGrheader({
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        warehouseId: [this.auth.warehouseId],
        languageId: [this.auth.languageId],
        refDocNumber: [data.code.refDocNumber],
        statusId: [17],
      }).subscribe(res => {

        this.spin.hide();
        this.itemReceipt = res;


        // if(data.pageflow == 'Edit'){
        //   // console.log('done')
        //   this.transactionHistory(data);  
        // }

      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
      this.sub.add(this.grservice.searchLineSpark({
        stagingNo: [data.code.stagingNo],
        caseCode: [data.code.caseCode],
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        warehouseId: [this.auth.warehouseId],
        languageId: [this.auth.languageId],
        //  statusId:[14],
      }).subscribe(res => {
        const filteredRes = res.filter((x: any) => x.statusId != 17 && x.statusId != 101);
        filteredRes.forEach((x: any) => {
          x.varianceQty = Number(x.orderQty) - (Number(x.rec_accept_qty) + Number(x.rec_damage_qty));
          x.putAwayHandlingEquipment = "";
          x.packBarcodes = [];
          x.stagingNo = this.form.controls.stagingNo.value;
          x.goodsReceiptNo = this.form.controls.goodsReceiptNo.value;
          x.damageQty = 0;
          x.acceptedQty = x.orderQty;
          x.inventoryQuantity = x.orderQty;
        });
        this.spin.hide();
        this.grNew = filteredRes;
        this.selectedgrNew = filteredRes;
        this.grBackup = filteredRes;
        if (this.auth.companyId == '1001') {
          this.grNew.forEach((x: any) => {
            // let count =0;
            x.partner_item_barcode = x.batchSerialNumber;
            x.acceptedQty = x.orderQty;
            x.varianceQty = (x.acceptedQty - x.orderQty)
            x.packBarcodesState = true;
            x.noOfAcceptedLabel = x.acceptedQty;
            x.noOfDamageLabel = 0;
          });
        }
        if (filteredRes.length == 0) {
          this.toastr.error("No Lines Found", "", {
            timeOut: 2000,
            progressBar: false,
          });
        }

        // if(data.pageflow == 'Edit'){
        //   // console.log('done')
        //   this.transactionHistory(data);  
        // }

      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
      this.form.patchValue(data.code, {
        emitEvent: false
      });

    }
  }


  back() {
    this.location.back();
  }
  ngOnDestroy() {
    // if(this.js.pageflow == 'Edit' && this.activeUser == true){
    //   localStorage.removeItem('transactionHistory');
    // }
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
    sessionStorage.removeItem('barcode');
  }

  
  

  delete() {
    if (this.selectedgrNew.length === 0) {
      this.toastr.error("Kindly select one row", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    if (this.selectedgrNew.length > 1) {
      this.toastr.error("Kindly select one row", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: {
        top: '6.7%',
      },
      // data: this.selection.selected[0],
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spin.show();
        this.sub.add(this.service.DeleteLine(this.selectedgrNew[0]).subscribe(res => {
          this.spin.hide();
          this.ngOnInit();
        }, err => {

          this.cs.commonerrorNew(err);
          this.spin.hide();

        }));
      }
    });

  }
  impartnerLinesFound: any[] = [];
  selectedgrNew1: any[] = [];



  filterValue: any;

  lablelGenerate(element, pageflow) {
    if (!element) {  //this.selectedgrNew.length === 0
      this.toastr.error("Kindly select one row", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    const dialogRef = this.dialog.open(LabelGenerateComponent, {
      disableClose: true,
      width: '85%',
      maxWidth: '60%',
      position: { top: '9%', },
      data: { line: element, pageflow: pageflow },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.selectedgrNew = [];

      if (result.line.partner_item_barcode == null) {   // && (result.line.partner_item_barcode1 ==null)
        this.form.controls.partner_item_barcode.patchValue(result.line.itemCode);
      }
      if (result) {
        if (result) {
          this.selectedgrNew.push(result)
        }
      }
    });
  }

  finalValue: any[] = [];






  assignPalletID(){
      const cols = [
        { field: 'handlingUnit', header: 'HE', format: 'normal' },
        { field: 'length', header: 'Length', format: 'normal' },
        { field: 'width', header: 'Width', format: 'normal' },
        { field: 'height', header: 'Height', format: 'normal' },
      ];
      const dialogRef = this.dialog.open(CustomTableSelectionComponent, {
        disableClose: true,
        width: '50%',
        maxWidth: '80%',
        position: { top: '9%', },
        data: { cols: cols, title: 'Assign Pallet', pageFrom: 'pallet' },
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.selectedgrNew.forEach((x: any) => {
              x.palletId = result;
          });
          this.submitInterim();
        }
      });
  }

  assignUserID(): void {
    if (this.selectedgrNew.length === 0) {
      this.toastr.error("Kindly select one row", "", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }

    const dialogRef = this.dialog.open(AssignPickerComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result2 => {
      if (result2) {
        this.selectedgrNew.forEach((x: any) => {
            x.assignedUserId = result2;
        });
        this.submitInterim();
      }
    });
  }

  submitInterim() {
    //this.finalValue = [];
    this.spin.show();
    this.selectedgrNew.forEach(x => {
      if (x.packBarcodesState == true) {
        x.receiverName = x.referenceField6;
        x.unloaderName = x.referenceField7;
      //  this.finalValue.push(x);
      }
    })
    this.service.updatePalletization(this.selectedgrNew).subscribe(res => {
      this.toastr.success("Palletization assigned successfully", "Notification",
        {
          timeOut: 2000,
          progressBar: false,
        });
      this.router.navigate(["/main/inbound/palletization-main"]);
      this.spin.hide();
    }, err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    })
  }
  
  onChange() {
    const choosen = this.selectedgrNew[this.selectedgrNew.length - 1];
    this.selectedgrNew.length = 0;
    this.selectedgrNew.push(choosen);
    this.lablelGenerate(this.selectedgrNew[0], 'New');
  }



  onDeleteRow(element) {
    element.acceptedQty = null;
    element.damageQty = 0;
    element.statusId = 14;
    element.packBarcodes = [];
    element.packBarcodesState = false;
    element.partner_item_barcode = null;
    element.varianceQty = 0;
    this.grNew.forEach((x: any) => {
      let count = 0;
      if (x.acceptedQty != null) {
        count++;
        // console.log(count);
        if (count == 1) {
        }
        else {
        }
      }
      else {;
      }
    });
  }
  Edit(element) {
    if (this.js.pageflow == "Edit") {
      const dialogRef = this.dialog.open(EditpopupComponent, {
        disableClose: true,
        width: '85%',
        maxWidth: '60%',
        position: { top: '9%', },
        data: element,

      });

      dialogRef.afterClosed().subscribe(result => {
        this.selectedgrNew = [];

        if (result) {
          if (result) {
            this.selectedgrNew1.push(result)
          }
        }
      });
    }
  }



  printLabel(element) {
    if (element.acceptedQty == null || element.acceptedQty == '' || element.acceptedQty == 0) {
      this.toastr.error(
        "Lines not confirmed",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );

      this.cs.notifyOther(true);
      return;
    }

    let obj: any = {};
    let dataa: any[] = [];
    for (let j = 0; j < element.orderQty; j++) {

      obj.barcodeId = element.barcodeId;
      obj.partner_item_barcode = element.partner_item_barcode;
      obj.goodReceiptQty = 1;
      obj.noOfDamageLabel = element.noOfDamageLabel;
      obj.noOfAcceptedLabel = element.orderQty;
      obj.size = element.bagSize;
      obj.mrp = element.mrp;
      obj.totalLabel = element.orderQty;
      obj.barCodeType = element.barCodeType;
      obj.manufacturerName = element.manufacturerName;
      obj.origin = element.origin;
      obj.itemCode = element.itemCode;
      obj.referenceField9 = element.referenceField9;
      obj.itemDescription = element.itemDescription;
      obj.qty = element.orderQty;
      dataa.push(obj);
    }
    sessionStorage.setItem(
      'barcode',
      JSON.stringify({ BCfor: "barcode", list: dataa })
    );
    window.open('#/barcodeNew', '_blank');
  }

  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.grNew.forEach((x, i) => {
      res.push({
        "S No": i + 1,
        "Mfr Name": x.manufacturerName,
        "Part No ": x.itemCode,
        "Description": x.itemDescription,
        "Order Qty": x.orderQty,
        "Released Qty ": x.acceptedQty,
        'Rejected Qty': x.damageQty,
        "Variance": x.varianceQty,
        'Barcode': x.partner_item_barcode,
        "Total Release Qty": x.rec_accept_qty,
        "Total Rejected Qty": x.rec_damage_qty,
        "UOM": x.orderUom,
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Palletization");
  }



  addLines() { // Check value here
    const newRow = {
      languageId: this.auth.languageId,
      companyCode: this.auth.companyId,
      plantId: this.auth.plantId,
      warehouseId: this.auth.warehouseId,
      preInboundNo: this.code.preInboundNo,
      refDocNumber: this.code.refDocNumber,
      stagingNo: this.code.stagingNo,
      palletCode: this.code.palletCode,
      caseCode: this.code.caseCode,
      lineNo: this.grNew.length + 1,
      itemCode: null,
      inboundOrderTypeId: this.code.inboundOrderTypeId,
      goodsReceiptNo: this.code.goodsReceiptNo,
      variantCode: null,
      variantSubCode: null,
      batchSerialNumber: null,
      stockTypeId: null,
      specialStockIndicatorId: null,
      storageMethod: null,
      statusId: null,
      businessPartnerCode: this.code.businessPartnerCode,
      containerNo: this.code.containerNo,
      invoiceNo: null,
      orderQty: null,
      orderUom: this.code.orderUom,
      itemQtyPerPallet: null,
      itemQtyPerCase: null,
      assignedUserId: null,
      itemDescription: null,
      manufacturerPartNo: null,
      hsnCode: null,
      variantType: null,
      specificationActual: null,
      itemBarcode: null,
      referenceOrderNo: null,
      referenceOrderQty: null,
      crossDockAllocationQty: null,
      remarks: null,
      packBarcodes: [],
      referenceField1: null,
      referenceField2: null,
      referenceField3: null,
      referenceField4: null,
      referenceField5: null,
      referenceField6: null,
      referenceField7: null,
      referenceField8: null,
      referenceField9: null,
      referenceField10: null,
      deletionIndicator: null,
      createdBy: null,
      createdOn: null,
      updatedBy: null,
      updatedOn: null,
      confirmedBy: null,
      confirmedOn: null,
      companyDescription: null,
      plantDescription: null,
      warehouseDescription: null,
      statusDescription: null,
      inventoryQuantity: null,
      manufacturerCode: null,
      manufacturerName: null,
      origin: null,
      brand: null,
      partner_item_barcode: null,
      rec_accept_qty: null,
      rec_damage_qty: null,
      middlewareId: null,
      middlewareHeaderId: null,
      middlewareTable: null,
      purchaseOrderNumber: this.code.purchaseOrderNumber,
      manufacturerFullName: null,
      referenceDocumentType: this.code.referenceDocumentType,
      branchCode: null,
      transferOrderNo: null,
      isCompleted: null,
      alternateUom: null,
      noBags: null,
      bagSize: null,
      newCreated: true
    };

    this.grNew.splice(this.grNew.length, 0, newRow);
    this.grNew = [...this.grNew];
    this.scrollToLast();
    // console.log(this.grNew);
  }


  fourDigitDate() {
    let currentDate = new Date();
    let month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    let day = currentDate.getDate().toString().padStart(2, '0');
    let firstFourDigits = day + month;
    return firstFourDigits
  }

  calculateBarcode(line) {
    const refDocNumber = line.refDocNumber;
    const lastFourDigits = refDocNumber.slice(-4);
    const concatenatedValue = line.itemCode + lastFourDigits + this.fourDigitDate();
    line.partner_item_barcode = concatenatedValue;
  }



  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.ReportsService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.description })); //+x.manufacturerName
          }
        });
    }
  }

  itemCodeChanged(event) {
    this.spin.show();
    this.basicData.search({ itemCode: [event.itemCode], companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], warehouseId: [this.auth.warehouseId] }).subscribe(res => {
      if (res.length > 0) {
        event.itemDescription = res[0].description;
        event.manufacturerCode = res[0].manufacturerCode;
        event.manufacturerPartNo = res[0].manufacturerPartNo;
        event.manufacturerName = res[0].manufacturerName;
        event.manufacturerFullName = res[0].manufacturerFullName;
        this.calculateBarcode(event);
        this.dropdownList(event);
      }
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })
  }

  altUomChange(data,event) { 
const module = this.uomList.find(module => module.value === data);
   let uomChanged=module.alternateUom;
    this.AltuomService.search({ itemCode: [event.itemCode], companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], warehouseId: [this.auth.warehouseId],alternateUom:[uomChanged] }).subscribe(res => {
      if (res.length > 0) {
      event.alternateUom=res[0].referenceField1
      event.bagSize=res[0].uomIdQty;
      }
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })
  }

  calculateOrderQty(element) {
    element.noBags = element.orderQty;

    element.damageQty = element.damageQty ?? 0;
    element.acceptedQty = element.acceptedQty ?? 0;
    element.recAcceptQty = element.rec_accept_qty ?? 0;
    element.recDamageQty = element.rec_damage_qty ?? 0;
    
    // Calculate the sum of quantities
    let num: number = (Number(element.damageQty) + Number(element.acceptedQty) + Number(element.recAcceptQty) + Number(element.recDamageQty));
  
    // Ensure orderQty is also set to 0 if it's null or undefined
    const orderQty = element.orderQty ?? 0;
  
    // Calculate the variance
    element.varianceQty = orderQty - num;
  }

  @ViewChildren('row') rows!: QueryList<ElementRef>;
  scrollToLast() {
    if (this.rows.length) {
      this.cdr.detectChanges();
      const lastRow = this.rows.toArray()[this.grNew.length - 1];
      if (this.grNew.length > 11 && lastRow) {
        lastRow.nativeElement.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }


  printLabel1() {
    let dataa: any[] = [];
    this.selectedgrNew.forEach(x => {
      for (let j = 0; j < (1); j++) { //for (let j = 0; j < (x.noBags ?? 1); j++) {
        let obj: any = {};
        obj.barcodeId = x.partner_item_barcode;
        obj.partner_item_barcode = x.partner_item_barcode;
        obj.goodReceiptQty = 1;
        obj.noOfDamageLabel = x.noOfDamageLabel;
        obj.noOfAcceptedLabel = x.noBags;
        obj.size = x.bagSize;
        obj.mrp = x.mrp;
        obj.totalLabel = x.noBags;
        obj.barCodeType = x.barCodeType;
        obj.manufacturerName = x.manufacturerName;
        obj.origin = x.origin;
        obj.itemCode = x.itemCode;
        obj.referenceField9 = 'Consignment Label';
        obj.itemDescription = x.itemDescription;
        obj.qty = x.orderQty;
        dataa.push(obj);
      }

    })
   
    sessionStorage.setItem(
      'barcode',
      JSON.stringify({ BCfor: "barcode", list: dataa })
    );
    window.open('#/barcode', '_blank');

  }
}


