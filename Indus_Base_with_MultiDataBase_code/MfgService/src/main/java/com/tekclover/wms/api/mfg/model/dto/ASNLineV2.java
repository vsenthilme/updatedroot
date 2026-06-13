package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ASNLineV2 {

    @NotNull(message = "Line Reference is mandatory")
    private Long lineReference;

    @NotBlank(message = "sku is mandatory")
    private String sku;

    @NotBlank(message = "sku description is mandatory")
    private String skuDescription;

    private String containerNumber;
    private String supplierCode;
    private String supplierPartNumber;

    private String manufacturerName;
    private String manufacturerCode;

    @NotBlank(message = "Expected Date is mandatory")
    private String expectedDate;

    @NotNull(message = "expectedQty is mandatory")
    private Double expectedQty;

    @NotBlank(message = "Uom is mandatory")
    private String uom;

    private Double packQty;
    private String origin;
    private String supplierName;
    private String brand;

    //almailem fields
    private Date receivedDate;
    private Double receivedQty;
    private String receivedBy;
    private String isCompleted;
    private String isCancelled;

    private String companyCode;
    private String branchCode;
    private String batchSerialNumber;

    private String manufacturerFullName;
    private String purchaseOrderNumber;
    private String supplierInvoiceNo;
    private Long inboundOrderTypeId;
    private String storageSectionId;

    //MiddleWare Fields
    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
}