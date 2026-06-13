package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindNumberRange {

    private List<String> languageId;
    private List<Long> numberRangeCode;
    private List<String> numberRangeObject;
    private List<String> numberRangeStatus;

}
