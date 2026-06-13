package com.tekclover.wms.api.transaction.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.tekclover.wms.api.transaction.model.notification.NotificationMessage;
import com.tekclover.wms.api.transaction.model.notification.NotificationSave;
import com.tekclover.wms.api.transaction.repository.NotificationMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
public class PushNotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private NotificationMessageRepository notificationMessageRepository;

    /*=================================================================================================================*/

    /**
     * SendPushNotification with UserId List
     *
     * @param tokens
     * @param title
     * @param message
     * @param languageId
     * @param companyId
     * @param plantId
     * @param warehouseId
     * @param processId
     * @param userIds
     * @return
     */
    public String sendPushNotificationWithUserIdList(List<String> tokens, String title, String message, String languageId, String companyId,
                                                     String plantId, String warehouseId, String processId, List<String> userIds) {

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
                log.info("pushMessage --> {}", pushMessage);

                boolean existingMessages = notificationMessageRepository.existsByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProcessIdAndDeletionIndicator(
                        languageId, companyId, plantId, warehouseId, processId, 0L);
                if (!existingMessages) {
                    try {
                        saveNotfMsgUserIdLoop(languageId, companyId, plantId, warehouseId, title, message, processId, userIds);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                }
            } catch (FirebaseMessagingException e) {
                // Token not found, remove it from the list
                iterator.remove();
                log.error("FireBase Exception : " + e.toString());
            } catch (Exception e) {
                // Handle other unexpected exceptions
                log.error("Exception while push notification : " + e.toString());
            }
        }
        return "OK";
    }

    /**
     * SendPushNotification with UserId
     *
     * @param tokens
     * @param title
     * @param message
     * @param languageId
     * @param companyId
     * @param plantId
     * @param warehouseId
     * @param processId
     * @param userId
     * @return
     */
    public String sendPushNotificationWithUserId(List<String> tokens, String title, String message, String languageId, String companyId,
                                                 String plantId, String warehouseId, String processId, String userId) {

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

                boolean existingMessages = notificationMessageRepository.existsByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProcessIdAndDeletionIndicator(
                        languageId, companyId, plantId, warehouseId, processId, 0L);
                if (!existingMessages) {
                    try {
                        saveNotfMsgUserId(languageId, companyId, plantId, warehouseId, title, message, processId, userId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                }
            } catch (FirebaseMessagingException e) {
                // Token not found, remove it from the list
                iterator.remove();
                log.error("FireBase Exception : " + e.toString());
            } catch (Exception e) {
                // Handle other unexpected exceptions
                log.error("Exception while push notification : " + e.toString());
            }
        }
        return "OK";
    }

    /**
     * SendPushNotification
     *
     * @param tokens
     * @param title
     * @param message
     * @param languageId
     * @param companyId
     * @param plantId
     * @param warehouseId
     * @param processId
     * @return
     */
    public String sendPushNotification(List<String> tokens, String title, String message, String languageId, String companyId,
                                       String plantId, String warehouseId, String processId) {

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

                boolean existingMessages = notificationMessageRepository.existsByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProcessIdAndDeletionIndicator(
                        languageId, companyId, plantId, warehouseId, processId, 0L);
                if (!existingMessages) {
                    try {
                        saveNotificationMessage(languageId, companyId, plantId, warehouseId, title, message, processId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                }
            } catch (FirebaseMessagingException e) {
                // Token not found, remove it from the list
                iterator.remove();
                log.error("FireBase Exception : " + e.toString());
            } catch (Exception e) {
                iterator.remove();
                // Handle other unexpected exceptions
                log.error("Exception while firebase push notification : " + e.toString());
            }
        }
        return "OK";
    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param plantId
     * @param warehouseId
     * @param title
     * @param message
     * @param processId
     */
    public void saveNotificationMessage(String languageId, String companyId, String plantId, String warehouseId, String title, String message, String processId) {
        log.info("SaveNotification process start");

//        Boolean menu = false;
//        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndMenu(
//                String.valueOf(classId), clientId, message, orderType, menu);

//        if (existingMessage.isEmpty()) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyCodeId(companyId);
        notificationMessage.setLanguageId(languageId);
        notificationMessage.setPlantId(plantId);
        notificationMessage.setWarehouseId(warehouseId);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);
        notificationMessage.setProcessId(processId);
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(new Date());
        notificationMessageRepository.save(notificationMessage);
        log.info("{} Notification Message saved successfully ", warehouseId);
//        } else {
//            log.info("Similar notification message already exists, skipping saving...");
//        }
    }

    public void saveNotfMsgUserId(String languageId, String companyId, String plantId, String warehouseId, String title,
                                  String message, String processId, String userId) {
        log.info("SaveNotification process start");

//        Boolean menu = false;
//        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndMenu(
//                String.valueOf(classId), clientId, message, orderType, menu);

//        if (existingMessage.isEmpty()) {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyCodeId(companyId);
        notificationMessage.setLanguageId(languageId);
        notificationMessage.setPlantId(plantId);
        notificationMessage.setWarehouseId(warehouseId);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);
        notificationMessage.setProcessId(processId);
        notificationMessage.setUserId(userId);
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(new Date());
        notificationMessageRepository.save(notificationMessage);
        log.info("{} Notification Message saved successfully ", warehouseId);
