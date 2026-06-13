import { DatePipe } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { OverlayPanel } from 'primeng/overlaypanel';
import { CommonServiceService } from '../../../common-service/common-service.service';
import { PathNameService } from '../../../common-service/path-name.service';
import { AuthService } from '../../../core/core';
import { ConsoleService } from '../../airport/console/console.service';
import { ConsignmentService } from '../../operation/consignment/consignment.service';
import { ReportService } from '../report.service';
interface DataItem {
  companyId: string;
  languageId: string;
  partnerId: string;
  masterAirwayBill: string;
  partnerHouseAirwayBill: string;
  partnerMasterAirwayBill: string;
  partnerName: string;
  totalWeight: number;
  flightNo: string;
  consoleIndicator: string | null;
  consignmentValueLocal: number;
  manifestIndicator: string | null;
  flightName: string;
  estimatedTimeOfDeparture: string;
  estimatedTimeOfArrival: string;
  noOfPieces: string;
  consignmentValue: string; 
  exchangeRate: number;
  iata: number;
  customsInsurance: number;
  duty: number;
  addIata: number;
  addInsurance: number;
  customsValue: number;
  calculatedTotalDuty: number;
  bayanHv: string;
  currency: string;
  description: string;
  consigneeName: string;
  shipper: string;
  origin: string;
  originCode: string;
  hsCode: string;
  partnerType: string;
  incoTerm: string;
  hawbType: string | null;
  hawbTypeId: string | null;
  hawbTypeDescription: string | null;
  companyName: string;
  languageDescription: string;
  hawbTimeStamp: string;
  consignmentLocalId: string;
  houseAirwayBill: string;
  hubCode: string;
  hubName: string;
  referenceField1: string | null;
  referenceField2: string | null;
  referenceField3: string | null;
  referenceField4: string | null;
  referenceField5: string | null;
  referenceField6: string | null;
  referenceField7: string | null;
  referenceField8: string | null;
  referenceField9: string | null;
  referenceField10: string | null;
  createdBy: string;
  createdOn: string;
  updatedBy: string | null;
  updatedOn: string;
  countHawb: number;
}
@Component({
  selector: 'app-customs-calculation-report',
  templateUrl: './customs-calculation-report.component.html',
  styleUrl: './customs-calculation-report.component.scss'
})

export class CustomsCalculationReportComponent {
  inventoryScanningTable: any[] = [];
  selectedConsole: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  constructor(
    private messageService: MessageService,
    private cs: CommonServiceService,
    private router: Router,
    private path: PathNameService,
    private service: ReportService,
    private consigmentservice: ConsignmentService,
    public dialog: MatDialog,
    private datePipe: DatePipe,
    private auth: AuthService,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
  ) { }

  fullData: any;
  today: any;
  activeLink: any;
  pageFlow:any;

