package com.courier.overc360.api.idmaster.replica.model.billmode;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaBillModeCompositeKey  implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `BILL_MODE_ID`
     */
    private String companyId;
    private String languageId;
    private String billModeId;
}
