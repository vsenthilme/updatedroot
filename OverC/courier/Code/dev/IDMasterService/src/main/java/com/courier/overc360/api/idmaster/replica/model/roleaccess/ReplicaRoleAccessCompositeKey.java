package com.courier.overc360.api.idmaster.replica.model.roleaccess;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaRoleAccessCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `USR_ROLE_ID`, `MENU_ID`, `SUB_MENU_ID`
     */
    private String languageId;
    private String companyId;
    private Long userRoleId;
    private Long menuId;
    private Long subMenuId;
}

