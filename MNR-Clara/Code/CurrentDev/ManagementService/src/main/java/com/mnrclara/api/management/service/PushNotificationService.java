package com.mnrclara.api.management.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mnrclara.api.management.model.hhtnotification.NotificationMessage;
import com.mnrclara.api.management.repository.NotificationMessageRepository;
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

    // Send Notification
    /**
     *
     * @param classId
     * @param clientId
     * @param tokens
     * @param title
     * @param message
     * @param orderType
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendPushNotification(Long classId, String clientId, List<String> tokens,
                                       String title, String message, String orderType) throws FirebaseMessagingException {
        for (String token : tokens) {
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                saveNotificationMessage(classId, clientId, title, message, orderType);
                log.info("Send Notification Successfully");
            } catch (Exception e) {
                log.error("Error sending notification to token: " + token);
                e.printStackTrace();
            }
        }
        return "OK";
    }

    // Send Notification
    /**
     *
     * @param classId
     * @param clientId
     * @param tokens
     * @param title
     * @param message
     * @param orderType
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendNotificationForReceipt(Long classId, String clientId, List<String> tokens,
                                       String title, String message, String orderType, String receiptNo) throws FirebaseMessagingException {
        for (String token : tokens) {
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                log.info("Send Notification Successfully");
            } catch (Exception e) {
                log.error("Error sending notification to token: " + token);
                e.printStackTrace();
            }
        }
        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndReceiptNo(
                String.valueOf(classId), clientId, message, orderType, receiptNo);

        if(existingMessage.isEmpty()) {
            saveNotificationMessage(classId, clientId, title, message,null, orderType, null, receiptNo);
        }
        return "OK";
    }

    // TimeTicketNotification
    /**
     *
     * @param classId
     * @param tokens
     * @param title
     * @param message
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendPushNotification(Long classId, List<String> tokens, String title, String message, String clientUserId) throws FirebaseMessagingException {

        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                log.info("Send Notification Successfully");
            } catch (FirebaseMessagingException e) {
                iterator.remove();
                e.printStackTrace();
            }
        }
        saveNotificationMessage(classId, null, title, message, clientUserId, "TIMETICKET",null,null);
        return "OK";
    }


    /**
     *
     * @param notificationMessage
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendQutationHeader(com.mnrclara.api.management.model.hhtnotification.Notification notificationMessage) throws FirebaseMessagingException {


        Iterator<String> iterator = notificationMessage.getToken().iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(notificationMessage.getTitle())
                                .setBody(notificationMessage.getMessage())
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                log.info("Send Notification Successfully");
            } catch (FirebaseMessagingException e) {
                iterator.remove();
                e.printStackTrace();
            }
        }

        log.info("QuotationHeader ----------->" + notificationMessage.getQuotationHeaderNo());
        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndQuotationNo(
                String.valueOf(notificationMessage.getClassId()), notificationMessage.getClientId(), notificationMessage.getMessage(),
                notificationMessage.getOrderType(), notificationMessage.getQuotationHeaderNo());

        if(existingMessage.isEmpty()) {
            saveNotificationMessage(notificationMessage.getClassId(), notificationMessage.getClientId(), notificationMessage.getTitle(),
                    notificationMessage.getMessage(), notificationMessage.getClientUserId(), notificationMessage.getOrderType(),
                    notificationMessage.getQuotationHeaderNo(), null);
        }
        return "OK";
    }

    // Save Notification Message
    /**
     *
     * @param classId
     * @param clientId
     * @param title
     * @param message
     * @param orderType
     */
    public void saveNotificationMessage(Long classId, String clientId, String title, String message, String orderType) {
        log.info("SaveNotification process start");

        Boolean menu = false;

        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndMenu(
                String.valueOf(classId), clientId, message, orderType, menu);

        if (existingMessage.isEmpty()) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setClassId(String.valueOf(classId));
            notificationMessage.setClientId(clientId);
            notificationMessage.setTitle(title);
            notificationMessage.setMessage(message);
            notificationMessage.setOrderType(orderType);
            notificationMessage.setDeletionIndicator(0L);
            notificationMessage.setCreatedOn(new Date());
            notificationMessageRepository.save(notificationMessage);
            log.info(orderType + " Notification Message saved successfully ");
        } else {
            log.info("Similar notification message already exists, skipping saving...");
        }
    }


    // Save Notification Message
    /**
     *
     * @param classId
     * @param clientId
     * @param title
     * @param message
     * @param orderType
     */
    public void saveNotificationMessage(Long classId, String clientId, String title, String message,
                                        String clientUserId, String orderType, String quotationHeaderNo, String receiptNo) {
        log.info("SaveNotification process start");

//        Boolean menu = false;
//        List<NotificationMessage> existingMessage = notificationMessageRepository.findByClassIdAndClientIdAndMessageAndOrderTypeAndMenu(
//                String.valueOf(classId), clientId, message, orderType, menu);

//        if (existingMessage.isEmpty()) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setClientUserId(clientUserId);
            notificationMessage.setClassId(String.valueOf(classId));
            notificationMessage.setClientId(clientId);
            notificationMessage.setTitle(title);
            notificationMessage.setQuotationNo(quotationHeaderNo);
            notificationMessage.setMessage(message);
            notificationMessage.setReceiptNo(receiptNo);
            notificationMessage.setOrderType(orderType);
            notificationMessage.setDeletionIndicator(0L);
            notificationMessage.setCreatedOn(new Date());
            notificationMessageRepository.save(notificationMessage);
            log.info(orderType + " Notification Message saved successfully ");
//        } else {
//            log.info("Similar notification message already exists, skipping saving...");
//        }
    }


    // Send Notification for Document_Upload ---CheckList
    /**
     *
     * @param documentId
     * @param classId
     * @param clientId
     * @param tokens
     * @param title
     * @param message
     * @param orderType
     * @return
     * @throws FirebaseMessagingException
     */
    public String sendPushNotification(Long documentId, Long classId, String clientId, List<String> tokens,
                                       String title, String message, String orderType) throws FirebaseMessagingException {
        for (String token : tokens) {
            try {
                Message pushMessage = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build())
                        .build();
                firebaseMessaging.send(pushMessage);
                log.info("Send Notification Successfully");
            } catch (Exception e) {
                log.error("Error sending notification to token: " + token);
                e.printStackTrace();
            }
        }

        // Save Message
        List<NotificationMessage> existingMessage = notificationMessageRepository.findByDocumentIdAndClassIdAndClientIdAndMessageAndOrderType(
               documentId, String.valueOf(classId), clientId, message, orderType);

        if (existingMessage.isEmpty()) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setClassId(String.valueOf(classId));
            notificationMessage.setClientId(clientId);
            notificationMessage.setTitle(title);
            notificationMessage.setMessage(message);
            notificationMessage.setOrderType(orderType);
            notificationMessage.setDocumentId(documentId);
            notificationMessage.setDeletionIndicator(0L);
            notificationMessage.setCreatedOn(new Date());
            notificationMessageRepository.save(notificationMessage);
            log.info(orderType + " Notification Message saved successfully ");
        } else {
            log.info("Similar notification message already exists, skipping saving...");
        }
        return "OK";
    }
}
