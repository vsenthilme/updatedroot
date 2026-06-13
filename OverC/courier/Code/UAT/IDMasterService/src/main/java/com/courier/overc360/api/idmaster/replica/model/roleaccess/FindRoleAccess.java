package com.courier.overc360.api.idmaster.replica.model.roleaccess;

import lombok.Data;

import java.util.List;

@Data
public class FindRoleAccess {

    private List<String> companyId;
    private List<Long> roleId;
    private List<String> languageId;
    private List<Long> menuId;
    private List<Long> subMenuId;
    private List<String> statusId;

}
