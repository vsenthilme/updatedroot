package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SalesOrderLineV2 {

    @Column(nullable = false)
    @NotNull(message = "Line Reference is mandatory")
    private Long lineReference;                                // IB_LINE_NO

    @Column(nullable = false)
    @NotBlank(message = "SKU is mandatory")
    private String sku;                                    // ITM_CODE

    private String skuDescription;                            // ITEM_TEXT

    @Column(nullable = false)
    @NotNull(message = "Ordered Quantity is mandatory")
    private Double orderedQty;                                // ORD_QTY

    @Column(nullable = false)
    @NotBlank(message = "Unit of Measure is mandatory")
    private String uom;                                        // ORD_UOM

    private String manufacturerCode;

    private String manufacturerName;

    private String brand;

    private String salesOrderNo;
    private String pickListNo;
    private String storageSectionId;

    private String orderType;                                // REF_FIELD_1
    private String origin;
    private String supplierName;
    private Double packQty;
    private String fromCompanyCode;
    private Double expectedQty;
    protected String storeID;
    private String sourceBranchCode;
    private String countryOfOrigin;
    private String manufacturerFullName;
    //MiddleWare Fields
    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
}