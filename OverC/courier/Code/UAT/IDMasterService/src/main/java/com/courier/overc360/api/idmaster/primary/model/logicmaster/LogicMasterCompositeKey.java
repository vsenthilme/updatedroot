package com.courier.overc360.api.idmaster.primary.model.logicmaster;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogicMasterCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
     * `C_ID`, `LANG_ID`, `CONSOLE_COUNT_ID`
     */
    private String companyId;
    private String languageId;
    private String consoleCountId;
}

