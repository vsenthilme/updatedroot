package com.courier.overc360.api.midmile.primary.model.finance;

import lombok.Data;

import java.io.Serializable;

@Data
public class RtoPriceCompositeKey implements Serializable {
    private static final long serialVersionUID = -7617672247680004647L;
    private String languageId;
    private String companyId;
    private String partnerId;
}
