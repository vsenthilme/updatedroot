package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDrsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private ReplicaDrsRepository replicaDrsRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    /**
     *
     * @param companyId
     * @param languageId
     */
    public void sendNotificationForPickupCreate(String companyId, String languageId, String pickupId) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaDrsRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaDrsRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaDrsRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Pickup";
            String message = notifyId.getNotificationText() + "(Pickup Id / " + pickupId +")";
            String response = pushNotificationService.sendPushNotification(
                    deviceToken, title, message, companyId, languageId, pickupId);

//            if (response.equalsIgnoreCase("OK")) {
//                log.info("Notification sent successfully. Updating console table. ");
//                replicaDrsRepository.updateNotificationInConsoleTable(companyId, languageId, pickupId);
//            } else {
//                log.warn("Failed to send notification. Response: {}", response);
//            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Console Create", e);
        }
    }

    public void sendNotificationForPickupUpdate(String companyId, String languageId, String pickupId) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaDrsRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaDrsRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaDrsRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Pickup";
            String message = notifyId.getNotificationText() + "(Pickup Id / " + pickupId +")";
            String response = pushNotificationService.sendPushNotification(
                    deviceToken, title, message, companyId, languageId, pickupId);

//            if (response.equalsIgnoreCase("OK")) {
//                log.info("Notification sent successfully. Updating console table. ");
//                replicaDrsRepository.updateNotificationInConsoleTable(companyId, languageId, pickupId);
//            } else {
//                log.warn("Failed to send notification. Response: {}", response);
//            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Pickup Update", e);
        }


    }

    public void sendNotificationForDeliveryCreate(String companyId, String languageId, String deliveryId) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaDrsRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaDrsRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaDrsRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Pickup";
            String message = notifyId.getNotificationText() + "(Delivery Id / " + deliveryId +")";
            String response = pushNotificationService.sendPushNotification(
                    deviceToken, title, message, companyId, languageId, deliveryId);

//            if (response.equalsIgnoreCase("OK")) {
//                log.info("Notification sent successfully. Updating console table. ");
//                replicaDrsRepository.updateNotificationInConsoleTable(companyId, languageId, pickupId);
//            } else {
//                log.warn("Failed to send notification. Response: {}", response);
//            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Delivery Create", e);
        }
    }

    public void sendNotificationForDeliveryUpdate(String companyId, String languageId, String deliveryId) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaDrsRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaDrsRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaDrsRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Pickup";
            String message = notifyId.getNotificationText() + "(Delivery Id / " + deliveryId +")";
            String response = pushNotificationService.sendPushNotification(
                    deviceToken, title, message, companyId, languageId, deliveryId);

//            if (response.equalsIgnoreCase("OK")) {
//                log.info("Notification sent successfully. Updating console table. ");
//                replicaDrsRepository.updateNotificationInConsoleTable(companyId, languageId, pickupId);
//            } else {
//                log.warn("Failed to send notification. Response: {}", response);
//            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Delivery Update", e);
        }
    }


}
