package com.courier.overc360.api.model.lastmile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;




@Data
public class Npr {



    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String consignmentBagId;

    private String pickupId;

    private String houseAirwayBill;

    private String consignmentId;

    private String nprId;

    private String nprDescription;

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

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;



}
