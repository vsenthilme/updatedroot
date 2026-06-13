package com.courier.overc360.api.idmaster.replica.model.hub;

import lombok.Data;

import java.util.List;

@Data
public class FindHub {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> hubCode;
    private List<String> statusId;

}
