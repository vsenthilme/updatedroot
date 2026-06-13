package com.courier.overc360.api.idmaster.primary.model.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
    {
    'LANG_ID','C_ID','STATUS_CODE','EVENT_CODE'
    }
     */
    private String languageId;
    private String companyId;
    private String eventCode;

}
