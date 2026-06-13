package com.courier.overc360.api.model.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AddUserManagement {

    @NotBlank(message = "UserId is mandatory")
    private String userId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private Long userRoleId;

    private Long userTypeId;

    private String password;

    private String userName;

    private String firstName;

    private String lastName;

    private Long statusId;

    private Boolean createHhtUser;

    private Boolean portalLoggedIn;

    private Boolean hhtLoggedIn;

    private Boolean resetPassword;

    private Long dateFormatId;

    private Long currencyDecimal;

    private String timeZone;

    private String emailId;

}
