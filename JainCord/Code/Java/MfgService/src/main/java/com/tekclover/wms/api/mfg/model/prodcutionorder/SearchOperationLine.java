package com.tekclover.wms.api.mfg.model.prodcutionorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchOperationLine {

    private List<String> companyDescription;
    private List<String> plantDescription;
    private List<String> warehouseDescription;
    private List<String> itemCode;
    private List<String> itemDescription;
    private List<String> uom;
    private List<String> productionOrderNo;
    private List<String> batchNumber;
    private Date startOrderConfirmedOn;
    private Date endOrderConfirmedOn;
    private List<String> orderConfirmedBy;
    private List<String> consumption;
    private List<String> process;

    private List<String> statusId;

    private Date startCreatedOn;
    private Date endCreatedOn;
    private List<Double> orderQuantity;
    private List<Double> expectedQuantity;
    private List<Double> actualQuantity;
    private List<Double> yieldPercentage;
    private List<Double> lossPercentage;
}
