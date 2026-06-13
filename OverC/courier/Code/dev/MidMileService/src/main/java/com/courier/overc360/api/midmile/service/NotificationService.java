package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.repository.CcrRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCcrRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    private CcrRepository ccrRepository;

    //------------------------------------------------Notification--------------------------------------------//

    /**
     * Send Notification to Consignment
     *
     * @param companyId
     * @param languageId
     */
    public void sendNotificationForConsignmentCreate(String companyId, String languageId) {

        try {
            // Get NotificaitonId
            IKeyValuePair notifyId = replicaCcrRepository.getNotificationId(companyId, languageId, "1");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }

            List<String> userIds = replicaCcrRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }

            List<String> deviceToken = replicaCcrRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Consignment";
            String message = notifyId.getNotificationText();
            String response = pushNotificationService.sendPushNotificationV2(
                    deviceToken, title, message, companyId, languageId);

            if (response.equalsIgnoreCase("OK")) {
                log.info("Notification sent successfully. For Consignment ");
//                ccrRepository.updateNotificationInConsoleTable(companyId, languageId, consoleId);
            } else {
                log.warn("Failed to send notification. Response: {}", response);
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Console Create", e);
        }
    }

    // PreAlert_Send_Notification
    public void sendNotificationForPreAlertCreate(String companyId, String languageId) {

        try {
            // Get NotificaitonId
            IKeyValuePair notifyId = replicaCcrRepository.getNotificationId(companyId, languageId, "1");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }

            List<String> userIds = replicaCcrRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }

            List<String> deviceToken = replicaCcrRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "PreAlert";
            String message = notifyId.getNotificationText();
            String response = pushNotificationService.sendPushNotificationV2(
                    deviceToken, title, message, companyId, languageId);

            if (response.equalsIgnoreCase("OK")) {
                log.info("Notification sent successfully. For PreAlert ");
//                ccrRepository.updateNotificationInConsoleTable(companyId, languageId, consoleId);
            } else {
                log.warn("Failed to send notification. Response: {}", response);
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Console Create", e);
        }
    }

    public void sendNotificationForMobileAppCreate(String companyId, String languageId) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaCcrRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaCcrRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaCcrRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Console";
            String message = notifyId.getNotificationText();
            String response = pushNotificationService.sendPushMobileNotification(
                    deviceToken, title, message, companyId, languageId);

//            if (response.equalsIgnoreCase("OK")) {
//                log.info("Notification sent successfully. Updating console table. ");
//                ccrRepository.updateNotificationInConsoleTable(companyId, languageId);
//            } else {
//                log.warn("Failed to send notification. Response: {}", response);
//            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Console Create", e);
        }
    }


}
