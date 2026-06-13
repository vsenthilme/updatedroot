package com.mnrclara.api.setup.service;


import com.mnrclara.api.setup.controller.UserProfileController;
import com.mnrclara.api.setup.model.notification.HhtNotification;
import com.mnrclara.api.setup.model.notificationmessage.NotificationMsg;
import com.mnrclara.api.setup.model.timetickettracting.TimeTicketTracking;
import com.mnrclara.api.setup.model.userprofile.UserProfile;
import com.mnrclara.api.setup.repository.TimeTicketNotificationRepository;
import com.mnrclara.api.setup.repository.TimeTicketTrackingRepository;
import com.mnrclara.api.setup.repository.UserProfileRepository;
import com.mnrclara.api.setup.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class TimeTrackingService {

    @Autowired
    TimeTicketTrackingRepository timeTicketTrackingRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    TimeTicketNotificationRepository timeTicketNotificationRepository;

    @Autowired
    CommonService commonService;


    // Create

    /**
     *
     * @param userId
     * @param timeTicketTracking
     */
    public void createTimeTracking(String userId, UserProfile timeTicketTracking) {

        TimeTicketTracking dbTimeTicket = new TimeTicketTracking();

        BeanUtils.copyProperties(timeTicketTracking, dbTimeTicket, CommonUtils.getNullPropertyNames(timeTicketTracking));
        dbTimeTicket.setDeletionIndicator(0L);
        dbTimeTicket.setUserId(userId);
        dbTimeTicket.setCreatedBy(userId);
        dbTimeTicket.setUpdatedBy(userId);
        dbTimeTicket.setProcessedStatus(0L);
        dbTimeTicket.setCreatedOn(new Date());
        dbTimeTicket.setUpdatedOn(new Date());
        timeTicketTrackingRepository.save(dbTimeTicket);
    }


    // send Notification
    public void sendNotification(TimeTicketTracking timeTicketTracking) {
        try {
//            log.info("userId - " + timeTicketTracking.getUserId());
//            log.info("UserProfile - " + timeTicketTracking);

            String classId = null;
            Long refField7 = null;
            Long refField8 = null;
            Long refField9 = null;

            if (timeTicketTracking.getClassId() != null) {
                classId = String.valueOf(timeTicketTracking.getClassId());
            }
//            log.info("classId" + timeTicketTracking.getClassId());
            if (timeTicketTracking.getReferenceField7() != null && !timeTicketTracking.getReferenceField7().equalsIgnoreCase("null")) {
                refField7 = Long.parseLong(timeTicketTracking.getReferenceField7());
            } else {
//                log.info("Reference field 7 is null ");
            }
            if (timeTicketTracking.getReferenceField8() != null && !timeTicketTracking.getReferenceField8().equalsIgnoreCase("null")) {
                refField8 = new BigDecimal(timeTicketTracking.getReferenceField8()).longValue();
            } else {
//                log.info("Reference field 8 is null ");
            }
            if (timeTicketTracking.getReferenceField9() != null && !timeTicketTracking.getReferenceField9().equalsIgnoreCase("null")) {
                refField9 = Long.parseLong(timeTicketTracking.getReferenceField9());
            } else {
//                log.info("Reference field 9 is null ");
            }

            String title = null;
            String body = null;

//            log.info("CLassID --------------->" + classId);
//            log.info("UserID ------------------> " + timeTicketTracking.getUserId());
            List<String> token = userProfileRepository.getToken(classId, timeTicketTracking.getUserId());
//            log.info("Device Token " + token);

            if (refField8 != null && refField8 >= 35 && refField7 == 0) {
                if ((refField9 == 0 || refField9 == 1) && (refField9 != null)) {
                    title = "Congratulations";
                    body = "You've met last week's goal for time ticket hours. \n " +
                            "Thank you for entering your time tickets on time!!";
                }
            }
            if (refField8 != null && refField8 >= 35 && refField7 == 0 && refField9 == null) {
                title = "Congratulations";
                body = "You've met last week's goal for time ticket hours. \n " +
                        "Thank you for entering your time tickets on time!!";
            }
//		if (refField8 != null && refField8 < 35 && refField8 >= 0) {
//			if (refField9 == 0 && refField9 != null) {
//				title = "Reminder";
//				body = "Last week's time is due today \n " +
//						"Time ticket hours for last week was " + refField8 + ", your goal is 35 hours.";
//			}
//			if (refField9 == 1 && refField9 != null) {
//				title = "Reminder";
//				body = "Last week's time is past due. Please enter your time.";
//			}
//			if (refField9 == null && refField8 < 35 && refField7 == 0) {
//				title = "Reminder";
//				body = "Last week's time is past due. Please enter your time.";
//			}
//		}
            if (refField8 != null && refField8 < 35 && refField8 >= 0) {
                if (refField9 != null && refField9 == 0) {
                    title = "Reminder";
                    body = "Last week's time is due today \n " +
                            "Time ticket hours for last week was " + refField8 + ", your goal is 35 hours.";
                } else if (refField9 != null && refField9 == 1) {
                    title = "Reminder";
                    body = "Last week's time is past due. Please enter your time.";
                } else if (refField9 == null && refField7 == 0) {
                    title = "Reminder";
                    body = "Last week's time is past due. Please enter your time.";
                }
            }

//            HhtNotification[] hhtNotifications = commonService.getToken(String.valueOf(classId), userId);
//            List<String> noti = new ArrayList<>();

//            for (HhtNotification getToken : hhtNotifications) {
//                String token = getToken.getTokenId();
//                log.info("token -------------> " + token);
//                noti.add(token);
//            }
//            log.info("3 step ok ---------------------------------------->");
//            log.info("Title ----------->" + title);
//            log.info("token -------------> " + token);
//            log.info("userId ------------>" + timeTicketTracking.getUserId());
//            log.info("body --------------> " + body);
            if (title != null && !title.isEmpty() && token != null && !token.isEmpty() &&
                    timeTicketTracking.getUserId() != null && !timeTicketTracking.getUserId().isEmpty() && body != null && !body.isEmpty()) {
                NotificationMsg notificationMsg = new NotificationMsg();
                notificationMsg.setClassId(Long.valueOf(classId));
                notificationMsg.setClientUserId(timeTicketTracking.getUserId());
                notificationMsg.setTitle(title);
//                notificationMsg.setUserId(timeTicketTracking.getUserId());
                notificationMsg.setToken(token);
                notificationMsg.setMessage(body);

//                log.info("title------------------------" + title);
                // LocalDate
                LocalDate currentDT = LocalDate.now();
                Long currentWk = (long) currentDT.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) - 1;
//                log.info("Current Date --------------------->" + currentDT);
//                log.info("CurrentWeek ------------------------> " + currentWk);

                Long dbTicket = timeTicketNotificationRepository.notificationStatus(timeTicketTracking.getUserId(), currentWk);
//                log.info("TimeTicketNotification ------------------>" + dbTicket);
                if (dbTicket == 0) {
                    String sendNotification = commonService.sendNotification(notificationMsg, timeTicketTracking.getUserId());
                    if (sendNotification.equalsIgnoreCase("OK")) {
                        timeTicketTrackingRepository.updateNotification(timeTicketTracking.getTicketId());
                        timeTicketNotificationRepository.updateNotification(timeTicketTracking.getUserId(), currentWk);
//                        log.info("Notification update successfully");
                    }
                } else {
//                    log.info("Notification Already Processed");
                }
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for userId: " + timeTicketTracking.getUserId(), e);
        }
    }

}

