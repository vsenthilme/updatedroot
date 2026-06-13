package com.courier.overc360.api.midmile.primary.repository;


import com.courier.overc360.api.midmile.primary.model.notificationmessage.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long>, JpaSpecificationExecutor<NotificationMessage> {


    List<NotificationMessage> findByCompanyIdAndLanguageIdAndConsoleIdAndHouseAirwayBillAndDeletionIndicator(
            String companyId, String languageId, String consoleId, String houseAirwayBill, Long deletionIndicator);

    List<NotificationMessage> findByCompanyIdAndLanguageIdAndDeletionIndicator(
            String companyId, String languageId, Long deletionIndicator);
}
