package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.notification.NotificationMessage;
import com.courier.overc360.api.midmile.primary.repository.NotificationMessageRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /*=================================================================================================================*/

    /**
     * SendPushNotification
     *
     * @param tokens
     * @param title
     * @param message
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendPushNotification(List<String> tokens, String title, String message, String companyId,
                                       String languageId, String pickupId) {

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

                    saveNotificationMessage(companyId, languageId, pickupId, title, message);
            } catch (Exception e) {
                iterator.remove();
//                ccrRepository.deleteNotAccessToken(token);
                e.printStackTrace();
            }
        }
        return "OK";
    }


    // Save Notification Message

    /**
     * @param companyId
     * @param languageId
     * @param title
     * @param message
     */
    public void saveNotificationMessage(String companyId, String languageId, String pickupId, String title, String message) {
        log.info("SaveNotification process start");

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyId(companyId);
        notificationMessage.setLanguageId(languageId);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(new Date());
        notificationMessageRepository.save(notificationMessage);
        log.info(pickupId + " Notification Message saved successfully ");
    }

}