//        } else {
//            log.info("Similar notification message already exists, skipping saving...");
//        }
    }

    public void saveNotfMsgUserIdLoop(String languageId, String companyId, String plantId, String warehouseId, String title,
                                      String message, String processId, List<String> userIds) {
        log.info("SaveNotification process start");

        for (String userId : userIds) {
//        Boolean menu = false;
//        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndMenu(
//                String.valueOf(classId), clientId, message, orderType, menu);

//        if (existingMessage.isEmpty()) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setCompanyCodeId(companyId);
            notificationMessage.setLanguageId(languageId);
            notificationMessage.setPlantId(plantId);
            notificationMessage.setWarehouseId(warehouseId);
            notificationMessage.setTitle(title);
            notificationMessage.setMessage(message);
            notificationMessage.setProcessId(processId);
            notificationMessage.setUserId(userId);
            notificationMessage.setDeletionIndicator(0L);
            notificationMessage.setCreatedOn(new Date());
            notificationMessageRepository.save(notificationMessage);
            log.info("{} Notification Message saved successfully ", warehouseId);
//        } else {
//            log.info("Similar notification message already exists, skipping saving...");
//        }
        }
    }

    /**
     *
     * @param tokens
     * @param notification
     * @return
     */
    public String sendPushNotification(List<String> tokens, NotificationSave notification) {

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
                                                 .setTitle(notification.getTopic())
                                                 .setBody(notification.getMessage())
                                                 .build())
                        .build();
                firebaseMessaging.send(pushMessage);

                boolean existingMessages = notificationMessageRepository.existsByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProcessIdAndDeletionIndicator(
                        notification.getLanguageId(), notification.getCompanyCodeId(), notification.getPlantId(),
                        notification.getWarehouseId(), notification.getDocumentNumber(), 0L);
                if (!existingMessages) {
                    try {
                        saveNotificationMessage(notification);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                }
            } catch (FirebaseMessagingException e) {
                // Token not found, remove it from the list
                iterator.remove();
                log.error("FireBase Exception : " + e.toString());
            } catch (Exception e) {
                iterator.remove();
                // Handle other unexpected exceptions
                log.error("Exception while firebase push notification : " + e.toString());
            }
        }
        return "OK";
    }

    /**
     *
     * @param notification
     */
    public void saveNotificationMessage(NotificationSave notification) {
        log.info("SaveNotification started");
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setCompanyCodeId(notification.getCompanyCodeId());
        notificationMessage.setLanguageId(notification.getLanguageId());
        notificationMessage.setPlantId(notification.getPlantId());
        notificationMessage.setWarehouseId(notification.getWarehouseId());
        notificationMessage.setTitle(notification.getTopic());
        notificationMessage.setMessage(notification.getMessage());
        notificationMessage.setProcessId(notification.getReferenceNumber());
        notificationMessage.setDeletionIndicator(0L);
        notificationMessage.setCreatedOn(notification.getCreatedOn());

        notificationMessage.setUserId(notification.getUserId().toString());
        notificationMessage.setUserType(null);
        notificationMessage.setReferenceNumber(notification.getReferenceNumber());
        notificationMessage.setDocumentNumber(notification.getDocumentNumber());
        notificationMessage.setCreatedBy(notification.getCreatedBy());
        notificationMessage.setStorageBin(notification.getStorageBin());
        notificationMessage.setNewCreated(true);
        notificationMessageRepository.save(notificationMessage);
        log.info("{} Notification Message saved successfully ", notification.getDocumentNumber());
    }

}