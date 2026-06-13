package com.courier.overc360.api.model.user;

import lombok.Data;

import java.util.Date;

@Data

public class UserManagement {

    private String userId;

    private String languageId;

    private String companyId;

    private Long userRoleId;

    private String languageIdAndDescription;

    private String companyIdAndDescription;

    private String userRoleIdAndDescription;

    private String userTypeIdAndDescription;

    private Long userTypeId;

    private String password;

    private String userName;

    private String firstName;

    private String lastName;

    private Long statusId;

    private Long dateFormatId;

    private Long currencyDecimal;

    private Boolean createHhtUser;

    private String timeZone;

    private Boolean portalLoggedIn;

    private Boolean hhtLoggedIn;

    private Boolean resetPassword;

    private String emailId;

    private Long deletionIndicator;

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;
}
