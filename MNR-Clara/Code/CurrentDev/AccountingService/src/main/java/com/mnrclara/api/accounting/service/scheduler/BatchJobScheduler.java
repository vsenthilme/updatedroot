package com.mnrclara.api.accounting.service.scheduler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class BatchJobScheduler {

    @Autowired
    SendNotificationScheduleService sendNotificationScheduleService;


    @Scheduled(cron = "0 0 8 * * *")
    public void processNotification(){
        LocalDateTime localDateTime = LocalDateTime.now();

        log.info("-----------Invoice Send Notification - Date " + new Date() + " Time " + localDateTime);
        sendNotificationScheduleService.sendNotificationForInvoice();

        log.info("-----------Payment Plant Send Notification --Date " + new Date() + " Time " + localDateTime);
        sendNotificationScheduleService.paymentPlantSendNotification();

        log.info("-----------Quotation Header Send Notification --Date " + new Date() + " Time " + localDateTime);
        sendNotificationScheduleService.sendQuotationHeaderNotification();

    }
}
