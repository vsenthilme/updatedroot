package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindRoute {


    List<String> companyId;
    List<String>languageId;
    List<String> routeId;
    List<String>statusId;
}
