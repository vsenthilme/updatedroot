package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindErrorLog {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> refDocNumber;

    private Date fromLogDate;
    private Date toLogDate;

}
