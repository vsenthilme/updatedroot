package com.courier.overc360.api.idmaster.replica.model.sortingmaster;


import lombok.Data;

import java.util.List;

@Data
public class FindSortMaster {


    private List<String> languageId;
    private List<String> companyId;
    private List<String> sortingId;
    private List<String> zoneType;


}
