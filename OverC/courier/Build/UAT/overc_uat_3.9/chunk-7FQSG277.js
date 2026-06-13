import {
  require_JsBarcode
} from "./chunk-5BHKZR5U.js";
import {
  ConsignmentService,
  iwExpressLogo,
  iwExpressLogo3,
  require_pdfmake,
  require_vfs_fonts
} from "./chunk-S73KOQBB.js";
import {
  AuthService,
  CommonServiceService,
  MessageService,
  NgxSpinnerService
} from "./chunk-XFYC4BWK.js";
import {
  DatePipe,
  DomSanitizer,
  HttpClient,
  __async,
  __toESM,
  ɵsetClassDebugInfo,
  ɵɵdefineComponent,
  ɵɵdefineInjectable,
  ɵɵdirectiveInject,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵinject,
  ɵɵtext
} from "./chunk-Z5YEPTLN.js";

// src/app/main/airport/ccr/ccr.service.ts
var _CcrService = class _CcrService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(partnerId) {
    return this.http.get("/overc-midmile-service/ccr/" + partnerId);
  }
  Create(obj) {
    return this.http.post("/overc-midmile-service/ccr/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-midmile-service/ccr/" + obj.partnerId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&cityId=" + obj.cityId, obj);
  }
  UpdateList(obj) {
    return this.http.patch("/overc-midmile-service/ccr/update/list", obj);
  }
  Delete(obj) {
    return this.http.post("/overc-midmile-service/ccr/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-midmile-service/ccr/findCcr", obj);
  }
  genearateLabel(obj) {
    return this.http.post("/overc-midmile-service/pdf/Label", obj);
  }
  genearateInvoice(obj) {
    return this.http.post("/overc-midmile-service/consignment/findConsignmentInvoice", obj);
  }
  uploadBayan(file, filePath) {
    const formData = new FormData();
    formData.append("file", file);
    return this.http.post("/pdf/extract?filePath=" + filePath, formData);
  }
  uploadfile(file, location) {
    let formParams = new FormData();
    formParams.append("file", file);
    return this.http.post(`/doc-storage/multiUpload?location=${location}`, formParams);
  }
};
_CcrService.\u0275fac = function CcrService_Factory(t) {
  return new (t || _CcrService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CcrService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CcrService, factory: _CcrService.\u0275fac, providedIn: "root" });
var CcrService = _CcrService;

