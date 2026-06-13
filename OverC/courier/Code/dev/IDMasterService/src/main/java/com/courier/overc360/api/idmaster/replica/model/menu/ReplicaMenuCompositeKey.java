package com.courier.overc360.api.idmaster.replica.model.menu;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaMenuCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `MENU_ID`, `SUB_MENU_ID`, `AUT_OBJ_ID`
     */

    private String languageId;
    private String companyId;
    private Long menuId;
    private Long subMenuId;
    private Long authorizationObjectId;

}
