package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindCurrency {

    private List<String> currencyId;
    private List<String> statusId;

}
