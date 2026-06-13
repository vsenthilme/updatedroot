package com.courier.overc360.api.idmaster.primary.model.country;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountryCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `COUNTRY_ID`, C_ID`
     */

    private String languageId;
    private String companyId;
    private String countryId;

}
