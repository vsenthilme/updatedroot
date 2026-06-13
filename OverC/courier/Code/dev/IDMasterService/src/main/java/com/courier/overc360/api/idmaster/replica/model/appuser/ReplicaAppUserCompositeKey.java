package com.courier.overc360.api.idmaster.replica.model.appuser;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaAppUserCompositeKey implements Serializable {
    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'APP_USER_ID'
     */
    private String companyId;
    private String languageId;
    private String appUserId;

}
