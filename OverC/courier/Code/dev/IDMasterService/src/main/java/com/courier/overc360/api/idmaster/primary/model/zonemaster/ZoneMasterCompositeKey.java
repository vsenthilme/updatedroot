package com.courier.overc360.api.idmaster.primary.model.zonemaster;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZoneMasterCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `ZONE_ID`,`ZONE_TYPE`
     */

    private String companyId;
    private String languageId;
    private String zoneId;
    private String zoneType;
    private String hubCode;
}
