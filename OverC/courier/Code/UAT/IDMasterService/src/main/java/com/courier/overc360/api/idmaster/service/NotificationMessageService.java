package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.primary.model.hhtnotification.NotificationMessage;
import com.courier.overc360.api.idmaster.primary.repository.NotificationMessageRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.primary.util.DateUtils;
import com.courier.overc360.api.idmaster.replica.model.hhtnotification.FindNotificationMessage;
import com.courier.overc360.api.idmaster.replica.model.hhtnotification.ReplicaNotificationMessage;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaNotificationMessageRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.NotificationMessageSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationMessageService {

    @Autowired
    NotificationMessageRepository notificationMessageRepository;

    @Autowired
    ReplicaNotificationMessageRepository replicaNotificationMessageRepository;

    //===================================================================================================================================================================

    // Get All
    public List<ReplicaNotificationMessage> getAll() {
        List<ReplicaNotificationMessage> dbNotification = replicaNotificationMessageRepository.findAll();
        dbNotification = dbNotification.stream().filter(n -> n.getDeletionIndicator() == 0)
                .collect(Collectors.toList());

        return dbNotification;
    }

    // FindNotificationMessage

    /**
     * @param findNotificationMessage
     * @return
     * @throws ParseException
     */
    public List<ReplicaNotificationMessage> findNotification(FindNotificationMessage findNotificationMessage) throws ParseException {

        if (findNotificationMessage.getStartDate() != null && findNotificationMessage.getEndDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(findNotificationMessage.getStartDate(), findNotificationMessage.getEndDate());
            findNotificationMessage.setStartDate(dates[0]);
            findNotificationMessage.setEndDate(dates[1]);
        }

        NotificationMessageSpecification messageSpecification = new NotificationMessageSpecification(findNotificationMessage);
        List<ReplicaNotificationMessage> notificationMessages = replicaNotificationMessageRepository.findAll(messageSpecification);
        return notificationMessages;
    }


    // UpdateNotification

    /**
     * @param updateNotification
     * @param loginUserID
     * @return
     */
    public List<NotificationMessage> updateNotification(List<NotificationMessage> updateNotification, String loginUserID) {
        log.info("Notification to be updated: " + updateNotification);
        List<NotificationMessage> notificationList = new ArrayList<>();
        for (NotificationMessage dbNotification : updateNotification) {
            Long notificationId = dbNotification.getNotificationId();
            if (notificationId != null) {
                NotificationMessage notificationMessage =
                        notificationMessageRepository.findByNotificationId(notificationId);
                if (notificationMessage != null) {
                    BeanUtils.copyProperties(dbNotification, notificationMessage, CommonUtils.getNullPropertyNames(dbNotification));
                    if (notificationMessage.getTab() != null) {
                        notificationMessage.setTab(notificationMessage.getTab());
                    }
                    notificationMessage.setUpdatedOn(new Date());
                    notificationMessage.setUpdatedBy(loginUserID);
                    log.info("Updated Notification : " + notificationMessage);
                    notificationList.add(notificationMessageRepository.save(notificationMessage));
                }
            }
        }
        return notificationList;
    }

    // Delete NotificationMessage

    /**
     *
     * @param notificationId
     * @param companyId
     * @param languageId
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deleteNotificationMessage(Long notificationId, String companyId, String languageId, String houseAirwayBill, String loginUserID) {

        List<NotificationMessage> notificationMessages;
        if (companyId != null && languageId != null && houseAirwayBill != null &&
                !companyId.isEmpty() && !languageId.isEmpty() && !houseAirwayBill.isEmpty()) {
            notificationMessages = notificationMessageRepository.findByCompanyIdAndLanguageIdAndHouseAirwayBillAndDeletionIndicator(
                    companyId, languageId, houseAirwayBill, 0L);

        } else if (notificationId != null) {
            notificationMessages = notificationMessageRepository.findByNotificationIdAndDeletionIndicator(notificationId, 0L);
        } else {
            throw new IllegalArgumentException("At least one of the HouseAirwayBillNo and CompanyId And LanguageId must be provided");
        }

        if (notificationMessages != null && !notificationMessages.isEmpty()) {
            for (NotificationMessage dbNotification : notificationMessages) {
                dbNotification.setDeletionIndicator(1L);
                dbNotification.setUpdatedBy(loginUserID);
                dbNotification.setUpdatedOn(new Date());
                notificationMessageRepository.save(dbNotification);
            }
        } else {
            throw new IllegalArgumentException("Given values doesn't exist");
        }
    }

}
