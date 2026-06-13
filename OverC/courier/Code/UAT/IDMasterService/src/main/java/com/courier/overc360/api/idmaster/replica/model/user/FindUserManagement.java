package com.courier.overc360.api.idmaster.replica.model.user;

import lombok.Data;

import java.util.List;

@Data
public class FindUserManagement {

    private List<String> userId;
    private List<String> languageId;
    private List<String> companyId;
    private List<Long> userRoleId;
    private List<Long> userTypeId;
    private Boolean portalLoggedIn;
    private Boolean hhtLoggedIn;
}
