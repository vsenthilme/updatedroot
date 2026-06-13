package com.tekclover.wms.core.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PickupHeaderV2 extends PickupHeader {

    private Double inventoryQuantity;
    private String manufacturerCode;
    private String manufacturerName;
    private String origin;
    private String brand;
    private String barcodeId;
    private String levelId;
    private String companyDescription;
    private String plantDescription;
    private String warehouseDescription;
    private String statusDescription;

    private String middlewareId;
    private String middlewareTable;
    private String referenceDocumentType;
    private String salesOrderNumber;
    private String tokenNumber;
    private String targetBranchCode;

    private String fromBranchCode;
    private String isCompleted;
    private String isCancelled;
    private Date mUpdatedOn;
    private String batchSerialNumber;

    private String customerId;
    private String customerName;

    //================================JainCord=============================
    private String sortNo;
    private String meter;
    private String lotNo;
    private String pieceId;
    private String gsm;
    private String grade;
    private String color;
    private String palletId;
}