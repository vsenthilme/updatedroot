package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.notificationmessage.NotificationMessage;
import com.courier.overc360.api.midmile.primary.model.notificationmessage.ReadMessages;
import com.courier.overc360.api.midmile.primary.repository.NotificationMessageRepository;
import com.courier.overc360.api.midmile.primary.repository.ReadMessagesRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class PushNotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;
    @Autowired
    private NotificationMessageRepository notificationMessageRepository;
    @Autowired
    ReadMessagesRepository readMessagesRepository;


    /*=================================================================================================================*/
    // Send Notification
    public String sendPushNotification(List<String> tokens, String title, String message, String companyId,
                                       String languageId, String houseAirwayBill, String consoleId) {

        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            if (token == null || token.isEmpty()) {
                iterator.remove();
                continue;
            }
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);

                List<NotificationMessage> existingMessage =
                        notificationMessageRepository.findByCompanyIdAndLanguageIdAndConsoleIdAndHouseAirwayBillAndDeletionIndicator(
                                companyId, languageId, consoleId, houseAirwayBill, 0L);

                if (existingMessage.isEmpty()) {
                    saveNotificationMessage(companyId, languageId, consoleId, houseAirwayBill, title, message);
                }
            } catch (Exception e) {
                iterator.remove();
//                ccrRepository.deleteNotAccessToken(token);
                e.printStackTrace();
            }
        }
        return "OK";
    }


    // Send Notification
    public String sendPushNotificationV2(List<String> tokens, String title, String message, String companyId,
                                         String languageId) {
        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            if (token == null || token.isEmpty()) {
                iterator.remove();
                continue;
            }
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                saveNotificationMessageV2(companyId, languageId, title, message);
            } catch (Exception e) {
                iterator.remove();
//                ccrRepository.deleteNotAccessToken(token);
                e.printStackTrace();
            }
        }
        return "OK";
    }


    // Save Notification Message
    public void saveNotificationMessage(String companyId, String languageId, String consoleId, String houseAirwayBill, String title, String message) {
        log.info("SaveNotification process start");
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyId(companyId);
        notificationMessage.setLanguageId(languageId);
        notificationMessage.setHouseAirwayBill(houseAirwayBill);
        notificationMessage.setConsoleId(consoleId);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(new Date());
        notificationMessageRepository.save(notificationMessage);
        log.info(consoleId + " Notification Message saved successfully ");
    }

    // Save Notification_Message
    public void saveNotificationMessageV2(String companyId, String languageId, String title, String message) {
        log.info("SaveNotification process start");
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyId(companyId);
        notificationMessage.setLanguageId(languageId);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(new Date());
        notificationMessageRepository.save(notificationMessage);
        log.info(" Notification Message saved successfully for ConsignmentCreate");

    }


    // Once Read Message Delete Record in Notification_Message Table & Insert Read_Message_Table Save Record
    public List<ReadMessages> updateReadMessages(List<NotificationMessage> notificationMessages, String loginUserID) {
        List<ReadMessages> readMessagesList = new ArrayList<>();
        notificationMessages.forEach(noti -> {
            ReadMessages newRead = new ReadMessages();
            notificationMessageRepository.delete(noti);
            log.info("Notification Message Table Delete Successfully -- NotificationId - " + noti.getNotificationId());
            BeanUtils.copyProperties(noti, newRead, CommonUtils.getNullPropertyNames(noti));
            newRead.setCreatedBy(loginUserID);
            newRead.setCreatedOn(new Date());
            readMessagesRepository.save(newRead);
            readMessagesList.add(newRead);
        });
        return readMessagesList;
    }

    public String sendPushMobileNotification(List<String> tokens, String title, String message, String companyId,
                                       String languageId) {

        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            if (token == null || token.isEmpty()) {
                iterator.remove();
                continue;
            }
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);

                List<NotificationMessage> existingMessage =
                        notificationMessageRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                                companyId, languageId,0L);

                if (existingMessage.isEmpty()) {
                    saveNotificationMessageV2(companyId, languageId, title, message);
                }
            } catch (Exception e) {
                iterator.remove();
//                ccrRepository.deleteNotAccessToken(token);
                e.printStackTrace();
            }
        }
        return "OK";
    }

}