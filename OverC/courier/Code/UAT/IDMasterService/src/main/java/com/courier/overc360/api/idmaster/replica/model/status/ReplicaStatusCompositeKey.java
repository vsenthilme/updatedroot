package com.courier.overc360.api.idmaster.replica.model.status;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaStatusCompositeKey implements Serializable {


    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `STATUS_ID`
     */

    private String languageId;
    private String statusId;
}
