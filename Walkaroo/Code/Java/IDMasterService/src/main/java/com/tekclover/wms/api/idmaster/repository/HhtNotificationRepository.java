package com.tekclover.wms.api.idmaster.repository;

import com.tekclover.wms.api.idmaster.model.hhtnotification.HhtNotification;
<<<<<<< HEAD
import com.tekclover.wms.api.idmaster.repository.fragments.StreamableJpaSpecificationRepository;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
<<<<<<< HEAD
public interface HhtNotificationRepository extends JpaRepository<HhtNotification,Long>, JpaSpecificationExecutor<HhtNotification>, StreamableJpaSpecificationRepository<HhtNotification> {
=======
public interface HhtNotificationRepository extends JpaRepository<HhtNotification,Long>, JpaSpecificationExecutor<HhtNotification> {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e


    Optional<HhtNotification> findByCompanyIdAndPlantIdAndWarehouseIdAndLanguageIdAndDeviceIdAndUserIdAndTokenIdAndDeletionIndicator(
             String companyId, String plantId, String warehouseId, String languageId, String deviceId, String userId, String tokenId, Long deletionIndicator);

    Optional<HhtNotification> findTopByCompanyIdAndPlantIdAndWarehouseIdAndLanguageIdAndUserIdAndDeletionIndicator(
            String companyId, String plantId, String warehouseId, String languageId, String userId, Long deletionIndicator);
}