  ngOnInit(): void {
    const link = this.router.url;
    this.activeLink = link.split('/')[3];
      const dataToSend = ['Mid-Mile', 'Customs Calculation Report'];
      this.path.setData(dataToSend);
      this.pageFlow = 'Customs Calculation Report';
      this.tableStyle = {'width': '170rem'};
    this.callTableHeader();
    this.initialCall();
  }
  totalDeclaredValue: number = 0;
  totalCustomsValue: number = 0;
  totalDutyValue: number = 0;
  callTableHeader() {
    this.cols = [
   
    {field: 'partnerMasterAirwayBill', header: 'Partner MAWB' , format:'hyperLink'},
      { field: 'noOfShipments', header: 'No of Consignment' },
      { field: 'shipper', header: 'Shipper' },
      { field: 'totalConsignmentValue', header: 'Total Declared Value' },
      { field: 'currency', header: 'Currency' },
      { field: 'totalCustomsValue', header: 'Total Customs Value' },
      { field: 'iata', header: 'IATA Charges' },
      { field: 'customsInsurance', header: 'Insurance' },
     // { field: 'duty', header: 'Customs Duty %' },
      { field: 'totalDutyValue', header: 'Total Duty Value' },
     
    ];
    this.target = [
    ];
  }
  customsCaltulationTable:any[]=[];
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      this.service.customssearch().subscribe({
        next: (res: any[] = []) => {
          this.customsCaltulationTable = res;
          this.spin.hide();
        }, error: (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 1000); // Delay of 1000 ms for demo purposes
  }
  
  openEdit(type: any = 'New', linedata: any = null): void {
   
    if(linedata){
      this.selectedConsole = linedata;
    }
    if (this.selectedConsole.length === 0 && type != 'New') {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any Row' });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsole[0] : linedata, pageflow: type });
      this.router.navigate(['/main/reports/customsCalculationsline/' + paramdata]);
    }
  }
  
  
  
  isSelected(item: any): boolean {
    return this.selectedConsole.includes(item);
  }

  onChange() {
    const choosen = this.selectedConsole[this.selectedConsole.length - 1];
    this.selectedConsole.length = 0;
    this.selectedConsole.push(choosen);
  }
  tableStyle:any;
  downloadExcel() {

    const inventoryScanningReport = [
      {field: 'partnerMasterAirwayBill', header: 'Partner MAWB' },
      { field: 'countHawb', header: 'No of Consignment' },
      { field: 'shipper', header: 'Shipper' },
      { field: 'totalDeclaredValue', header: 'Total Declared Value' },
      { field: 'currency', header: 'Currency' },
      { field: 'totalCustomsValue', header: 'Total Customs Value' },
      { field: 'iata', header: 'IATA Charges' },
      { field: 'customsInsurance', header: 'Insurance' },
      { field: 'duty', header: 'Customs Duty %' },
      { field: 'calculatedTotalDuty', header: 'Total Duty Value' },
    ];

    const exportData = this.customsCaltulationTable.map(item => {
      const exportItem: any = {};
      inventoryScanningReport.forEach(col => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });

    // Call ExcelService to export data to Excel
    this.cs.exportAsExcel(exportData, 'Customs Calculation Report');
  }

  form = this.fb.group({
    actualCurrency: [],
    actualValue: [],
    addIata: [],
    addInsurance: [],
    airportOriginCode: [],
    bondedId: [],
    calculatedTotalDuty: [],
    companyName: [],
    consigneeCivilId: [],
    consigneeName: [],
    consignmentCurrency: [],
    consignmentLocalId: [],
    consignmentValue: [],
    consignmentValueLocal: [],
    consoleGroupName: [],
    consoleId: [],
    consoleName: [],
    countryOfOrigin: [],
    createdBy: [],
    createdOn: [],
    currency: [],
    customsCurrency: [],
    customsInsurance: [],
    customsKd: [],
    customsValue: [],
    dduCharge: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    duty: [],
    dutyPercentage: [],
    exchangeRate: [],
    exemptionBeneficiary: [],
    exemptionFor: [],
    exemptionReference: [],
    expectedDuty: [],
    finalDestination: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    grossWeight: [],
    hawbTimeStamp: [],
    hawbType: [],
    hawbTypeDescription: [],
    hawbTypeId: [],
    hsCode: [],
    hubCode: [],
    iata: [],
    iataCharge: [],
    incoTerms: [],
    invoiceDate: [],
    invoiceNumber: [],
    invoiceSupplierName: [],
    invoiceType: [],
    isConsolidatedShipment: [],
    isExempted: [],
    isPendingShipment: [],
    isSplitBillOfLading: [],
    landedQuantity: [],
    languageDescription: [],
    companyId: [[this.auth.companyId],],
  languageId: [[this.auth.languageId],],
    manifestedGrossWeight: [],
    manifestedQuantity: [],
    netWeight: [],
    noOfPackageMawb: [],
    noOfPieceHawb: [],
    noOfPieces: [],
    notifyParty: [],
    partnerHouseAirwayBill: [],
    partnerId: [],
    partnerMasterAirwayBill: [],
    partnerName: [],
    partnerType: [],
    paymentType: [],
    pieceId: [],
    pieceTimeStamp: [],
    pieceType: [],
    pieceTypeDescription: [],
    pieceTypeId: [],
    primaryDo: [],
    productId: [],
    productName: [],
    quantity: [],
    referenceField1: [],
    referenceField10: [],
    referenceField11: [],
    referenceField12: [],
    referenceField13: [],
    referenceField14: [],
    referenceField15: [],
    referenceField16: [],
    referenceField17: [],
    referenceField18: [],
    referenceField19: [],
    referenceField2: [],
    referenceField20: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    remarks: [],
    secondaryDo: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipmentBagId: [],
    shipperId: [],
    shipperName: [],
    specialApprovalCharge: [],
    specialApprovalValue: [],
    subProductId: [],
    subProductName: [],
    tareWeight: [],
    totalQuantity: [],
    updatedBy: [],
    updatedOn: [],
    volume: [],
    statusText: [],
    statusId: [],

  });

  readonly fieldDisplayNames: Record<string, string> = {
    partnerHouseAirwayBill: 'Partner HAWB',
    partnerMasterAirwayBill: 'Partner MAWB',
    hawbTypeId: 'Action',
    hawbTimeStamp: 'Date Time'
  };

  houseAirwayBillDropdown: any[] = [];
  masterAirwayBillDropdown: any = [];
  statusDropdown: any[] = [];
  timeStampDropdown: any[] = [];

  getSearchDropdown() {

    this.customsCaltulationTable.forEach(res => {

      if (res.partnerHouseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, 'value');
      }
      if (res.partnerMasterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, 'value');
      }
      if (res.hawbTypeId != null) {
        this.statusDropdown = [{ value: 6, label: '6 - Pending Customs' }, { value: 47, label: '47 - Gateway Inventory' }]
      }
      if (res.hawbTimeStamp != null) {
        const formattedDate = this.datePipe.transform(res.hawbTimeStamp, 'MMM d, y, h:mm a');
        this.timeStampDropdown.push({ value: res.hawbTimeStamp, label: formattedDate });
        this.timeStampDropdown = this.cs.removeDuplicatesFromArrayList(this.timeStampDropdown, 'value');
      }
    })

  }

