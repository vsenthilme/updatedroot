package com.courier.overc360.api.idmaster.primary.model.partnerhubmapping;

import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;

@Data
public class PartnerHubMappingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `HUB_CODE`, `PARTNER_TYPE`, `PARTNER_ID`
     */

    private String companyId;
    private String languageId;
    private String hubCode;
    private String partnerType;
    private String partnerId;
    private String productCode;

}
