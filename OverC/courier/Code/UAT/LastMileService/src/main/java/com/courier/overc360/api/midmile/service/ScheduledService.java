package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.repository.DeliveryRepository;
import com.courier.overc360.api.midmile.primary.repository.LMDInvoiceHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class ScheduledService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    LMDInvoiceService lmdInvoiceService;

    @Autowired
    LMDInvoiceHeaderRepository invoiceHeaderRepository;


    @Scheduled(cron = "0 */15 * * * ?")
    public void runDailyTask() {
        List<Object[]> customerList = deliveryRepository.getCustomerId();

        customerList.forEach(customer -> {
            if (customer[0] != null && customer[1] != null) {
                String customerId = (String) customer[0];
                String billingFrequency = (String) customer[1];
                System.out.println("Customer ID: " + customerId + ", Billing Frequency: " + billingFrequency);
                if (billingFrequency.equalsIgnoreCase("2")) {
                    dailyBasisInsertDate(customerId);
                } else if (billingFrequency.equalsIgnoreCase("1")) {
                    monthlyBasisInsert(customerId);
                } else if (billingFrequency.equalsIgnoreCase("3")) {
//                    Date parseDate = (Date) customer[2];
                    dateWiseInsert(customerId);
                } else {
                    log.info("Billing Frequency Not Correct -----> " + billingFrequency);
                }
            }
        });
    }

    // Daily Basis Insert
    public void dailyBasisInsertDate(String partnerId) {
        IKeyValuePair charge = deliveryRepository.getChargeValue(partnerId);
        if (charge != null) {
            lmdInvoiceService.createInvoice(charge, partnerId);
        } else {
            log.info("Daily Basis Record Fetch Consignment Data is Null");
        }
    }

    // Month Basis Insert
    public void monthlyBasisInsert(String partnerId) {

        Date date = invoiceHeaderRepository.getDate(partnerId);
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);  // subtract of one month
            Date startDate = calendar.getTime();
            IKeyValuePair charge = deliveryRepository.getChargeValueForOneMonth(partnerId, startDate, date);
            if (charge != null) {
                lmdInvoiceService.createInvoice(charge, partnerId);
            } else {
                log.info("Monthly Basis Record Fetch Consignment Data is Null");
            }
        } else {
            log.info("Monthly Basis Data Set Date is Null in Invoice Table");
        }
    }


    // Date wise Insert
    public void dateWiseInsert(String partnerId) {

        Object[] toDateArray = deliveryRepository.getDate(partnerId);

        if (toDateArray != null) {
            LocalDate currentDate = LocalDate.now();
            boolean isProcessed = false;

            for (int i = 0; i < toDateArray.length; i++) {
                Object toDateObject = toDateArray[i];
                if (toDateObject != null) {
                    LocalDate inputDate = ((Date) toDateObject).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (inputDate.equals(currentDate)) {
                        Date fromDate = invoiceHeaderRepository.getDate(partnerId);
                        IKeyValuePair charge = deliveryRepository.getChargeValueForDateWise(partnerId, fromDate, (Date) toDateObject);
                        if (charge != null) {
                            lmdInvoiceService.createInvoice(charge, partnerId);
                            isProcessed = true;
                            break;
                        }
                    }
                }
            }
            if (!isProcessed) {
                log.info("Monthly Basis Record Fetch Consignment Data is Null");
            }
        } else {
            log.info("No dates found for the given partnerId.");
        }
    }


// // Date wise Insert
//    public void dateWiseInsert(String partnerId) {
//
//        Object[] toDateArray = deliveryRepository.getDate(partnerId);
//
//
//
//        Date fromDate = null;
//        Date invoiceDate = invoiceHeaderRepository.getDate(partnerId);
//        if (invoiceDate != null) {
//            fromDate = invoiceDate;
//        }
//        // Convert Date to LocalDate
//        LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate currentDate = LocalDate.now();
//
//        IKeyValuePair charge = deliveryRepository.getChargeValueForDateWise(partnerId, fromDate, date);
//        if (charge != null && inputDate.equals(currentDate)) {
//            lmdInvoiceService.createInvoice(charge, partnerId);
//        } else {
//            log.info("Monthly Basis Record Fetch Consignment Data is Null");
//        }
//    }

}
