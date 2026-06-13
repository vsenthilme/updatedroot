package com.courier.overc360.api.idmaster.replica.model.module;

import lombok.Data;

import java.io.Serializable;

@Data
public class
ReplicaModuleCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`,`MOD_ID`,`LANG_ID`,`MENU_ID`,`SUB_MENU_ID`
     */
    private String companyId;
    private String moduleId;
    private String languageId;
    private Long menuId;
    private Long subMenuId;
}
