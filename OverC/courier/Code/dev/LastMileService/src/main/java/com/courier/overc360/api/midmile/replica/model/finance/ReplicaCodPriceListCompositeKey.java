package com.courier.overc360.api.midmile.replica.model.finance;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaCodPriceListCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
    'LANG_ID','C_ID','PARTNER_ID', 'LINE_NO'
     */
    private String languageId;
    private String companyId;
    private String partnerId;
    private Long lineNo;
}
