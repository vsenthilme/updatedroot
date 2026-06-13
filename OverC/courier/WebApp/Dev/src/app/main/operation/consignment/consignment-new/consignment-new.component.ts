import { Component, ElementRef, EventEmitter, Output } from '@angular/core';
import { ConsignmentService } from '../consignment.service';
import { FormBuilder, Validators, FormControl, FormArray, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { MatDialog } from '@angular/material/dialog';
import { ItemDetailsComponent } from './item-details/item-details.component';
import { DimensionComponent } from './dimension/dimension.component';
import { ImageUploadComponent } from './image-upload/image-upload.component';
import { ProductService } from '../../../id-masters/product/product.service';
import { HubPartnerAssignmentService } from '../../../master/hub-partner-assignment/hub-partner-assignment.service';
import { CustomerService } from '../../../master/customer/customer.service';
import { ConsignorService } from '../../../master/consignor/consignor.service';
import { ConsignmentLabelComponent } from '../../../pdf/consignment-label/consignment-label.component';
import { format } from 'util';
import { TableRowCollapseEvent, TableRowExpandEvent } from 'primeng/table';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { ConsignmentStatusPopupComponent } from './consignment-status-popup/consignment-status-popup.component';

@Component({
  selector: 'app-consignment-new',
  templateUrl: './consignment-new.component.html',
  styleUrl: './consignment-new.component.scss',
  animations: [
    trigger('fadeLater', [
      state('fade-in', style({ opacity: 1, transform: 'translateY(0)' })),
      state('fade-out', style({ opacity: 0, transform: 'translateY(0)' })),
      transition('fade-in <=> fade-out', animate('0.6s ease-in-out'))
    ]),
  ]
})
export class ConsignmentNewComponent {


  activeIndex: number = 0;
  panelOpenState = false;
  status: any[] = [];
  partnerType: any[] = [];
  paymentType: any[] = [];
  incoTerms: any[] = [];
  codCollectionMode: any[] = [];
  disabledCarrier = true;
  disabledSender = true;
  disabledDelivery = true;
  disabledBilling = true;
  disabledConsignment = true;
  disabledPiece = true;
  disabledDelivered = true;
  disabledReturn = true;


  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsignmentService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private el: ElementRef,
    public dialog: MatDialog,
    public productService: ProductService,
    public customerService: CustomerService,
    public consignorService: ConsignorService,
    public download: ConsignmentLabelComponent,
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
    this.partnerType = [
      { value: 'customer', label: 'Customer' },
      { value: 'consignor', label: 'Consignor' }
    ];
    this.paymentType = [
      { value: 'Prepaid', label: 'Prepaid' },
      { value: 'COD', label: 'COD' }
    ];
    this.incoTerms = [
      { value: 'DDU', label: 'DDU' },
      { value: 'DDP', label: 'DDP' }
    ];
    this.codCollectionMode = [
      { value: 'Cash', label: 'Cash' },
      { value: 'Cheque', label: 'Cheque' },
      { value: 'Online', label: 'Online' },
      { value: 'Card', label: 'Card' }
    ];
  }

  pageToken: any;
  // form builder initialize

  shipmentInfo = this.fb.group({
    companyId: [this.auth.companyId,],
    priority: [],
    incoTerms: [],
    partnerType: ['', Validators.required],
    consignmentId: [],
    partnerId: [, Validators.required],
    partnerName: [,],
    statusId: [,],
    paymentType: [,],
    productId: [],
    productName: [],
    subProductId: [],
    subProductName: [],
    serviceTypeId: [, Validators.required],
    serviceTypeText: [],
    manufacturer: [],
    masterAirwayBill: [],
    houseAirwayBill: [],
    noOfPieceHawb: [],
    noOfPackageMawb: [],
    countryOfOrigin: [],
    countryOfDestination: [],
    consignmentType: [,],
    customerCode: [],
    customerId: [],
    codAmount: [],
    codCollectionMode: [],
    codFavorOf: [],
    customerReferenceNumber: [],
    actionType: [],
    movementType: [],
    forwardReferenceNumber: [],
    workerCode: [],
    loadType: [, ],
    loadTypeId: [, Validators.required],
    courierAccount: [],
    courierPartner: [],
    invoiceNumber: [],
    courierPartnerReferenceNumber: [],
    invoiceAmount: [],
    invoiceUrl: [],
  });

  carrierInfo = this.fb.group({

  })


  OriginDetails = this.fb.group({
    accountId: [],
    addressHubCode: [,],
    addressLine1: [],
    addressLine2: [],
    alternatePhone: [],
    city: [],
    companyName: [],
    country: [],
    district: [],
    email: [],
    latitude: [],
    longitude: [],
    name: [],
    phone: [],
    pinCode: [],
    state: [],
  });


  DestinationDetails = this.fb.group({
    accountId: [,],
    addressHubCode: [],
    addressLine1: [],
    addressLine2: [],
    alternatePhone: [],
    city: [,],
    companyName: [],
    country: [],
    district: [],
    email: [],
    latitude: [],
    longitude: [],
    name: [],
    phone: [],
    pinCode: [],
    state: [],
  });


  senderInfo = this.fb.group({
    shipperId: [],
    shipperName: [],
    originDetails: this.OriginDetails
  })

  deliveryInfo = this.fb.group({
    consigneeName: [,],
    consigneeCivilId: [],
    destinationDetails: this.DestinationDetails
  })

  billing = this.fb.group({
    // incoTerms: [],
    // paymentType: [],
    //currency: [],
    // freightCurrency: [],
    //freightCharges: [],
    countryOfSupply: [],
    declaredValue: [],
    // consignmentCurrency: [],
    // consignmentValue: [],
    //actualCurrency: [],
    totalDuty: [],
    customsCurrency: [],
    costPerShipment: [],
    specialApprovalValue: [],
    // codAmount: [],
    // codFavorOf: [],
    // codCollectionMode: [],
    declaredValueWithoutTax: [],
    // invoiceAmount: [],
    // invoiceUrl: [],
    productCode: [],
    //  amount: [],
    //isCustomsDeclarable: [],
    iata: [],
    dduCharge: [],
    customsValue: [],
    specialApprovalCharge: [],
    exchangeRate: [],
    consignmentValueLocal: [],
    dutyPercentage: ['5%',],
    addInsurance: [],
    customsInsurance: [],
    addIata: [],
    actualDuty: [],
    calculatedTotalDuty: [],
  })

  consignment = this.fb.group({
    length: [],
    width: [],
    height: [],
    dimensionUnit: [],
    volume: [],
    volumeUnit: [],
    weight: [],
    weightUnit: [],
    invoiceDate: [,],
    invoiceDateFE: [new Date,],
    invoiceSupplierName: [],
    goodsDescription: [],
    notifyParty: [],
    consignmentCurrency: [],
    consignmentValue: [],
    partnerHouseAirwayBill: [],
    partnerMasterAirwayBill: [],
    airportOriginCode: [],
    flightArrivalTime: [],
    noOfPackages: [],
    flightName: [],
    flightNo: [],
    packageType: [],
    netWeight: [],
    grossWeight: [],
  })

  piece = this.fb.group({
    pieceDetails: this.fb.array([
    ])
  });

  initPieceDetail() {
   const control =  this.piece.controls.pieceDetails as FormArray
    return this.fb.group({
      codAmount: [''],
      declaredValue: [''],
      description: [''],
      dimensionUnit: [''],
      height: [''],
      itemDetails: this.fb.array([]),
      length: [''],
      packReferenceNumber: [control.value.length + 1, ],
      masterAirwayBill: [''],
      partnerId: [''],
      houseAirwayBill: [''],
      pieceProductCode: [''],
      tags: [''],
      partnerType: [''],
      pieceId: [''],
      referenceField1: [''],
      referenceField10: [''],
      referenceField11: [''],
      referenceField12: [''],
      referenceField13: [''],
      referenceField14: [''],
      referenceField15: [''],
      referenceField16: [''],
      referenceField17: [''],
      referenceField18: [''],
      referenceField19: [''],
      referenceField2: [''],
      referenceField20: [''],
      referenceField3: [''],
      referenceField4: [''],
      referenceField5: [''],
      referenceField6: [''],
      referenceField7: [''],
      referenceField8: [''],
      referenceField9: [''],
      referenceImageList: this.fb.array([]),
      volume: [''],
      volumeUnit: [''],
      weight: [''],
      weightUnit: [''],
      width: [''],
      hsCode: [''],
      pieceValue: [''],
      pieceCurrency: [''],
    });
  }

  initItemDetail() {
    return this.fb.group({
      codAmount: [''],
      declaredValue: [''],
      description: [''],
      dimensionUnit: [''],
      height: [''],
      hsCode: [''],
      imageRefId: [''],
      itemCode: [''],
      length: [''],
      partnerName: [''],
      partnerType: [''],
      pieceItemId: [''],
      referenceField1: [''],
      referenceField10: [''],
      referenceField11: [''],
      referenceField12: [''],
      referenceField13: [''],
      referenceField14: [''],
      referenceField15: [''],
      referenceField16: [''],
      referenceField17: [''],
      referenceField18: [''],
      referenceField19: [''],
      referenceField2: [''],
      referenceField20: [''],
      referenceField3: [''],
      referenceField4: [''],
      referenceField5: [''],
      referenceField6: [''],
      referenceField7: [''],
      referenceField8: [''],
      referenceField9: [''],
      referenceImageList: this.fb.array([]),
      volume: [''],
      volumeUnit: [''],
      weight: [''],
      weightUnit: [''],
      width: [''],
      quantity: [''],
      unitValue: [''],
      currency: [''],
    });
  }

  addPieceDetail() {
    const control = this.piece.controls.pieceDetails as FormArray;
    control.push(this.initPieceDetail());
  }

  removePieceDetail(index: number, data:any) {
    const control = this.piece.controls.pieceDetails as FormArray;
    control.removeAt(index);
    this.service.DeletePiece(data.getRawValue()).subscribe({next: (res) => {}})
  }

  addItemDetail(pieceIndex: number) {
    const control = (this.piece.controls.pieceDetails as FormArray).at(pieceIndex).get('itemDetails') as FormArray;
    control.push(this.initItemDetail());
  }

  removeItemDetail(pieceIndex: number, itemIndex: number) {
    const control = (this.piece.controls.pieceDetails as FormArray).at(pieceIndex).get('itemDetails') as FormArray;
    control.removeAt(itemIndex);
  }


  patchForm(shipmentData: any) {
    const piecesArray = this.piece.get('pieceDetails') as FormArray;
    shipmentData.pieceDetails.forEach((piece: any) => {
      piecesArray.push(this.patchPieceDetail(piece));
    });
  }

  patchPieceDetail(piece: any) {
    return this.fb.group({
      codAmount: [piece.codAmount],
      declaredValue: [piece.declaredValue],
      description: [piece.description],
      dimensionUnit: [piece.dimensionUnit],
      height: [piece.height],
      itemDetails: this.patchItemDetails(piece.itemDetails),
      length: [piece.length],
      packReferenceNumber: [piece.packReferenceNumber],
      masterAirwayBill: [piece.masterAirwayBill],
      houseAirwayBill: [piece.houseAirwayBill],
      partnerId: [piece.partnerId],
      partnerType: [piece.partnerType],
      pieceId: [piece.pieceId],
      referenceField1: [piece.referenceField1],
      referenceField10: [piece.referenceField10],
      referenceField11: [piece.referenceField11],
      referenceField12: [piece.referenceField12],
      referenceField13: [piece.referenceField13],
      referenceField14: [piece.referenceField14],
      referenceField15: [piece.referenceField15],
      referenceField16: [piece.referenceField16],
      referenceField17: [piece.referenceField17],
      referenceField18: [piece.referenceField18],
      referenceField19: [piece.referenceField19],
      referenceField2: [piece.referenceField2],
      referenceField20: [piece.referenceField20],
      referenceField3: [piece.referenceField3],
      referenceField4: [piece.referenceField4],
      referenceField5: [piece.referenceField5],
      referenceField6: [piece.referenceField6],
      referenceField7: [piece.referenceField7],
      referenceField8: [piece.referenceField8],
      referenceField9: [piece.referenceField9],
      referenceImageList: this.patchReferenceImages(piece.referenceImageList),
      volume: [piece.volume],
      volumeUnit: [piece.volumeUnit],
      weight: [piece.weight],
      weightUnit: [piece.weightUnit],
      width: [piece.width],
      hsCode: [piece.hsCode],
      pieceValue: [piece.pieceValue],
      pieceCurrency: [piece.pieceCurrency],
      pieceProductCode: [piece.pieceProductCode],
      tags:  [piece.tags],
    });
  }

  patchItemDetails(itemDetails: any[]) {
    return this.fb.array(itemDetails.map(item => this.patchItemDetail(item)));
  }

  patchItemDetail(item: any) {
    return this.fb.group({
      codAmount: [item.codAmount],
      declaredValue: [item.declaredValue],
      description: [item.description],
      dimensionUnit: [item.dimensionUnit],
      height: [item.height],
      hsCode: [item.hsCode],
      imageRefId: [item.imageRefId],
      itemCode: [item.itemCode],
      length: [item.length],
      partnerName: [item.partnerName],
      partnerType: [item.partnerType],
      pieceItemId: [item.pieceItemId],
      referenceField1: [item.referenceField1],
      referenceField10: [item.referenceField10],
      referenceField11: [item.referenceField11],
      referenceField12: [item.referenceField12],
      referenceField13: [item.referenceField13],
      referenceField14: [item.referenceField14],
      referenceField15: [item.referenceField15],
      referenceField16: [item.referenceField16],
      referenceField17: [item.referenceField17],
      referenceField18: [item.referenceField18],
      referenceField19: [item.referenceField19],
      referenceField2: [item.referenceField2],
      referenceField20: [item.referenceField20],
      referenceField3: [item.referenceField3],
      referenceField4: [item.referenceField4],
      referenceField5: [item.referenceField5],
      referenceField6: [item.referenceField6],
      referenceField7: [item.referenceField7],
      referenceField8: [item.referenceField8],
      referenceField9: [item.referenceField9],
      referenceImageList: this.patchReferenceImages(item.referenceImageList),
      volume: [item.volume],
      volumeUnit: [item.volumeUnit],
      weight: [item.weight],
      weightUnit: [item.weightUnit],
      width: [item.width],
      quantity: [item.quantity],
      unitValue: [item.unitValue],
      currency: [item.currency],
      masterAirwayBill: [item.masterAirwayBill],
      houseAirwayBill: [item.houseAirwayBill],
      partnerId: [item.partnerId],
    });
  }

  patchReferenceImages(referenceImageList: any[]) {
    if (referenceImageList == null) {
      return
    }
    return this.fb.array(referenceImageList.map(image => this.fb.group({
      imageRefId: [image.imageRefId],
      pdfUrl: [image.pdfUrl],
      referenceImageUrl: [image.referenceImageUrl]
    })));
  }


  form = this.fb.group({
    updatedBy: [,],
    updatedOn: ['',],
    createdOn: ['',],
    createdBy: [,],
    originDetails: [],
  });



  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandlingShipment(control: string, error: string = 'required') {
    const controlInstance = this.shipmentInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingCarrier(control: string, error: string = 'required') {
    const controlInstance = this.carrierInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingSender(control: string, error: string = 'required') {
    const controlInstance = this.senderInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingDelivery(control: string, error: string = 'required') {
    const controlInstance = this.deliveryInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingBilling(control: string, error: string = 'required') {
    const controlInstance = this.billing.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingConsignment(control: string, error: string = 'required') {
    const controlInstance = this.consignment.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Operations', 'Consignment', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.shipmentInfo.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }

    this.callCNTable();
  }

  companyIdList: any[] = [];
  districtIdList: any[] = [];
  productIdList: any[] = [];
  subProductIdList: any[] = [];
  serviceTypeIdList: any[] = [];
  consignmentTypeIdList: any[] = [];
  loadTypeIdList: any[] = [];
  countryIdListOrigin: any[] = [];
  countryIdListDestination: any[] = [];
  cityIdList: any[] = [];
  provinceIdList: any[] = [];
  partnerName: any[] = [];
  currencyDropdown: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.product.url,
      this.cas.dropdownlist.setup.subProduct.url,
      this.cas.dropdownlist.setup.serviceType.url,
      this.cas.dropdownlist.setup.consignmentType.url,
      this.cas.dropdownlist.setup.loadType.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.city.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.currency.url
    ]).subscribe({
      next: (results: any) => {
        this.companyIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.company.key);
        this.districtIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.district.key);
        this.productIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.product.key);
        this.subProductIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.subProduct.key);
        this.serviceTypeIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.serviceType.key);
        this.consignmentTypeIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.consignmentType.key);
        this.loadTypeIdList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.loadType.key);
        this.countryIdListOrigin = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.country.key);
        this.countryIdListDestination = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.country.key);
        this.cityIdList = this.cas.forLanguageFilter(results[8], this.cas.dropdownlist.setup.city.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[9], this.cas.dropdownlist.setup.province.key); 
       this.currencyDropdown = this.cas.foreachlist(results[10], this.cas.dropdownlist.setup.currency.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  partnerNameList: any[] = [];

  partnerTypeChanged() {
    if (this.shipmentInfo.controls.partnerType.value == 'customer') {
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.spin.show();
      this.customerService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: 'customerId', value: 'customerName' });
          this.partnerNameList =  this.cs.removeDuplicatesFromArrayList( this.partnerNameList, 'value');
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }

    if (this.shipmentInfo.controls.partnerType.value == 'consignor') {
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.spin.show();
      this.consignorService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: 'consignorId', value: 'consignorName' });
          this.partnerNameList =  this.cs.removeDuplicatesFromArrayList( this.partnerNameList, 'value');
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }

  consignmentCurrency:any;
  consignmentValue:any;

  fill(line: any) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));

    this.disabledConsignment = false;
    this.disabledPiece = false;
    this.disabledSender = false;
    this.disabledDelivery = false;
    this.disabledBilling = false;

      this.shipmentInfo.patchValue(line),
      this.consignment.patchValue(line),
      this.senderInfo.patchValue(line),
      this.deliveryInfo.patchValue(line),
      this.billing.patchValue(line)


      this.consignmentCurrency =  this.consignment.controls.consignmentCurrency.value;
      this.consignmentValue =  this.consignment.controls.consignmentValue.value;

    this.patchForm(line);
    if (this.consignment.controls.invoiceDate.value) {
      this.consignment.controls.invoiceDateFE.patchValue(this.cs.pCalendar(this.consignment.controls.invoiceDate.value));
    }
    this.partnerTypeChanged();

    this.shipmentInfo.controls.masterAirwayBill.disable();
    this.shipmentInfo.controls.houseAirwayBill.disable();

    this.callCNTableHeader();
    this.callItemLevel(line);
    this.callTableHeader();
  }

  billingTable:any[] = [];
  selectedConsignment:any[] = [];

  cols:any[] = [];

  cnTable: any[] = [];
  selectedCNTable: any[] = [];

  cnCols : any[] = [];


  // CN Tracking Code
  callCNTableHeader() {
    this.cnCols = [
      { field: 'houseAirwayBill', header: 'Consignment ID'},
      { field: 'hawbType', header: 'Type' },
      // { field: 'bagId', header: 'Bag ID' },
      { field: 'hawbTypeDescription', header: 'Description' },
      { field: 'hubName', header: 'Hub Name' },
      { field: 'hawbTimeStamp', header: 'Time', format: 'date' },
      { field: 'updatedBy', header: 'User' },
    ];
  }

  onRowExpand(event: TableRowExpandEvent) {
  }
  onRowCollapse(event: TableRowCollapseEvent) {
  }

  getColspan(): number {
    return this.cols.length + 2; // +1 for the expanded content column
  }


  groupByHouseAirway:any;
  groupByPieceId:any;
  callCNTable(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.houseAirwayBill = [this.pageToken.line.houseAirwayBill];

    this.service.searchStatus(obj).subscribe({next: res=> {
      this.cnTable =  res;
      this.groupByHouseAirway = this.cs.groupByDynamicField(res, 'houseAirwayBill');
      this.groupByPieceId = this.cs.groupByDynamicField(this.groupByHouseAirway[0].lines, 'pieceId')
    
      console.log(this.groupByHouseAirway)
      console.log(this.groupByPieceId)
    },error: err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }
  selectedPieceId: string | null = null;
  onPieceIdClick(pieceId: string) {
    this.selectedPieceId = pieceId;
  }
  getReorderedGroupByPieceId() {
    if (!this.selectedPieceId) {
      return this.groupByPieceId;
    }
    return [
      this.groupByPieceId.find((piece:any) => piece.pieceId === this.selectedPieceId),
      ...this.groupByPieceId.filter((piece:any) => piece.pieceId !== this.selectedPieceId)
    ];
  }

  cnTablePopup(data: any,line: any): void {
    const dialogRef = this.dialog.open(ConsignmentStatusPopupComponent, {
      disableClose: true,
      width: '80%',
      height: '50%',
      maxWidth: '95%',
      position: { top: '6.5%', left: '14%' },
      data: {pageflow: data, code: line},
    });
  
  }


  callTableHeader() {
    this.cols = [
      { field: 'consignmentCurrency', header: 'Consignment Currency', showFooter: false  },
      { field: 'consignmentValue', header: 'Consignment Value', showFooter: true  },
      { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false  },
      { field: 'iataCharge', header: 'IATA', showFooter: false  },
      { field: 'customsInsurance', header: 'Customs Insurance', showFooter: true  },
      { field: 'dutyPercentage', header: 'Duty', showFooter: true, },
      { field: 'consignmentValueLocal', header: 'Consignment Value Local', showFooter: true  },
      { field: 'addIata', header: 'Add IATA', showFooter: true  },
      { field: 'addInsurance', header: 'Add Insurance', showFooter: true  },
      { field: 'customsValue', header: 'Custom', showFooter: true  },
      { field: 'calculatedTotalDuty', header: 'Calculated Total duty', showFooter: true },
      { field: 'dduCharge', header: 'DDU Charge', showFooter: true},
      { field: 'specialApprovalCharge', header: 'Spl Approval Charge', showFooter: false },
      { field: 'totalDuty', header: 'Duty From Bayan', showFooter: false },
    ];
  }

  callItemLevel(line:any){
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId]
    obj.houseAirwayBill = [line.houseAirwayBill];

    this.service.search(obj).subscribe({next: res=> {
      this.billingTable =  res;
    },error: err => {
      this.spin.hide();
      this.cs.commonerrorNew(err); 
    }})
  }

  calculateFooterTotal(field: string): number {
    let total = 0;
    this.billingTable.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(2));
  }

  opendialog(type: any = 'New', index: any) {
    const dialogRef = this.dialog.open(ItemDetailsComponent, {
      disableClose: true,
      width: '90%',
      maxWidth: '95%',
      position: { top: '6.5%', left: '10%' },
      data: { pageflow: type, line: (this.piece.controls.pieceDetails as FormArray).at(index).get('itemDetails') as FormArray },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const itemDetailsFormArray = (this.piece.controls.pieceDetails as FormArray).at(index).get('itemDetails') as FormArray;
        itemDetailsFormArray.clear();
       const getPieceForm = (this.piece.controls.pieceDetails as FormArray).at(index);
       getPieceForm.patchValue({
        pieceValue: result.pieceValue,
        length: result.length,
        width: result.width,
        height: result.height,
        weight: result.weight,
        tags: result.tags,
        volume: result.volume,
        weightUnit: result.weightUnit,
        volumeUnit: result.volumeUnit,
        dimensionUnit: result.dimensionUnit,
        pieceCurrency: result.currency,
      });
        result.lines.forEach((item: any) => {
          itemDetailsFormArray.push(this.fb.group({
            codAmount: item.codAmount,
            declaredValue: item.declaredValue,
            description: item.description,
            dimensionUnit: item.dimensionUnit,
            height: item.height,
            hsCode: item.hsCode,
            imageRefId: item.imageRefId,
            itemCode: item.itemCode,
            length: item.length,
            partnerName: item.partnerName,
            partnerType: item.partnerType,
            pieceItemId: item.pieceItemId,
            volume: item.volume,
            volumeUnit: item.volumeUnit,
            weight: item.weight,
            weightUnit: item.weightUnit,
            width: item.width,
            quantity: item.quantity,
            unitValue: item.unitValue,
            currency: item.currency,
            masterAirwayBill: item.masterAirwayBill,
            partnerId: item.partnerId,
            houseAirwayBill: item.houseAirwayBill,
            referenceImageList: this.patchReferenceImages(item.referenceImageList),
          }));
        });
        console.log(this.piece)
      }
    });
  }

  dimension(type: any = 'New', module: any, index: any) {
    const dialogRef = this.dialog.open(DimensionComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '25%' },
      data: { pageflow: type, module: module, line: (this.piece.controls.pieceDetails as FormArray).at(index) },
    });

    dialogRef.afterClosed().subscribe(result => {
      const control = (this.piece.controls.pieceDetails as FormArray).at(index);
      control.patchValue(result);
    })
  }

  imageupload(type: any = 'New', index: any) {
    const dialogRef = this.dialog.open(ImageUploadComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '25%' },
      data: { pageflow: type, line: (this.piece.controls.pieceDetails as FormArray).at(index).get('referenceImageList') as FormArray, lineDetails: (this.piece.controls.pieceDetails as FormArray).at(index), type: 'piece' },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const imageDetailsFormArray = (this.piece.controls.pieceDetails as FormArray).at(index).get('referenceImageList') as FormArray;
        imageDetailsFormArray.clear();
        result.forEach((image: any) => {
          imageDetailsFormArray.push(this.fb.group({
            imageRefId: image.imageRefId,
            pdfUrl: image.pdfUrl,
            referenceImageUrl: image.referenceImageUrl,
          }));
        });
      }
    })
  }

  save() {
    this.submitted = true;

    if (this.form.invalid) {
      for (const control in this.form.controls) {
        const controlInstance = this.form.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          console.log(invalidControl)
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }

    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show();
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res.consignmentId + ' has been updated successfully',
          });
          this.router.navigate(['/main/master/rate']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res.consignmentId + ' has been created successfully',
            });
            this.router.navigate(['/main/master/rate']);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  calculateVolume(formName: any) {
    const volume = formName.controls.length.value as number * formName.controls.width.value as number * formName.controls.height.value as number;
    formName.controls.volume.patchValue(volume);
  }

  saveShipment() {
    this.submitted = true;
    if (this.shipmentInfo.invalid) {
      for (const control in this.shipmentInfo.controls) {
        const controlInstance = this.shipmentInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }

    if (this.shipmentInfo.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    } else {
      this.activeIndex = 1;
      this.disabledPiece = false;
      this.submitted = false;
    }
  }

  saveConsignment() {
    this.submitted = true;
    if (this.consignment.invalid) {
      for (const control in this.consignment.controls) {
        const controlInstance = this.consignment.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }

    if (this.consignment.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    } else {
      this.activeIndex = 3;
      this.submitted = false;
      this.disabledSender = false;
    }
  }
  savePiece() {

    const control = (this.piece.controls.pieceDetails as FormArray)
   if(control.value.length > 0){
  this.consignment.controls.length.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.length), 0));
  this.consignment.controls.width.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.width), 0));
  this.consignment.controls.height.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.height), 0));
  this.consignment.controls.volume.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.volume), 0));
  this.consignment.controls.weight.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.weight), 0));
  this.consignment.controls.consignmentValue.patchValue(control.value.reduce((acc:any, item:any) => parseInt(acc) + parseInt(item.pieceValue), 0));
  this.consignment.controls.volumeUnit.patchValue(control.value[0].volumeUnit);
  this.consignment.controls.dimensionUnit.patchValue(control.value[0].dimensionUnit);
  this.consignment.controls.weightUnit.patchValue(control.value[0].weightUnit);
  this.consignment.controls.consignmentCurrency.patchValue(control.value[0].pieceCurrency);
}
    this.activeIndex = 2;
    this.submitted = false;
    this.disabledConsignment = false;
  }
  saveSender() {
    this.submitted = true;
    if (this.senderInfo.invalid) {
      for (const control in this.senderInfo.controls) {
        const controlInstance = this.senderInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }

    if (this.senderInfo.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    } else {
      this.activeIndex = 4;
      this.disabledDelivery = false;
      this.submitted = false;
    }
  }
  saveDelivery() {
    this.submitted = true;
    if (this.deliveryInfo.invalid) {
      for (const control in this.deliveryInfo.controls) {
        const controlInstance = this.deliveryInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }

    if (this.deliveryInfo.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    } else {
      // if (this.pageToken.pageflow != 'New') {
        this.activeIndex = 5;
        this.submitted = false;
        this.disabledBilling = false;
      // } else {
      //   this.saveFinal();
      // }


    }
  }

  saveBilling() {
    this.submitted = true;
    if (this.billing.invalid) {
      for (const control in this.billing.controls) {
        const controlInstance = this.billing.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }
    if (this.billing.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    } else {
      this.saveFinal();
    }
  }

  mainForm: FormGroup = new FormGroup({

  });

  saveFinal() {
    this.mainForm = this.fb.group({
      ...this.shipmentInfo.getRawValue(),
      ...this.consignment.getRawValue(),
      ...this.senderInfo.getRawValue(),
      ...this.deliveryInfo.getRawValue(),
      ...this.billing.getRawValue(),
      pieceDetails: this.piece.controls.pieceDetails as FormArray,
      updatedBy: [,],
      updatedOn: ['',],
      createdOn: ['',],
      createdBy: [,],
      companyId: [this.auth.companyId,],
      languageId: [this.auth.languageId,],
      invoiceDate: this.cs.jsonDate(this.consignment.controls.invoiceDateFE.value)
    });

    if (this.pageToken.pageflow != 'New') {
      this.spin.show();
      this.service.Update([this.mainForm.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].houseAirwayBill + ' has been updated successfully',
          });
          this.spin.hide();
          this.router.navigate(['/main/operation/consignment']);
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show();
      this.service.Create([this.mainForm.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Created',
            key: 'br',
            detail: res[0].houseAirwayBill + ' has been created successfully',
          });
          this.spin.hide();
          this.router.navigate(['/main/operation/consignment']);
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }


  showPaymentTypeFields = false;
  paymentChange() {
    const paymentTypeValue = this.shipmentInfo.controls.paymentType.value;
    if (typeof paymentTypeValue === 'string' && paymentTypeValue === 'COD') {
      this.showPaymentTypeFields = true;
    }else{
      this.showPaymentTypeFields = false;
    }
  }

  subProductValueList: any[] = [];

  productChanged() {  // subProduct should be confined according to the product onChange function

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.productId = [this.shipmentInfo.controls.productId.value];

    this.subProductIdList = [];
    this.spin.show();
    this.productService.search(obj).subscribe({
      next: (result) => {
        // this.form.patchValue(result[0]);
        // this.subProductIdList = this.cas.forLanguageFilter(result, this.cas.dropdownlist.setup.subProduct.key);
        this.subProductIdList = this.cas.foreachlist(result, { key: 'subProductName', value: 'referenceField1', });
        this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, 'value');
        // this.subProductValueList = this.cas.foreachlist(result, { key: 'subProductValue', value: 'subProductValue' });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }



  selectedFiles: FileList | null = null;
  selectFiles(event: any): void {
    this.selectedFiles = event.target.files;
    this.uploadFile(event);
  }

  imageDetailsTable: any[] = [];
  fileLocation:any;
  uploadFile(event:any) {
    if (!this.selectedFiles || this.selectedFiles.length === 0) {
      console.log('No files selected for upload.');
      return;
    }
  this.fileLocation = '/Invoice/';
    this.service.uploadFiles(this.selectedFiles, this.fileLocation).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'File uploaded successfully',
        });
        this.shipmentInfo.controls.invoiceUrl.patchValue(result[0].fileName)
        this.selectedFiles = null;
        this.clearFileInput(event.target); 
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  
  clearFileInput(input: HTMLInputElement): void {
    input.value = '';
  }

  downloadInvoice(value:any){
    let obj: any = {};
    obj.value = {imageRefId: value, referenceImageUrl: '/Invoice/'}
    this.download.downloadDocument(obj);
  }

}












// saveCarrier() {
//   this.submitted = true;
//   if (this.carrierInfo.invalid) {
//     for (const control in this.carrierInfo.controls) {
//       const controlInstance = this.carrierInfo.get(control);
//       if (controlInstance?.invalid) {
//         const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
//         if (invalidControl) {
//           invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
//           break;
//         }
//       }
//     }
//   }

//   if (this.carrierInfo.invalid) {
//     this.messageService.add({
//       severity: 'error',
//       summary: 'Error',
//       key: 'br',
//       detail: 'Please fill required fields to continue',
//     });
//     return;
//   } else {
//     this.activeIndex = 6;
//     this.disabledBilling = false;
//     this.submitted = false;
//   }
// }