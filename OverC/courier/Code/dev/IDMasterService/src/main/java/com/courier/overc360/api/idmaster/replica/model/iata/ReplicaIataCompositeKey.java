package com.courier.overc360.api.idmaster.replica.model.iata;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaIataCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `ORIGIN`, `ORIGIN_CODE` , `IATA_KD`
     */

    private String companyId;
    private String languageId;
    private String origin;
    private String originCode;

}
