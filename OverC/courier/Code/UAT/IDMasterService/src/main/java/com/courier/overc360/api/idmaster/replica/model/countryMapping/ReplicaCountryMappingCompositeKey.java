package com.courier.overc360.api.idmaster.replica.model.countryMapping;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaCountryMappingCompositeKey implements Serializable {

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