// src/app/main/pdf/consignment-label/consignment-label.component.ts
var import_pdfmake = __toESM(require_pdfmake());
var import_vfs_fonts = __toESM(require_vfs_fonts());
var import_jsbarcode = __toESM(require_JsBarcode());
import_pdfmake.default.vfs = import_vfs_fonts.default.pdfMake.vfs;
import_pdfmake.default.fonts = {
  Roboto: {
    normal: "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf",
    bold: "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Medium.ttf"
  }
};
var _ConsignmentLabelComponent = class _ConsignmentLabelComponent {
  constructor(datePipe, ccrService, consginementService, sanitizer, messageService, spin, cs) {
    this.datePipe = datePipe;
    this.ccrService = ccrService;
    this.consginementService = consginementService;
    this.sanitizer = sanitizer;
    this.messageService = messageService;
    this.spin = spin;
    this.cs = cs;
    this.fileNameList = [];
  }
  generateBarcode(text) {
    const canvas = document.createElement("canvas");
    (0, import_jsbarcode.default)(canvas, text, { height: 80 });
    return canvas.toDataURL("image/png");
  }
  generatePdfBarocde(line, type) {
    if (line.pieceDetails.length <= 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "No data found" });
      return;
    }
    let createdOn = this.datePipe.transform(line.createdOn, "dd-MMM-yyyy HH:mm");
    var dd;
    dd = {
      pageSize: "A6",
      pageOrientation: "portrait",
      pageMargins: [10, 0, 10, 10],
      // header(currentPage: number, pageCount: number, pageSize: any): any {
      //   return [
      //     {
      //       table: {
      //         headerRows: 1,
      //         widths: ['*', '*'],
      //         body: headerTable
      //       },
      //       margin: [5, 5, 5, 5]
      //     }
      //   ]
      // },
      styles: {
        anotherStyle: {
          bordercolor: "#6102D3"
        }
      },
      footer(currentPage, pageCount, pageSize) {
        return [{
          text: "",
          style: "header",
          alignment: "center",
          bold: true,
          fontSize: 6
        }];
      },
      content: ["\n"]
    };
    line.pieceDetails.forEach((x, i) => {
      let barcodeAWB = [];
      if (i != 0) {
        const barcodeImageData1 = this.generateBarcode(line.houseAirwayBill);
        barcodeAWB.push([
          { image: iwExpressLogo.headerLogo, margin: [0, 4, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { image: barcodeImageData1, margin: [0, 4, 0, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
          //{ image: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
        ]);
      } else {
        const barcodeImageData1 = this.generateBarcode(line.houseAirwayBill);
        barcodeAWB.push([
          { image: iwExpressLogo.headerLogo, margin: [0, -5, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { image: barcodeImageData1, margin: [0, -5, 0, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
          //{ image: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
        ]);
      }
      dd.content.push({
        table: {
          headerRows: 1,
          //   widths: [95, 80, '*'],
          widths: [95, 80],
          body: barcodeAWB
        }
      }, "\n");
      let bodyArray = [];
      bodyArray.push([
        { text: "Label Date", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: createdOn, bold: false, fontSize: 6, border: [false, false, false, true] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: "Cus Ref No", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: line.partnerHouseAirwayBill, bold: false, fontSize: 6, border: [false, false, false, true] }
      ]);
      bodyArray.push([
        { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: line.countryOfOrigin, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.country, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Cust Name", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.partnerName, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Dest State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.state, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Mode", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.modeOfTransport, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Dest City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.city, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Declared Value", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.pieceValue, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Inco-Terms", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.incoTerms, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Load Type ", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.loadType, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Weight", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.grossWeight, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "COD", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.paymentType == "COD" ? "Yes" : "No", bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Service Type", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.serviceTypeText, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Insurance", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.insurance, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Customs Charge", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.totalDuty, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Piece Count", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.noOfPieceHawb, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Currency Code", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.consignmentCurrency, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "No.of items in Piece", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: x.tags, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Item Description", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.pieceProductCode, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray
        }
      });
      let bodyArray2 = [];
      bodyArray2.push([
        { text: "Shipper Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: line.originDetails.name, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: line.originDetails.country, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
      ]);
      bodyArray2.push([
        { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.originDetails.state, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.originDetails.city, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray2.push([
        { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.originDetails.phone, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.originDetails.alternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray2
        }
      });
      let bodyArray3 = [];
      bodyArray3.push([
        { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: (line.originDetails.addressLine1 != null ? line.originDetails.addressLine1 : "") + " " + (line.originDetails.addressLine2 != null ? line.originDetails.addressLine2 : ""), bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, "*"],
          body: bodyArray3
        }
      });
      let bodyArray4 = [];
      bodyArray4.push([
        { text: "Recipient Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: line.destinationDetails.name, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: line.destinationDetails.country, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
      ]);
      bodyArray4.push([
        { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.state, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.city, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray4.push([
        { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.phone, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: line.destinationDetails.alternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray4
        }
      });
      let bodyArray5 = [];
      bodyArray5.push([
        { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: (line.destinationDetails.addressLine1 != null ? line.destinationDetails.addressLine1 : "") + " " + (line.destinationDetails.addressLine2 != null ? line.destinationDetails.addressLine2 : ""), bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, "*"],
          body: bodyArray5
        }
      });
      let pieceId = [];
      const pieceIdcode = this.generateBarcode(line.pieceDetails.length > 0 ? x.pieceId : null);
      const partnercode = this.generateBarcode(line.partnerHouseAirwayBill);
      pieceId.push([
        { text: "Piece Id", bold: true, alignment: "left", margin: [0, 5, 0, 0], fontSize: 6, border: [false, true, false, false] }
        //   { text: 'Partner AWB', bold: true, alignment: 'right', margin: [0, 5, 0, 0], fontSize: 6, border: [false, true, false, false] },
      ]);
      pieceId.push([
        { image: pieceIdcode, margin: [0, -5, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] }
        // { image: partnercode, margin: [0, -5, 0, 0], fit: [80, 80], alignment: 'right', bold: false, fontSize: 12, border: [false, false, false, false] },
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: ["*"],
          body: pieceId
        }
      }, "\n");
    });
    if (type == "download") {
      import_pdfmake.default.createPdf(dd).download(line.houseAirwayBill);
    } else {
      import_pdfmake.default.createPdf(dd).print();
    }
  }
  getResultLabel(labelResult) {
    this.ccrService.genearateLabel({ pieceId: labelResult }).subscribe({
      next: (res) => {
        if (res.length > 0) {
          this.generateSingleLabel(res);
        } else {
          this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "No data found" });
        }
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
      }
    });
  }
  getResultLabelFromConsignment(labelResult) {
    this.ccrService.genearateLabel({ houseAirwayBill: labelResult }).subscribe({
      next: (res) => {
        this.generateSingleLabel(res);
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
      }
    });
  }
  generateSingleLabel(result) {
    var dd;
    let headerTable = [];
    dd = {
      pageSize: "A6",
      pageOrientation: "portrait",
      pageMargins: [10, 10, 10, 10],
      styles: {
        anotherStyle: {
          bordercolor: "#6102D3"
        }
      },
      footer(currentPage, pageCount, pageSize) {
        return [{
          text: "",
          style: "header",
          alignment: "center",
          bold: true,
          fontSize: 6
        }];
      },
      content: ["\n"]
    };
    result.forEach((resultLabel, index) => {
      let createdOn = this.datePipe.transform(resultLabel.createdOn, "dd-MMM-yyyy HH:mm");
      let barcodeAWB = [];
      if (index != 0) {
        const barcodeImageData1 = this.generateBarcode(resultLabel.houseAirwayBill);
        barcodeAWB.push([
          { image: iwExpressLogo.headerLogo, margin: [0, 4, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { image: barcodeImageData1, margin: [0, 4, 0, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
          //{ image: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
        ]);
      } else {
        const barcodeImageData1 = this.generateBarcode(resultLabel.houseAirwayBill);
        barcodeAWB.push([
          { image: iwExpressLogo.headerLogo, margin: [0, -10, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { image: barcodeImageData1, margin: [0, -10, 0, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
          //{ image: '', margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
        ]);
      }
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [95, 80, "*"],
          body: barcodeAWB
        }
      }, "\n");
      let bodyArray = [];
      bodyArray.push([
        { text: "Label Date", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: createdOn, bold: false, fontSize: 6, border: [false, false, false, true] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: "Cus Ref No", bold: true, fontSize: 6, border: [false, false, false, true] },
        { text: resultLabel.customerReferenceNumber, bold: false, fontSize: 6, border: [false, false, false, true] }
      ]);
      bodyArray.push([
        { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.countryOfOrigin, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationCountry, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Cust Name", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.partnerName, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Dest State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originState, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Mode", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.modeOfTransport, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Dest City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationCity, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Declared Value", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.pieceValue, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Inco-Terms", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.incoTerms, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Load Type ", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.loadType, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Weight", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.grossWeight, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "COD", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.paymentType == "COD" ? "Yes" : "No", bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Service Type", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.serviceTypeText, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Insurance", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.insurance, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Customs Charge", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.totalDuty, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "Piece Count", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.noOfPieceHawb, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Currency Code", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.consignmentCurrency, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray.push([
        { text: "No.of items in Piece", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.tags, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Item Description", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.goodsType, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray
        }
      });
      let bodyArray2 = [];
      bodyArray2.push([
        { text: "Shipper Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: resultLabel.originName, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: resultLabel.countryOfOrigin, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
      ]);
      bodyArray2.push([
        { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originState, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originCity, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray2.push([
        { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originPhone, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originAlternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray2
        }
      });
      let bodyArray3 = [];
      bodyArray3.push([
        { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.originAddress, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, "*"],
          body: bodyArray3
        }
      });
      let bodyArray4 = [];
      bodyArray4.push([
        { text: "Recipient Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: resultLabel.destinationName, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
        { text: resultLabel.destinationCountry, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
      ]);
      bodyArray4.push([
        { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationState, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationCity, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      bodyArray4.push([
        { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationPhone, bold: false, fontSize: 6, border: [false, false, false, false] },
        { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationAlternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, 60, 5, 60, 60],
          body: bodyArray4
        }
      });
      let bodyArray5 = [];
      bodyArray5.push([
        { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
        { text: resultLabel.destinationAddress, bold: false, fontSize: 6, border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [60, "*"],
          body: bodyArray5
        }
      });
      let pieceId = [];
      const pieceIdcode = this.generateBarcode(resultLabel.pieceId);
      const partnercode = this.generateBarcode(resultLabel.partnerHouseAirwayBill);
      pieceId.push([
        { text: "Piece Id", bold: true, alignment: "left", margin: [0, 5, 0, 0], fontSize: 6, border: [false, true, false, false] }
        //     { text: 'Partner AWB', bold: true, alignment: 'right', margin: [0, 5, 0, 0], fontSize: 6, border: [false, true, false, false] },
      ]);
      pieceId.push([
        { image: pieceIdcode, margin: [0, -5, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] }
        // { image: partnercode, margin: [0, -5, 0, 0], fit: [80, 80], alignment: 'right', bold: false, fontSize: 12, border: [false, false, false, false] },
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: ["*"],
          body: pieceId
        }
      }, "\n");
      if (index + 1 != result.length) {
        dd.content.push({
          text: "",
          pageBreak: "after"
          // Page break after this element
        });
      }
    });
    const pdfDocGenerator = import_pdfmake.default.createPdf(dd);
    pdfDocGenerator.getBlob((blob) => {
      var file = new File([blob], "Label" + result[0].houseAirwayBill);
      if (file) {
        this.consginementService.downloadCompressedFile(file).subscribe((blob2) => {
          const url = window.URL.createObjectURL(blob2);
          const a = document.createElement("a");
          a.href = url;
          a.download = "Label" + result[0].houseAirwayBill;
          a.click();
          URL.revokeObjectURL(this.docurl);
        }, (error) => {
          console.error("Error:", error);
        });
      }
    });
  }
  getResultInvoice(invoiceResult) {
    this.spin.show();
    this.ccrService.genearateInvoice({ houseAirwayBill: invoiceResult }).subscribe({
      next: (res) => {
        this.generateSingleInvoice(res);
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    });
  }
  generateSingleInvoice(result) {
    var dd;
    let headerTable = [];
    headerTable.push([
      { image: iwExpressLogo.headerLogo, fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] }
    ]);
    dd = {
      pageSize: "A6",
      pageOrientation: "portrait",
      pageMargins: [10, 10, 10, 10],
      styles: {
        anotherStyle: {
          bordercolor: "#6102D3"
        }
      },
      footer(currentPage, pageCount, pageSize) {
        return [{
          text: "",
          style: "header",
          alignment: "center",
          bold: true,
          fontSize: 6
        }];
      },
      content: ["\n"]
    };
    result.forEach((resultInvoice, index) => {
      let createdOn = this.datePipe.transform(resultInvoice.createdOn, "dd-MMM-yyyy HH:mm");
      let barcodeAWB = [];
      if (index != 0) {
        barcodeAWB.push([
          { text: "", margin: [0, 4, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { text: "Invoice", margin: [0, 4, 15, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 13, border: [false, false, false, false] },
          { text: "", margin: [0, 4, 0, 0], fit: [50, 50], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
        ]);
      } else {
        barcodeAWB.push([
          { text: "", margin: [0, -15, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
          { text: "Invoice", margin: [0, -10, 15, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 13, border: [false, false, false, false] },
          { text: "", margin: [0, -10, 0, 0], fit: [50, 50], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
        ]);
      }
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [95, 80, "*"],
          body: barcodeAWB
        }
      }, "\n");
      let bodyArray67 = [];
      bodyArray67.push([
        { text: "Sender", bold: true, fontSize: 6, alignment: "center", border: [true, true, true, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "", bold: false, fontSize: 6, alignment: "center", border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Receiver", bold: true, fontSize: 6, alignment: "center", border: [true, true, true, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [127, "*", 127],
          body: bodyArray67
        }
      }, "\n");
      let bodyArray = [];
      bodyArray.push([
        { text: "1." + resultInvoice.originAddress, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "1." + resultInvoice.destinationAddress, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [127, "*", 127],
          body: bodyArray
        },
        margin: [0, -14, 0, 0]
      });
      let bodyArray88 = [];
      bodyArray88.push([
        { text: "City", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.orgCity, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Country", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.orgCountry, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "City ", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.destCity, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Country", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.destCountry, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [30, 30, 22, 18, "*", 30, 30, 22, 18],
          body: bodyArray88
        },
        margin: [0, -1, 0, 0]
      });
      let bodyArray97 = [];
      bodyArray97.push([
        { text: "Telephone", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.orgPhone, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Telephone", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.destPhone, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [59, 59, "*", 59, 59],
          body: bodyArray97
        },
        margin: [0, -1, 0, 0]
      });
      let bodyArray2 = [];
      bodyArray2.push([
        { text: "QTY", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "HS Code", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Description", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Weight", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Unit Value", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Total Value", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Currency", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Country of Origin", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "INCO TERMS", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [22, 22, 22, 22, 22, 22, 22, 22, 23],
          body: bodyArray2
        },
        margin: [0, 20, 0, 0]
      });
      for (let i = 0; i < resultInvoice.invoiceFormLines.length; i++) {
        let bodyArray6 = [];
        bodyArray6.push([
          { text: resultInvoice.quantity, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.invoiceFormLines[i].hsCode, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.invoiceFormLines[i].goodsDescription, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.invoiceFormLines[i].itemWeight, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.invoiceFormLines[i].unitValue, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.invoiceFormLines[i].totalValue, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.currency, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.countryOfOrigin, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
          { text: resultInvoice.incoTerms, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
        ]);
        dd.content.push({
          table: {
            headerRows: 1,
            widths: [22, 22, 22, 22, 22, 22, 22, 22, 23],
            body: bodyArray6
          },
          margin: [0, -1, 0, 0]
        });
      }
      let bodyArray3 = [];
      bodyArray3.push([
        { text: "Piece", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.pieces, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "DATE", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: createdOn, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      bodyArray3.push([
        { text: "Weight", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.weight, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "AWB", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.awb, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      bodyArray3.push([
        { text: "Total Commerical Invoice Value", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.totalCiv, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: "Prepaid", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: resultInvoice.prepaid, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [61, 61, 61, 61],
          body: bodyArray3
        },
        margin: [0, 20, 0, 0]
      });
      let bodyArray125 = [];
      bodyArray125.push([
        { text: "I DECLARE THAT ABOVE INFORMATION IS TRUE AND CORRECT TO MY KNOWLEDGE", bold: true, fontSize: 6, alignment: "center", border: [false, false, false, false] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: ["*"],
          body: bodyArray125
        },
        margin: [0, 30, 0, 0]
      });
      if (index + 1 != result.length) {
        dd.content.push({
          text: "",
          pageBreak: "after"
          // Page break after this element
        });
      }
    });
    this.spin.hide();
    import_pdfmake.default.createPdf(dd).download("_Invoice_" + result[0].houseAirwayBill);
  }
  generateMutiple(labelResult, invoiceResult) {
    this.fileNameList = [];
    this.spin.show();
    this.ccrService.genearateLabel({ pieceId: labelResult }).subscribe({
      next: (res) => {
        if (res.length <= 0) {
          this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "No data found" });
          this.spin.hide();
          return;
        }
        res.forEach((x, i) => {
          this.generateMutipleLabel(x);
          if (i + 1 == res.length) {
            this.ccrService.genearateInvoice({ houseAirwayBill: invoiceResult }).subscribe({
              next: (res2) => {
                if (res2.length <= 0) {
                  this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "No data found" });
                  this.spin.hide();
                  return;
                }
                res2.forEach((x2, i2) => {
                  this.generateMultipleInvoice(x2, i2 + 1, res2.length);
                });
              },
              error: (err) => {
                this.cs.commonerrorNew(err);
                this.spin.hide();
              }
            });
          }
        });
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
      }
    });
  }
  generateMultipleInvoice(result, index, resultIndex) {
    let createdOn = this.datePipe.transform(result.createdOn, "dd-MMM-yyyy HH:mm");
    var dd;
    let headerTable = [];
    headerTable.push([
      { image: iwExpressLogo.headerLogo, fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] }
    ]);
    dd = {
      pageSize: "A6",
      pageOrientation: "portrait",
      pageMargins: [10, 10, 10, 10],
      styles: {
        anotherStyle: {
          bordercolor: "#6102D3"
        }
      },
      footer(currentPage, pageCount, pageSize) {
        return [{
          text: "",
          style: "header",
          alignment: "center",
          bold: true,
          fontSize: 6
        }];
      },
      content: ["\n"]
    };
    let barcodeAWB = [];
    const barcodeImageData1 = this.generateBarcode(result.houseAirwayBill);
    if (index != 0) {
      barcodeAWB.push([
        { text: "", margin: [0, 4, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
        { text: "Invoice", margin: [0, 4, 15, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 13, border: [false, false, false, false] },
        { text: "", margin: [0, 4, 0, 0], fit: [50, 50], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
      ]);
    } else {
      barcodeAWB.push([
        { text: "", margin: [0, -15, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
        { text: "Invoice", margin: [0, -10, 15, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 13, border: [false, false, false, false] },
        { text: "", margin: [0, -10, 0, 0], fit: [50, 50], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
      ]);
    }
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [95, 80, "*"],
        body: barcodeAWB
      }
    }, "\n");
    let bodyArray67 = [];
    bodyArray67.push([
      { text: "Sender", bold: true, fontSize: 6, alignment: "center", border: [true, true, true, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "", bold: false, fontSize: 6, alignment: "center", border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Receiver", bold: true, fontSize: 6, alignment: "center", border: [true, true, true, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [127, "*", 127],
        body: bodyArray67
      }
    }, "\n");
    let bodyArray = [];
    bodyArray.push([
      { text: "1." + result.originAddress, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "1." + result.destinationAddress, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [127, "*", 127],
        body: bodyArray
      },
      margin: [0, -14, 0, 0]
    });
    let bodyArray88 = [];
    bodyArray88.push([
      { text: "City", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.orgCity, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Country", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.orgCountry, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "City ", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.destCity, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Country", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.destCountry, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [30, 30, 22, 18, "*", 30, 30, 22, 18],
        body: bodyArray88
      },
      margin: [0, -1, 0, 0]
    });
    let bodyArray97 = [];
    bodyArray97.push([
      { text: "Telephone", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.orgPhone, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "", bold: false, fontSize: 6, border: [false, false, false, false], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Telephone", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.destPhone, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [59, 59, "*", 59, 59],
        body: bodyArray97
      },
      margin: [0, -1, 0, 0]
    });
    let bodyArray2 = [];
    bodyArray2.push([
      { text: "QTY", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "HS Code", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Description", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Weight", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Unit Value", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Total Value", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Currency", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Country of Origin", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "INCO TERMS", bold: true, margin: [0, 2, 0, 0], alignment: "left", fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [22, 22, 22, 22, 22, 22, 22, 22, 23],
        body: bodyArray2
      },
      margin: [0, 20, 0, 0]
    });
    for (let i = 0; i < result.invoiceFormLines.length; i++) {
      let bodyArray6 = [];
      bodyArray6.push([
        { text: result.quantity, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.invoiceFormLines[i].hsCode, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.invoiceFormLines[i].goodsDescription, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.invoiceFormLines[i].itemWeight, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.invoiceFormLines[i].unitValue, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.invoiceFormLines[i].totalValue, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.currency, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.countryOfOrigin, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
        { text: result.incoTerms, bold: true, margin: [0, 2, 0, 0], fontSize: 5, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
      ]);
      dd.content.push({
        table: {
          headerRows: 1,
          widths: [22, 22, 22, 22, 22, 22, 22, 22, 23],
          body: bodyArray6
        },
        margin: [0, -1, 0, 0]
      });
    }
    let bodyArray3 = [];
    bodyArray3.push([
      { text: "Piece", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.pieces, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "DATE", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: createdOn, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    bodyArray3.push([
      { text: "Weight", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.weight, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "AWB", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.awb, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    bodyArray3.push([
      { text: "Total Commerical Invoice Value", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.totalCiv, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: "Prepaid", bold: true, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] },
      { text: result.prepaid, bold: false, fontSize: 6, border: [true, true, true, true], borderColor: ["#808080", "#808080", "#808080", "#808080"] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [61, 61, 61, 61],
        body: bodyArray3
      },
      margin: [0, 20, 0, 0]
    });
    let bodyArray125 = [];
    bodyArray125.push([
      { text: "I DECLARE THAT ABOVE INFORMATION IS TRUE AND CORRECT TO MY KNOWLEDGE", bold: true, fontSize: 6, alignment: "center", border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: ["*"],
        body: bodyArray125
      },
      margin: [0, 30, 0, 0]
    });
    const pdfDocGenerator = import_pdfmake.default.createPdf(dd);
    pdfDocGenerator.getBlob((blob) => {
      var file = new File([blob], result.houseAirwayBill + "_invoice.pdf");
      var filepath = result.houseAirwayBill + "/invoice/";
      if (file) {
        this.consginementService.uploadsinglefile(file, filepath).subscribe((resp) => {
          this.fileNameList.push(resp.location + resp.file);
          if (index == resultIndex) {
            this.sortArray(this.fileNameList, result);
          }
        });
      }
    });
  }
  generateMutipleLabel(result) {
    let createdOn = this.datePipe.transform(result.createdOn, "dd-MMM-yyyy HH:mm");
    var dd;
    let headerTable = [];
    headerTable.push([
      { image: iwExpressLogo.headerLogo, fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] }
    ]);
    dd = {
      pageSize: "A6",
      pageOrientation: "portrait",
      pageMargins: [10, 10, 10, 10],
      styles: {
        anotherStyle: {
          bordercolor: "#6102D3"
        }
      },
      footer(currentPage, pageCount, pageSize) {
        return [{
          text: "",
          style: "header",
          alignment: "center",
          bold: true,
          fontSize: 6
        }];
      },
      content: ["\n"]
    };
    let barcodeAWB = [];
    const barcodeImageData1 = this.generateBarcode(result.houseAirwayBill);
    barcodeAWB.push([
      { image: iwExpressLogo3.headerLogo, margin: [0, -15, 0, 0], fit: [80, 80], alignment: "left", bold: false, fontSize: 12, border: [false, false, false, false] },
      { image: barcodeImageData1, margin: [0, -15, 0, 0], fit: [100, 100], alignment: "center", bold: false, fontSize: 12, border: [false, false, false, false] }
      ///  { image: iwExpressLogo.jntLogo, margin: [0, -10, 0, 0], fit: [50, 50], alignment: 'center', bold: false, fontSize: 12, border: [false, false, false, false] },
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [95, 80],
        body: barcodeAWB
      }
    }, "\n");
    let bodyArray = [];
    bodyArray.push([
      { text: "Label Date", bold: true, fontSize: 6, border: [false, false, false, true] },
      { text: createdOn, bold: false, fontSize: 6, border: [false, false, false, true] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, true] },
      { text: "Cus Ref No", bold: true, fontSize: 6, border: [false, false, false, true] },
      { text: result.customerReferenceNumber, bold: false, fontSize: 6, border: [false, false, false, true] }
    ]);
    bodyArray.push([
      { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
      { text: result.countryOfOrigin, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
      { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationCountry, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Cust Name", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.partnerName, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Dest State", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originState, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Mode", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.modeOfTransport, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Dest City", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationCity, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Declared Value", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.pieceValue, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Inco-Terms", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.incoTerms, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Load Type ", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.loadType, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Weight", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.grossWeight, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "COD", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.paymentType == "COD" ? "Yes" : "No", bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Service Type", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.serviceTypeText, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Insurance", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.insurance, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Customs Charge", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.totalDuty, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "Piece Count", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.noOfPieceHawb, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Currency Code", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.consignmentCurrency, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray.push([
      { text: "No.of items in Piece", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.tags, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Item Description", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.goodsType, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [60, 60, 5, 60, 60],
        body: bodyArray
      }
    });
    let bodyArray2 = [];
    bodyArray2.push([
      { text: "Shipper Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: result.originName, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: "Org Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: result.countryOfOrigin, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
    ]);
    bodyArray2.push([
      { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originState, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originCity, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray2.push([
      { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originPhone, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originAlternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [60, 60, 5, 60, 60],
        body: bodyArray2
      }
    });
    let bodyArray3 = [];
    bodyArray3.push([
      { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.originAddress, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [60, "*"],
        body: bodyArray3
      }
    });
    let bodyArray4 = [];
    bodyArray4.push([
      { text: "Recipient Name", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: result.destinationName, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: "", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: "Dest Country", bold: true, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] },
      { text: result.destinationCountry, bold: false, margin: [0, 2, 0, 0], fontSize: 6, border: [false, true, false, false] }
    ]);
    bodyArray4.push([
      { text: "State", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationState, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "City", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationCity, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    bodyArray4.push([
      { text: "Phone", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationPhone, bold: false, fontSize: 6, border: [false, false, false, false] },
      { text: "", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: "Phone 2", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationAlternatePhone, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [60, 60, 5, 60, 60],
        body: bodyArray4
      }
    });
    let bodyArray5 = [];
    bodyArray5.push([
      { text: "Addresss", bold: true, fontSize: 6, border: [false, false, false, false] },
      { text: result.destinationAddress, bold: false, fontSize: 6, border: [false, false, false, false] }
    ]);
    dd.content.push({
      table: {
        headerRows: 1,
        widths: [60, "*"],
        body: bodyArray5
      }
    });
    if (result.pieceId == "10000000617001") {
      import_pdfmake.default.createPdf(dd).download("ds");
    }
    const pdfDocGenerator = import_pdfmake.default.createPdf(dd);
    pdfDocGenerator.getBlob((blob) => {
      var file = new File([blob], result.pieceId + "_labels.pdf");
      var filepath = result.houseAirwayBill + "/label/";
      if (file) {
        this.consginementService.uploadsinglefile(file, filepath).subscribe((resp) => {
          this.fileNameList.push(resp.location + resp.file);
        });
      }
    });
  }
  sortArray(array, result) {
    array.sort((a, b) => {
      const numA = parseInt(a.match(/\d+/)[0], 10);
      const numB = parseInt(b.match(/\d+/)[0], 10);
      return numA - numB;
    });
    this.download(array, result);
  }
  download(array, result) {
    return __async(this, null, function* () {
      this.spin.show();
      const Path = "/" + result.houseAirwayBill + "/mergedFiles/" + result.houseAirwayBill + "_mergedFiles.pdf";
      const blob = yield this.consginementService.pdfMerge([{ filePaths: array, outputPath: Path }]).catch((err) => {
        this.cs.commonerrorNew(err);
      });
      this.spin.hide();
      if (blob) {
        const blobOb = new Blob([blob], { type: "application/zip" });
        this.fileUrldownload = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blobOb));
        this.docurl = window.URL.createObjectURL(blobOb);
        const a = document.createElement("a");
        a.href = this.docurl;
        const fileName = result.houseAirwayBill + "_mergedFiles.zip" || "merge.zip";
        a.download = fileName;
        a.click();
        URL.revokeObjectURL(this.docurl);
      }
      this.spin.hide();
    });
  }
  downloadDocument(element) {
    return __async(this, null, function* () {
      console.log(element);
      this.spin.show();
      const blob = yield this.consginementService.download(element.value).catch((err) => {
        this.cs.commonerrorNew(err);
      });
      this.spin.hide();
      if (blob) {
        const blobOb = new Blob([blob], {
          type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        });
        this.fileUrldownload = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blobOb));
        this.docurl = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = this.docurl;
        a.download = element.value.imageRefId;
        a.click();
        URL.revokeObjectURL(this.docurl);
      }
      this.spin.hide();
    });
  }
};
_ConsignmentLabelComponent.\u0275fac = function ConsignmentLabelComponent_Factory(t) {
  return new (t || _ConsignmentLabelComponent)(\u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(CcrService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(DomSanitizer), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(CommonServiceService));
};
_ConsignmentLabelComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsignmentLabelComponent, selectors: [["app-consignment-label"]], decls: 3, vars: 0, template: function ConsignmentLabelComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p");
    \u0275\u0275text(1, "consignment-label works!");
    \u0275\u0275elementEnd();
    \u0275\u0275text(2, "\nd");
  }
} });
_ConsignmentLabelComponent.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ConsignmentLabelComponent, factory: _ConsignmentLabelComponent.\u0275fac, providedIn: "root" });
var ConsignmentLabelComponent = _ConsignmentLabelComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsignmentLabelComponent, { className: "ConsignmentLabelComponent", filePath: "src\\app\\main\\pdf\\consignment-label\\consignment-label.component.ts", lineNumber: 35 });
})();

export {
  CcrService,
  ConsignmentLabelComponent
};
//# sourceMappingURL=chunk-7FQSG277.js.map
