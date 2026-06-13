package com.mnrclara.spark.core.model.Almailem;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindInboundLineV2 {

    private List<String> warehouseId;
    private List<String> referenceField1;
    private List<Long> statusId;
    private List<String> itemCode;
    private List<String> manufacturerPartNo;
    private List<String> refDocNumber;
<<<<<<< HEAD
=======

>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    private List<Long> inboundOrderTypeId;
    private Date startConfirmedOn;
    private Date endConfirmedOn;

    // V2 fields
    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
<<<<<<< HEAD
    private List<String> manufactureName;
    private List<String> sourceBranchCode;
    private List<String> sourceCompanyCode;
    private Date startCreatedOn;
    private Date endCreatedOn;
=======
    private List<String> sourceBranchCode;
    private List<String> sourceCompanyCode;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
