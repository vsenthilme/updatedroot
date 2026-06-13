package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.hhtnotification.FindHhtNotification;
import com.courier.overc360.api.idmaster.primary.model.hhtnotification.HhtNotification;
import com.courier.overc360.api.idmaster.primary.repository.HhtNotificationRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HhtNotificationService {

    @Autowired
    private HhtNotificationRepository hhtNotificationRepository;


    //Create HhtNotification
    public HhtNotification createHhtNotification(HhtNotification newHhtNotification, String loginUserID) {

        HhtNotification newHht = new HhtNotification();
        Optional<HhtNotification> optionalDbHhtNotification =
                hhtNotificationRepository.findByCompanyIdAndLanguageIdAndDeviceIdAndUserIdAndTokenIdAndDeletionIndicator(
                        newHhtNotification.getCompanyId(),
                        newHhtNotification.getLanguageId(),
                        newHhtNotification.getDeviceId(),
                        newHhtNotification.getUserId(),
                        newHhtNotification.getTokenId(),
                        0L
                );

        if (optionalDbHhtNotification.isPresent()) {
            HhtNotification dbHhtNotification = optionalDbHhtNotification.get();
            dbHhtNotification.setDeletionIndicator(1L);
            dbHhtNotification.setUpdatedOn(new Date());
            dbHhtNotification.setUpdatedBy(loginUserID);
            hhtNotificationRepository.save(dbHhtNotification);

            if (!newHhtNotification.getIsLoggedIn()) {
                return dbHhtNotification;
            }
        }
        BeanUtils.copyProperties(newHhtNotification, newHht, CommonUtils.getNullPropertyNames(newHhtNotification));
        newHht.setDeletionIndicator(0L);
        newHht.setCreatedBy(loginUserID);
        newHht.setUpdatedBy(loginUserID);
        newHht.setCreatedOn(new Date());
        newHht.setUpdatedOn(new Date());
        newHht.setNotificationHeaderId(System.currentTimeMillis());
        return hhtNotificationRepository.save(newHht);
    }

    // Get HhtNotification
    public HhtNotification getHhtNotification(String companyId, String languageId, String deviceId, String userId, String tokenId) {
        Optional<HhtNotification> dbHhtNotification =
                hhtNotificationRepository.findByCompanyIdAndLanguageIdAndDeviceIdAndUserIdAndTokenIdAndDeletionIndicator(
                        companyId,
                        languageId,
                        deviceId,
                        userId,
                        tokenId,
                        0L
                );
        if (dbHhtNotification.isPresent()) {
            return dbHhtNotification.get();
        } else {
            throw new BadRequestException("No User Found");
        }
    }

    /**
     * @return
     */
    public List<HhtNotification> getAllHhtNotifications() {
        List<HhtNotification> hhtNotificationList = hhtNotificationRepository.getAllHhtNotifications();
        return hhtNotificationList;
    }

    /**
     * Update HhtNotification
     *
     * @param updateHhtNotification
     * @param loginUserID
     * @return
     */
    public HhtNotification updateHhtNotification(HhtNotification updateHhtNotification, String loginUserID) {

        HhtNotification dbHhtNotification = getHhtNotification(updateHhtNotification.getCompanyId(), updateHhtNotification.getLanguageId(),
                updateHhtNotification.getDeviceId(), updateHhtNotification.getUserId(), updateHhtNotification.getTokenId());
        BeanUtils.copyProperties(updateHhtNotification, dbHhtNotification, CommonUtils.getNullPropertyNames(updateHhtNotification));
        dbHhtNotification.setUpdatedBy(loginUserID);
        dbHhtNotification.setUpdatedOn(new Date());
        return hhtNotificationRepository.save(dbHhtNotification);
    }

    /**
     * Find HhtNotification
     *
     * @param findHhtNotification
     * @return
     */
    public List<HhtNotification> findHhtNotifications(FindHhtNotification findHhtNotification) {

        log.info("given Inputs to fetch HhtNotifications with Qry --> {}", findHhtNotification);
        List<HhtNotification> hhtNotificationList = hhtNotificationRepository.fetchHhtNotificationsWithQry(
                findHhtNotification.getLanguageId(), findHhtNotification.getCompanyId(), findHhtNotification.getUserId(),
                findHhtNotification.getDeviceId(), findHhtNotification.getTokenId());
        return hhtNotificationList;
    }

}
