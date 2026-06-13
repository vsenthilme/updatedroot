package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.repository.DeliveryRepository;
import com.courier.overc360.api.midmile.primary.repository.LMDInvoiceHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        List<Date> dates = Arrays.asList(
                deliveryRepository.date1(partnerId),
                deliveryRepository.date2(partnerId),
                deliveryRepository.date3(partnerId),
                deliveryRepository.date4(partnerId),
                deliveryRepository.date5(partnerId)
        );

        // Current Date
        LocalDate currentDate = LocalDate.now();
        Date inputDate = null;

        //Check Current Date Equal
        for(Date date :dates) {
            if(date != null) {
                LocalDate localDate = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (localDate.equals(currentDate)) {
                    inputDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    break; // Exit loop when a match is found
                }
            }
        }

        if(inputDate == null) {
            log.info("No matching date found for the current date");
            return;
        }

        Date fromDate = null;
        Date invoiceDate = invoiceHeaderRepository.getDate(partnerId);
        if (invoiceDate != null) {
            fromDate = invoiceDate;
        }

        IKeyValuePair charge = deliveryRepository.getChargeValueForDateWise(partnerId, fromDate, inputDate);
        if (charge != null) {
            lmdInvoiceService.createInvoice(charge, partnerId);
        } else {
            log.info("Monthly Basis Record Fetch Consignment Data is Null");
        }
    }


}
