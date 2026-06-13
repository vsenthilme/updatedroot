package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class AddAppUser {

    private String companyId;

    private String languageId;

    private String appUserId;

    private String appUserName;

    private String appUserType;

    private String statusId;

    private String password;

    private String mobileNumber;

    private String vehicleRegNumber;

    private String routeId;

    private String remark;

    private String address;

    private Double latitude;

    private Double longitude;

    private String assignedHubCode;

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

}
