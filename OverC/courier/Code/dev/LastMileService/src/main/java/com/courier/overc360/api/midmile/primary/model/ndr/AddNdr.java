package com.courier.overc360.api.midmile.primary.model.ndr;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddNdr {

    @NotBlank(message = "Language Id is mandatory")
    private String languageId;

    @NotBlank(message = "Company Id is mandatory")
    private String companyId;

    @NotBlank(message = "Delivery Id is mandatory")
    private String deliveryId;

    @NotBlank(message = "NDR Description is mandatory")
    private String ndrDescription;

    private String ndrId;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String consignmentBagId;

    private String houseAirwayBill;

    private String consignmentId;

    private Long deletionIndicator = 0L;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();


}
