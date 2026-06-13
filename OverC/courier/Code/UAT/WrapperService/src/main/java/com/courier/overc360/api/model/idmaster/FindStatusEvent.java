package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindStatusEvent {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> typeId;
}
