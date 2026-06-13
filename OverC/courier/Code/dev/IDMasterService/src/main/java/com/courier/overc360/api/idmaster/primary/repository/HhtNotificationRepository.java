package com.courier.overc360.api.idmaster.primary.repository;

import com.courier.overc360.api.idmaster.primary.model.hhtnotification.HhtNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface HhtNotificationRepository extends JpaRepository<HhtNotification, Long>, JpaSpecificationExecutor<HhtNotification> {

    Optional<HhtNotification> findByCompanyIdAndLanguageIdAndDeviceIdAndUserIdAndTokenIdAndDeletionIndicator(
            String companyId, String languageId, String deviceId, String userId, String tokenId, Long deletionIndicator);


    // Get All Non-Deleted HhtNotifications
    @Query(value = "Select * From tblhhtnotification th\n" +
            "Where th.IS_DELETED=0", nativeQuery = true)
    List<HhtNotification> getAllHhtNotifications();


    // Fetch HhtNotifications with given params only
    @Query(value = "Select * From tblhhtnotification th\n" +
            "Where th.IS_DELETED=0\n" +
            "AND (COALESCE(:languageId, NULL) IS NULL OR th.LANG_ID IN (:languageId))\n" +
            "AND (COALESCE(:companyId, NULL) IS NULL OR th.C_ID IN (:companyId))\n" +
            "AND (COALESCE(:userId, NULL) IS NULL OR th.USR_ID IN (:userId))\n" +
            "AND (COALESCE(:deviceId, NULL) IS NULL OR th.DEVICE_ID IN (:deviceId))\n" +
            "AND (COALESCE(:tokenId, NULL) IS NULL OR th.TOKEN_ID IN (:tokenId))", nativeQuery = true)
    List<HhtNotification> fetchHhtNotificationsWithQry(
            @Param(value = "languageId") List<String> languageId,
            @Param(value = "companyId") List<String> companyId,
            @Param(value = "userId") List<String> userId,
            @Param(value = "deviceId") List<String> deviceId,
            @Param(value = "tokenId") List<String> tokenId);


}
