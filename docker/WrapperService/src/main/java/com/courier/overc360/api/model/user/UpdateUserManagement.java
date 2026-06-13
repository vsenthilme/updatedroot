package com.courier.overc360.api.model.user;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserManagement {

    private Long userRoleId;

    private Long userTypeId;

    private String password;

    private String userName;

    private String firstName;

    private String lastName;

    private Long statusId;

    private Long dateFormatId;

    private Long currencyDecimal;

    private String timeZone;

    private Boolean portalLoggedIn;

    private Boolean createHhtUser;

    private Boolean hhtLoggedIn;

    private Boolean resetPassword;

    private String emailId;

    private Long deletionIndicator;

}
