package com.courier.overc360.api.idmaster.primary.repository;


import com.courier.overc360.api.idmaster.primary.model.hhtnotification.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long>, JpaSpecificationExecutor<NotificationMessage> {



    List<NotificationMessage> findByNotificationIdAndDeletionIndicator(Long notificationId, Long deletionIndicator);

    NotificationMessage findByNotificationId(Long notificationId);


    List<NotificationMessage> findByCompanyIdAndLanguageIdAndHouseAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String houseAirwayBill, Long deletionIndicator);
}