  @ViewChild('inventoryScanningReport') overlayPanel!: OverlayPanel;
  closeOverLay() {
    this.overlayPanel.hide();
  }

  fieldsWithValue: any;

  search() {
    this.fieldsWithValue = null;
    const formValues = this.form.value;
    this.fieldsWithValue = Object.keys(formValues)
      .filter(key => formValues[key as keyof typeof formValues] !== null && formValues[key as keyof typeof formValues] !== undefined && key !== 'companyId' && key !== 'languageId')
      .map(key => this.fieldDisplayNames[key] || key);

    this.spin.show();
    this.consigmentservice.searchPrealert(this.form.getRawValue()).subscribe({
      next: (res: DataItem[]) => {
        let totalDeclaredValue = 0;
        let totalCustomsValue = 0;
        let totalDutyValue=0;
        const groupedData = res.reduce((acc, item) => {
          if (!acc[item.partnerMasterAirwayBill]) {
            acc[item.partnerMasterAirwayBill] = {
              ...item,
              totalDeclaredValue: 0,
              totalCustomsValue: 0,
              totalDutyValue:0,
            };
          }
          acc[item.partnerMasterAirwayBill].totalDeclaredValue += parseFloat(String(item.consignmentValue || '0'));
          acc[item.partnerMasterAirwayBill].totalCustomsValue += parseFloat(String(item.customsValue || '0'));
          acc[item.partnerMasterAirwayBill].totalDutyValue += parseFloat(String(item.calculatedTotalDuty || '0'));
          acc[item.partnerMasterAirwayBill].totalDeclaredValue = Number((acc[item.partnerMasterAirwayBill].totalDeclaredValue.toFixed(3)));
          acc[item.partnerMasterAirwayBill].totalCustomsValue = Number((acc[item.partnerMasterAirwayBill].totalCustomsValue.toFixed(3)));
          acc[item.partnerMasterAirwayBill].totalDutyValue = Number((acc[item.partnerMasterAirwayBill].totalDutyValue.toFixed(3)));

          return acc;
        }, {} as Record<string, { totalDeclaredValue: number; totalCustomsValue: number;totalDutyValue:number; [key: string]: any }>);

        // Convert grouped data to an array
        const processedData = Object.values(groupedData);

        // Calculate and round overall totals
        totalDeclaredValue = processedData.reduce((sum, item) => sum + item.totalDeclaredValue, 0);
        totalCustomsValue = processedData.reduce((sum, item) => sum + item.totalCustomsValue, 0);
        totalDutyValue=processedData.reduce((sum, item) => sum + item.totalDutyValue, 0);
        totalDeclaredValue = Number(totalDeclaredValue.toFixed(3));
        totalCustomsValue = Number(totalCustomsValue.toFixed(3));
        totalDutyValue=Number(totalCustomsValue.toFixed(3))

        this.totalDeclaredValue = totalDeclaredValue;
        this.totalCustomsValue = totalCustomsValue;
        this.totalDutyValue=totalDutyValue;
        this.totalDeclaredValue=this.totalDeclaredValue;
        this.customsCaltulationTable = this.cs.removeDuplicatesFromArrayList(processedData, 'partnerMasterAirwayBill');
        this.spin.hide();
        this.overlayPanel.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
     
    });
   
  };

  reset() {
    this.form.reset();
    this.search();
  }

  chipClear(value: any) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find(key => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.form.get(formControlKey)?.reset();
      this.search();
    }
  }

}

