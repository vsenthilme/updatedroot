package com.tekclover.wms.api.mfg.model.prodcutionorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchOperationLineReportProcess {

    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> productionOrderNo;
    private List<String> batchNumber;
    private List<String> itemCode;
    private List<Long> statusId;

    private List<String> receipeId;
    private List<String> operationNumber;
    private List<String> phaseNumber;
    private List<String> bomItem;
    private List<String> supervisorName;

    private List<String> process;        //to filter process

    private Date startCreatedOn;
    private Date endCreatedOn;

}