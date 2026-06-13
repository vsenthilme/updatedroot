package com.courier.overc360.api.idmaster.replica.model.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaUserManagementCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * LANG_ID , C_ID , USER_ID
     */
    private String languageId;
    private String companyId;
    private String userId;

}
