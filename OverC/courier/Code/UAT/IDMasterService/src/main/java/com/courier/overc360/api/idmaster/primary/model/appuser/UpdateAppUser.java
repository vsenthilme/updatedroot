package com.courier.overc360.api.idmaster.primary.model.appuser;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UpdateAppUser {

    private String appUserName;

    private String appUserType;

    private String remark;

    private String mobileNumber;

    private String vehicleRegNumber;

    private String routeId;

    private String password;

    private String assignedHubCode;

    private String statusId;

    private String address;

    private Double latitude;

    private Double longitude;

    private Long deletionIndicator;

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
