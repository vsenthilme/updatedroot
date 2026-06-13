package com.courier.overc360.api.idmaster.primary.model.status;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatusCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `LANG_ID`, `STATUS_ID`
     */

    private String languageId;
    private String statusId;
}
