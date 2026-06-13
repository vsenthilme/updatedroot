package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindMenu {

    private List<String> languageId;
    private List<String> companyId;
    private List<Long> menuId;
    private List<Long> subMenuId;
    private List<Long> authorizationObjectId;
    private List<String> statusId;

}
