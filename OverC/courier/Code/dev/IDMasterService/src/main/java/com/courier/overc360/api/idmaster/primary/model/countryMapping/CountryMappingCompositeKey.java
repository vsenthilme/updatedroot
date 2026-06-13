package com.courier.overc360.api.idmaster.primary.model.countryMapping;

import lombok.Data;

import java.io.Serializable;
@Data
public class CountryMappingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
    {
    'LANG_ID','C_ID','PARTNER_ID','COUNTRY_ID'
    }
     */
    private String languageId;
    private String companyId;
    private String countryId;
    private String partnerId;
}
