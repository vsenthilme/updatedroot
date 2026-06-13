package com.tekclover.wms.api.transaction.model.inbound.gr.v2;

import com.tekclover.wms.api.transaction.model.inbound.gr.AddGrLine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AddGrLineV2 extends AddGrLine {

    private Double inventoryQuantity;
    private String barcodeId;
    //    private Double cbm;
    private String cbmUnit;
    private String manufacturerCode;
    private String manufacturerName;
    private String storageSectionId;
    private String origin;
    private String brand;
    private String rejectType;
    private String rejectReason;
    //    private Double cbmQuantity;
    private String companyDescription;
    private String plantDescription;
    private String warehouseDescription;
    private String interimStorageBin;
    private String manufacturerFullName;
    private String referenceDocumentType;
    private String middlewareId;
    private String middlewareHeaderId;
    private String middlewareTable;
    private String purchaseOrderNumber;

    private String branchCode;
    private String transferOrderNo;
    private String isCompleted;
    private Double threePLCbm;
    private String threePLUom;
    private String threePLBillStatus;
    private Double threePLLength;
    private Double threePLHeight;
    private Double threePLWidth;
    private String parentProductionOrderNo;
    private String palletId;
    private String grLineFlag;
    private String levelId;
<<<<<<< HEAD
    private String pieceNo;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}