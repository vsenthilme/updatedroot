package com.courier.overc360.api.idmaster.replica.repository;

import com.courier.overc360.api.idmaster.replica.model.billingfrequency.ReplicaBillingFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface ReplicaBillingFrequencyRepository extends JpaRepository<ReplicaBillingFrequency, String>, JpaSpecificationExecutor<ReplicaBillingFrequency> {

    Optional<ReplicaBillingFrequency> findByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(
            String languageId, String companyId, String billingFrequencyId, Long deletionIndicator
    );

    @Query(value = "Select \n" +
            "CONCAT (ts.STATUS_ID, ' - ', ts.STATUS_TEXT) \n" +
            "From tblstatus ts \n" +
            "Where \n" +
            "ts.STATUS_ID IN (:statusId) and \n" +
            "ts.IS_DELETED = 0", nativeQuery = true)
    String getStatusDescription(@Param("statusId") String statusId);

}
