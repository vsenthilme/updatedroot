package com.tekclover.wms.core.model.idmaster;

import lombok.Data;
import java.util.Date;

@Data
public class NumberRange {
    private String languageId;
    private String companyCodeId;
    private String plantId;
    private String warehouseId;
    private Long numberRangeCode;
    private Long fiscalYear;
    private String numberRangeObject;
    private Long numberRangeFrom;
    private Long numberRangeTo;
    private String numberRangeCurrent;
    private Long deletionIndicator;
<<<<<<< HEAD
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
=======
    private String companyIdAndDescription;
    private String plantIdAndDescription;
    private String warehouseIdAndDescription;
    private String createdBy;
    private Date createdOn = new Date();
    private String updatedBy;
    private Date updatedOn = new Date();
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
