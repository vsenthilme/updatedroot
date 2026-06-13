package com.mnrclara.api.management.service;

import com.mnrclara.api.management.controller.exception.BadRequestException;
import com.mnrclara.api.management.model.hhtnotification.HhtNotification;
import com.mnrclara.api.management.repository.HhtNotificationRepository;
import com.mnrclara.api.management.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HhtNotificationService {

	@Autowired
	private HhtNotificationRepository hhtNotificationRepository;


	//Create HhtNotification
	public HhtNotification createHhtNotification (HhtNotification newHhtNotification, String loginUserID) {

        HhtNotification newHht = new HhtNotification();
        Optional<HhtNotification> optionalDbHhtNotification =
				hhtNotificationRepository.findByClassIdAndClientIdAndDeviceIdAndTokenIdAndDeletionIndicator(
						newHhtNotification.getClassId(),
						newHhtNotification.getClientId(),
						newHhtNotification.getDeviceId(),
						newHhtNotification.getTokenId(),
						0L
				);

		if(optionalDbHhtNotification.isPresent()) {
			HhtNotification dbHhtNotification = optionalDbHhtNotification.get();
			dbHhtNotification.setDeletionIndicator(1L);
			dbHhtNotification.setUpdatedOn(new Date());
			dbHhtNotification.setUpdatedBy(loginUserID);
			hhtNotificationRepository.save(dbHhtNotification);

			if(!newHhtNotification.getIsLoggedIn()) {
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
	public HhtNotification getHhtNotification (String classId, String clientId, String deviceId, String tokenId ) {
        Optional<HhtNotification> dbHhtNotification =
				hhtNotificationRepository.findByClassIdAndClientIdAndDeviceIdAndTokenIdAndDeletionIndicator(
						classId,
						clientId,
						deviceId,
						tokenId,
						0L
				);
        if (dbHhtNotification.isPresent()) {
            return dbHhtNotification.get();
		}else {
			throw new BadRequestException("No User Found");
		}
	}

	//Login
	public HhtNotification createNotificationForLogin(HhtNotification hhtNotification, String loginUserID) {
		Optional<HhtNotification> notificationOptional = hhtNotificationRepository.findByClassIdAndClientUserIdAndDeviceIdAndTokenIdAndDeletionIndicator(
				hhtNotification.getClassId(),
				hhtNotification.getClientUserId(),
				hhtNotification.getDeviceId(),
				hhtNotification.getTokenId(),
				0L);

		if(notificationOptional.isPresent()) {
			HhtNotification dbHht = notificationOptional.get();
			dbHht.setDeletionIndicator(1L);
			dbHht.setUpdatedBy(loginUserID);
			dbHht.setUpdatedOn(new Date());
			hhtNotificationRepository.save(dbHht);

			if(!hhtNotification.getIsLoggedIn()) {
				return hhtNotification;
			}
		}
		HhtNotification newHht = new HhtNotification();
		BeanUtils.copyProperties(hhtNotification, newHht, CommonUtils.getNullPropertyNames(hhtNotification));
		newHht.setCreatedBy(loginUserID);
		newHht.setUpdatedBy(loginUserID);
		newHht.setDeletionIndicator(0L);
		newHht.setCreatedOn(new Date());
		newHht.setUpdatedOn(new Date());
		newHht.setNotificationHeaderId(System.currentTimeMillis());
		return hhtNotificationRepository.save(newHht);
	}


	/**
	 *
	 * @param classId
	 * @param userId
	 * @return
	 */
	public List<HhtNotification> getToken (String classId, String userId) {

		List<HhtNotification> notificationList = new ArrayList<>();
		List<HhtNotification> token =
				hhtNotificationRepository.findByClassIdAndClientUserIdAndDeletionIndicator(classId, userId,  0L);

		for(HhtNotification userToken : token) {
			HhtNotification notification = new HhtNotification();
			BeanUtils.copyProperties(userToken, notification, CommonUtils.getNullPropertyNames(userToken));
			notificationList.add(notification);
		}
		return notificationList;
	}
}
