package com.courier.overc360.api.idmaster.replica.model.opStatus;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaOpStatusCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `COMPANY_ID`, `STATUS_CODE`
     */

    private String languageId;
    private String companyId;
    private String statusCode;

}
