package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindModule {

    private List<String> companyId;
    private List<String> moduleId;
    private List<String> languageId;
    private List<Long> menuId;
    private List<Long> subMenuId;
    private List<String> statusId;
}
