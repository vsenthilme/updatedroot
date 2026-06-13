package com.courier.overc360.api.idmaster.primary.model.province;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProvinceCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `COUNTRY_ID`, `PROVINCE_ID`
     */

    private String languageId;
    private String countryId;
    private String provinceId;
}
