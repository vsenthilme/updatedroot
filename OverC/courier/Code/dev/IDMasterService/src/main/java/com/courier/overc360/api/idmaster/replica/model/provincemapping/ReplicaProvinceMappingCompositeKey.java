package com.courier.overc360.api.idmaster.replica.model.provincemapping;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaProvinceMappingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `C_ID`, `PROVINCE_ID`, `PARTNER_ID`
     */

    private String languageId;
    private String companyId;
    private String provinceId;
    private String partnerId;

}
