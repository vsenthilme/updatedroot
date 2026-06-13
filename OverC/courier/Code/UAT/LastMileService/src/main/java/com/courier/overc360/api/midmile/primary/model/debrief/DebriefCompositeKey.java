package com.courier.overc360.api.midmile.primary.model.debrief;

import lombok.Data;

import java.io.Serializable;

@Data
public class DebriefCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `COURIER_ID`
     */

    private String languageId;
    private String companyId;
    private String courierId;
}
