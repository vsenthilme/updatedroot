package com.courier.overc360.api.model.idmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class ZoneTypeMaster {

    private String companyId;

    private String languageId;

    private String zoneTypeId;

    private String hubCode;

    private String zoneTypeText;

    private String statusId;

    private String languageDescription;

    private String companyName;

    private Long deletionIndicator = 0L;

    private String remark;

    private String statusDescription;

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
