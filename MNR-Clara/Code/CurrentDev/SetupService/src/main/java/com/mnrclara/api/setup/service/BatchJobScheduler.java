package com.mnrclara.api.setup.service;

import com.mnrclara.api.setup.model.timetickettracting.TimeTicketTracking;
import com.mnrclara.api.setup.repository.TimeTicketTrackingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BatchJobScheduler {

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    TimeTrackingService timeTrackingService;

    @Autowired
    TimeTicketTrackingRepository timeTicketTrackingRepository;

    //    @Scheduled(fixedRate = 2000) // Run every 2 seconds
//    @Scheduled(cron = "* * * * 2 *")
    @Scheduled(cron = "*/1 * * * * MON")
    public void sendNotification() {

        log.info("Scheduled task started.");
        List<TimeTicketTracking> dbTimeTicket = timeTicketTrackingRepository.findByProcessedStatus(0L);
        log.info("Fetching TimeTrackTable " + dbTimeTicket);
        for (TimeTicketTracking timeTicketTracking : dbTimeTicket) {
            if (timeTicketTracking != null) {
                timeTrackingService.sendNotification(timeTicketTracking);
            }
        }
    }
}
