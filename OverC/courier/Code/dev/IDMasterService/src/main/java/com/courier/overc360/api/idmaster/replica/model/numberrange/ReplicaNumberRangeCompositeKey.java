package com.courier.overc360.api.idmaster.replica.model.numberrange;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaNumberRangeCompositeKey implements Serializable {

    /*
     * `LANG_ID`, `CLASS_ID`, `NUM_RAN_CODE`, `NUM_RAN_OBJ`
     */

    private String languageId;
    private Long numberRangeCode;
    private String numberRangeObject;

}
