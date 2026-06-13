package com.mnrclara.spark.core.model.Almailem;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindOrderManagementLineV2 {

    private List<String> warehouseId;
    private List<String> preOutboundNo;
    private List<String> refDocNumber;
    private List<String> partnerCode;
    private List<String> itemCode;
    private List<Long> outboundOrderTypeId;
    private List<Long> statusId;
    private List<String> description;
    private List<String> soType;
<<<<<<< HEAD
    private List<String> manufacturerName;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

    private Date startRequiredDeliveryDate;
    private Date endRequiredDeliveryDate;

    //v2 fields
    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;

}
