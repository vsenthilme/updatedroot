package com.courier.overc360.api.idmaster.primary.model.appuser;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class AddAppUser {

    @NotBlank(message = "companyId is mandatory")
    private String companyId;

    @NotBlank(message = "languageId is mandatory")
    private String languageId;

    @NotBlank(message = "AppUser Id is mandatory")
    private String appUserId;

    @NotBlank(message = "appUserName is mandatory")
    private String appUserName;

    private String appUserType;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "StatusId is mandatory")
    private String statusId;

    private String mobileNumber;

    private String vehicleRegNumber;

    private String routeId;

    private String assignedHubCode;

    private String remark;

    private String address;

    private Double latitude;

    private Double longitude;

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
