package com.courier.overc360.api.idmaster.replica.model.serviceType;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaServiceTypeCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;
    /*
     * `C_ID`, `LANG_ID`,'SERVICE_TYPE_ID'
     */
    private String companyId;
    private String languageId;
    private String serviceTypeId;

}
