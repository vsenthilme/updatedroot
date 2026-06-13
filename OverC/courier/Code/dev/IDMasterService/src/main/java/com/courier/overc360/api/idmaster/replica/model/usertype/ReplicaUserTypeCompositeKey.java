package com.courier.overc360.api.idmaster.replica.model.usertype;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaUserTypeCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `USR_TYP_ID`, `LANG_ID`
     */
    private String companyId;
    private Long userTypeId;
    private String languageId;
}
