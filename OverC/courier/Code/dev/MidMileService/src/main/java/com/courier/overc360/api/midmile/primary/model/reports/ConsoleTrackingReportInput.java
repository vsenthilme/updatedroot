package com.courier.overc360.api.midmile.primary.model.reports;

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

//    public ConsoleTrackingReportInput() {
//        setDefaultDates();
//    }
//
//    private void setDefaultDates() {
//        Calendar calendar = Calendar.getInstance();
//
//        // Set fromDate to the start of the current day
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        this.fromDate = calendar.getTime();
//
//        // Set toDate to the end of the current day
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        calendar.set(Calendar.MILLISECOND, 999);
//        this.toDate = calendar.getTime();
//    }

}
