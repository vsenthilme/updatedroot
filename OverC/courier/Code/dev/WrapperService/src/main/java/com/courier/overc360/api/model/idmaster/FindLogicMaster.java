package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindLogicMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> consoleCountId;
    List<String> statusId;

}
