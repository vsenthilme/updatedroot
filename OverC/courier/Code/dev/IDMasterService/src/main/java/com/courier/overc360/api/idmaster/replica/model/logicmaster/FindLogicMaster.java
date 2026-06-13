package com.courier.overc360.api.idmaster.replica.model.logicmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindLogicMaster {

    List<String> companyId;
    List<String> languageId;
    List<String> consoleCountId;
    List<String> statusId;
}
