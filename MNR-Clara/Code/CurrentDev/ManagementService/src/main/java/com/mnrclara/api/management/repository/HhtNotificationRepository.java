package com.mnrclara.api.management.repository;

import com.mnrclara.api.management.model.hhtnotification.HhtNotification;
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
public interface HhtNotificationRepository extends JpaRepository<HhtNotification,Long>, JpaSpecificationExecutor<HhtNotification> {

    Optional<HhtNotification> findByClassIdAndClientIdAndDeviceIdAndTokenIdAndDeletionIndicator(
            String classId, String clientId, String deviceId, String tokenId, Long deletionIndicator);

    @Query(value = "select token_id as tokenId from tblhhtnotification where class_id = :classId and \n" +
            " client_id = :clientId and is_deleted = 0 ", nativeQuery = true)
    public List<String> getDeviceToken(@Param("classId")Long classId,
                                       @Param("clientId")String clientId);


    Optional<HhtNotification> findByClassIdAndClientUserIdAndDeviceIdAndTokenIdAndDeletionIndicator(
            String classId, String clientUserId, String deviceId, String tokenId, Long deletionIndicator);

    List<HhtNotification> findByClassIdAndClientUserIdAndDeletionIndicator(String classId, String userId, Long deletionIndicator);
}


