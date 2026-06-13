package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConsoleTrackingReportInput {

    private List<String> languageId;
    private List<String> companyId;
    //    private List<String> partnerId;
    private List<String> partnerMasterAirwayBill;
    private List<String> partnerHouseAirwayBill;

    private Date fromDate;
    private Date toDate;

}
