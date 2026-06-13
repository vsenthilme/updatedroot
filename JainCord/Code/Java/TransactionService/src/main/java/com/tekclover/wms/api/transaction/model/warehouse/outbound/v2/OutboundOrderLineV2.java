package com.tekclover.wms.api.transaction.model.warehouse.outbound.v2;

import com.tekclover.wms.api.transaction.model.warehouse.outbound.OutboundOrderLine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class OutboundOrderLineV2 extends OutboundOrderLine {

    private String manufacturerCode;
    private String origin;
    private String supplierName;
    private String brand;
    private Double packQty;
    private String fromCompanyCode;
    private Double expectedQty;
    protected String storeID;

    private String sourceBranchCode;
    private String countryOfOrigin;

    private String manufacturerName;
    private String manufacturerFullName;
    private String fulfilmentMethod;

    private String storageSectionId;

    private String salesOrderNo;
    private String pickListNo;

    private Long middlewareId;
    private Long middlewareHeaderId;
    private String middlewareTable;
    private String transferOrderNumber;
    private String salesInvoiceNo;
    private String supplierInvoiceNo;
    private String returnOrderNo;
    private String isCompleted;
    private String isCancelled;
    private String customerType;
    private Long outboundOrderTypeID;
    private String sortNo;

    private String meter;
    private String lotNo;
    private String pieceId;
    private String gsm;
    private String grade;
    private String color;
<<<<<<< HEAD
    private String processing_export;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
