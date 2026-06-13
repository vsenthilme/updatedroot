package com.courier.overc360.api.idmaster.replica.model.event;

import lombok.Data;

import java.util.List;
@Data
public class FindEvent {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> statusCode;
    private List<String> eventCode;

}
